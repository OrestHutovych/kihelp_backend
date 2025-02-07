package org.example.kihelp_back.wallet.usecase.impl;

import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.wallet.config.MonobankConfig;
import org.example.kihelp_back.wallet.dto.JarConfig;
import org.example.kihelp_back.wallet.dto.JarDto;
import org.example.kihelp_back.wallet.dto.MonobankJarInfoDto;
import org.example.kihelp_back.wallet.dto.WalletDto;
import org.example.kihelp_back.wallet.exception.MonobankApiException;
import org.example.kihelp_back.wallet.mapper.WalletMapper;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.service.WalletService;
import org.example.kihelp_back.wallet.usecase.WalletGetUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

import static org.example.kihelp_back.wallet.util.WalletErrorMessage.WALLET_JAR_INFO;
import static org.example.kihelp_back.wallet.util.WalletErrorMessage.WALLET_USER_ERR;

@Component
public class WalletGetUseCaseFacade implements WalletGetUseCase {
    private final WalletService walletService;
    private final WalletMapper walletMapper;
    private final UserService userService;
    private final MonobankConfig monobankConfig;
    private final RestTemplate restTemplate;

    @Value("${monobank.personal_info}")
    private String personalInfoUrl;

    public WalletGetUseCaseFacade(WalletService walletService,
                                  WalletMapper walletMapper,
                                  UserService userService,
                                  MonobankConfig monobankConfig,
                                  RestTemplate restTemplate) {
        this.walletService = walletService;
        this.walletMapper = walletMapper;
        this.userService = userService;
        this.monobankConfig = monobankConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<WalletDto> getWalletsByUser(String telegramId) {
        User sender = userService.findByJwt();
        User targetUser = userService.findByTelegramId(telegramId);

        if(!sender.getTelegramId().equals(targetUser.getTelegramId()) && !hasRole(sender, "ROLE_ADMIN")) {
            throw new IllegalArgumentException(WALLET_USER_ERR);
        }

        List<Wallet> wallets = walletService.findByUserId(targetUser.getId());

        return wallets.stream()
                .map(walletMapper::toDto)
                .toList();
    }

    @Override
    public JarDto getJarForDeposit() {
        List<JarConfig> jars = monobankConfig.getJars();
        User targetUser = userService.findByJwt();

        Optional<Map<String, String>> minJar = jars.parallelStream()
                .map(this::fetchJarInfo)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparing(jar -> parseBalance(jar.get("balance"))));

        return minJar.map(jar -> new JarDto(createJarLink(jar.get("sendId"), targetUser.getTelegramId())))
                .orElseThrow(() -> new MonobankApiException(WALLET_JAR_INFO));
    }

    private Optional<Map<String, String>> fetchJarInfo(JarConfig jarConfig) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Token", jarConfig.X_Token());

            ResponseEntity<MonobankJarInfoDto> response = restTemplate.exchange(
                    personalInfoUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<>() {}
            );

            return (response.getStatusCode() == HttpStatus.OK && response.getBody() != null)
                    ? findJarWithName(response.getBody(), jarConfig)
                    : Optional.empty();
        }catch (Exception e) {
            throw new MonobankApiException(e.getMessage());
        }
    }

    private String createJarLink(String jarSendId, String userTelegramId){
        return String.format("https://send.monobank.ua/%s?t=%s", jarSendId, userTelegramId);
    }

    private BigDecimal parseBalance(String balance) {
        return BigDecimal.valueOf(Double.parseDouble(balance));
    }

    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream().anyMatch(role -> roleName.equals(role.getName()));
    }

    private Optional<Map<String, String>> findJarWithName(MonobankJarInfoDto jarInfo, JarConfig jarConfig) {
        return jarInfo.jars().stream()
                .filter(jar -> jar.get("id").equals(jarConfig.jar()))
                .findFirst();
    }
}

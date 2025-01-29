package org.example.kihelp_back.wallet.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.global.service.TelegramBotService;
import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.model.TransactionStatus;
import org.example.kihelp_back.transaction.model.TransactionType;
import org.example.kihelp_back.transaction.usecase.TransactionCreateUseCase;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.wallet.config.MonobankConfig;
import org.example.kihelp_back.wallet.dto.JarConfig;
import org.example.kihelp_back.wallet.dto.WalletDepositDto;
import org.example.kihelp_back.wallet.dto.WalletWithdrawDto;
import org.example.kihelp_back.wallet.exception.MonobankApiException;
import org.example.kihelp_back.wallet.exception.WalletAmountNotValidException;
import org.example.kihelp_back.wallet.service.WalletService;
import org.example.kihelp_back.wallet.usecase.WalletUpdateUseCase;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import static org.example.kihelp_back.wallet.util.WalletErrorMessage.MIN_AMOUNT;
import static org.example.kihelp_back.wallet.util.WalletErrorMessage.WALLET_WITHDRAW_AMOUNT_NOT_VALID;

@Slf4j
@Component
public class WalletUpdateUseCaseFacade implements WalletUpdateUseCase {
    private final WalletService walletService;
    private final UserService userService;
    private final TransactionCreateUseCase transactionCreateUseCase;
    private final TelegramBotService telegramBotService;
    private final RestTemplate restTemplate;
    private final MonobankConfig monobankConfig;

    public WalletUpdateUseCaseFacade(WalletService walletService,
                                     MonobankConfig monobankConfig,
                                     UserService userService,
                                     TransactionCreateUseCase transactionCreateUseCase,
                                     TelegramBotService telegramBotService,
                                     RestTemplate restTemplate) {
        this.walletService = walletService;
        this.monobankConfig = monobankConfig;
        this.userService = userService;
        this.transactionCreateUseCase = transactionCreateUseCase;
        this.telegramBotService = telegramBotService;
        this.restTemplate = restTemplate;
    }

    @Override
    public void deposit(WalletDepositDto request) {
        User targetUser = userService.findByTelegramId(request.userTelegramId());

        walletService.depositAmountToWalletByUserId(targetUser.getId(), request.amount());
    }

    @Override
    @Transactional
    public void withdraw(WalletWithdrawDto request) {
        User targetUser = userService.findByJwt();

        if (request.amount().compareTo(BigDecimal.valueOf(1000.0)) < 0) {
            throw new IllegalArgumentException(WALLET_WITHDRAW_AMOUNT_NOT_VALID);
        }

        walletService.withdrawAmountFromDevWalletByUserId(targetUser.getId(), request.amount());

        TransactionWithdrawDto withdrawDto = new TransactionWithdrawDto(
                request.amount(),
                targetUser
        );

        Transaction processedTransaction = transactionCreateUseCase.createWithdrawTransaction(withdrawDto);

        telegramBotService.withdrawAdminMessage(processedTransaction, request.cardNumber());
    }

    @Scheduled(fixedRateString = "${monobank.interval}")
    @Transactional
    public void checkNewTransactions() {
        List<JarConfig> jars = monobankConfig.getJars();
        long currentTime = System.currentTimeMillis() / 1000;
        long timeFrom = currentTime - 61;

        jars.parallelStream().forEach(jarConfig -> {
            processJarTransactions(jarConfig, timeFrom);
        });
    }

    private void processJarTransactions(JarConfig jarConfig, long timeFrom){
        String jarId = jarConfig.jar();
        String xToken = jarConfig.X_Token();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Token", xToken);

        ResponseEntity<List<Map<String, String>>> response;

        try {
            response = restTemplate.exchange(
                    "https://api.monobank.ua/personal/statement/{jarId}/{timeFrom}",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<>() {},
                    jarId, timeFrom
            );
        }catch (Exception e) {
            throw new MonobankApiException(e.getMessage());
        }

        if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
            processSuccessfulResponse(response.getBody());
        }
    }

    private void processSuccessfulResponse(List<Map<String, String>> transactions) {
        if (transactions == null || transactions.isEmpty()) return;

        transactions.stream()
                .filter(transaction -> {
                    Object comment = transaction.get("comment");
                    return comment != null && !comment.toString().isBlank();
                })
                .forEach(this::processTransaction);
    }

    private void processTransaction(Map<String, String> transaction) {
        BigDecimal amount = BigDecimal.valueOf(Long.parseLong(transaction.get("amount")))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        if(!transaction.get("comment").equals("На чорну картку")) {
            if (amount.compareTo(BigDecimal.valueOf(10.0)) < 0) {
                // todo send message to chat user in telegram about error (MIN_AMOUNT)
                throw new WalletAmountNotValidException(MIN_AMOUNT);
            } else {
                User targetUser = userService.findByTelegramId(transaction.get("comment"));
                walletService.depositAmountToWalletByUserId(targetUser.getId(), amount);

                TransactionCreateDto dto = new TransactionCreateDto(
                        transaction.get("id"),
                        transaction.get("description").replace("Від: ", ""),
                        amount,
                        TransactionType.DEPOSIT,
                        TransactionStatus.COMPLETED,
                        transaction.get("comment")
                );

                Transaction processedTransaction = transactionCreateUseCase.createDepositTransaction(targetUser.getTelegramId(), dto);

                // todo add send message about deposit to chat user in telegram

                telegramBotService.depositAdminMessage(processedTransaction);
            }
        }
    }
}

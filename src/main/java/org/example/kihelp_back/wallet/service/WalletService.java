package org.example.kihelp_back.wallet.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.wallet.exception.WalletDefaultExistException;
import org.example.kihelp_back.wallet.exception.WalletExistException;
import org.example.kihelp_back.wallet.exception.WalletNotFoundException;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.example.kihelp_back.wallet.util.ErrorMessage.*;

@Service
@Slf4j
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet save(Wallet wallet) {
        log.info("Start saving wallet for user with Telegram ID: {}", wallet.getUser().getTelegramId());
        var existByName = walletRepository.existsByNameAndUserId(wallet.getName(), wallet.getUser().getId());

        if (existByName) {
            throw new WalletExistException(String.format(
                    WALLET_EXIST_BY_NAME, wallet.getName()
            ));
        }

        validateDefaultWallet(wallet);

        log.info("Successfully saved wallet by user with Telegram ID: {}", wallet.getUser().getTelegramId());
        return walletRepository.save(wallet);
    }

    public Wallet findById(Long id) {
        log.info("Attempting to find wallet by id: {}", id);
        return walletRepository.findById(id)
                .orElseThrow(() ->
                        new WalletNotFoundException(String.format(WALLET_NOT_FOUND, id))
                );
    }

    public List<Wallet> findByUserTelegramId(String telegramId) {
        log.info("Attempting to find wallet(s) by telegram ID: {}", telegramId);
        return walletRepository.findByUserTelegramId(telegramId);
    }

    @Transactional
    public void depositAmountToWalletByUserTelegramId(String telegramId, BigDecimal amount) {
        log.info("Start deposit amount to wallet for user by telegram ID: {}", telegramId);
        List<Wallet> wallets = findByUserTelegramId(telegramId);

        wallets.stream()
                .filter(Wallet::isDefaultWallet)
                .findFirst()
                .ifPresent(wallet -> {
                    wallet.setBalance(wallet.getBalance().add(amount));

                    log.info("Successfully deposit amount to wallet for user by telegram ID: {}", telegramId);
                    walletRepository.save(wallet);
                });
    }

    public void deleteNotDefaultWalletsByUser(Long userId) {
        log.info("Start deleting not default wallet(s) by user with ID: {}", userId);
        List<Wallet> wallets = walletRepository.findByUserId(userId);

        wallets.stream()
                .filter(wallet -> !wallet.isDefaultWallet())
                .forEach(walletRepository::delete);

        log.info("Successfully deleted not default wallet(s) by user with ID: {}", userId);
    }

    private void validateDefaultWallet(Wallet wallet) {
        log.info("Validating default wallet for user with Telegram ID: {}", wallet.getUser().getTelegramId());
        if (wallet.isDefaultWallet() && walletRepository.existsByUserAndDefaultWalletTrue(wallet.getUser())){
            throw new WalletDefaultExistException(DEFAULT_WALLET_EXIST);
        }
    }
}

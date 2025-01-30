package org.example.kihelp_back.wallet.service;

import org.example.kihelp_back.wallet.exception.WalletDefaultExistException;
import org.example.kihelp_back.wallet.exception.WalletExistException;
import org.example.kihelp_back.wallet.exception.WalletNotFoundException;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.example.kihelp_back.wallet.util.WalletErrorMessage.*;

@Service
public class WalletService {
    private final WalletRepository walletRepository;


    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Wallet save(Wallet wallet) {
        boolean existByName = walletRepository.existsByNameAndUserId(wallet.getName(), wallet.getUser().getId());

        if (existByName) {
            throw new WalletExistException(String.format(
                    WALLET_EXIST_BY_NAME, wallet.getName()
            ));
        }

        validateDefaultWallet(wallet);

        return walletRepository.save(wallet);
    }

    public Wallet findById(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(WALLET_NOT_FOUND));
    }

    public List<Wallet> findByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }

    @Transactional
    public void depositAmountToWalletByUserId(Long userId, BigDecimal amount, boolean isDefaultWallet) {
        List<Wallet> walletsByUser = findByUserId(userId);

        Wallet mainWallet = walletsByUser.stream()
                .filter(w -> w.isDefaultWallet() == isDefaultWallet)
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException(WALLET_NOT_FOUND));

        mainWallet.setBalance(mainWallet.getBalance().add(amount));
        walletRepository.save(mainWallet);
    }

    @Transactional
    public void withdrawAmountFromWalletByUserId(Long userId, BigDecimal amount, boolean isDefaultWallet) {
        List<Wallet> wallets = findByUserId(userId);

        Wallet wallet = wallets.stream()
                .filter(w -> w.isDefaultWallet() == isDefaultWallet)
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException(WALLET_NOT_FOUND));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(String.format(WALLET_AMOUNT_NOT_VALID, amount));
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));

        walletRepository.save(wallet);
    }

    @Transactional
    public void deleteNotDefaultWalletsByUser(Long userId) {
        List<Wallet> wallets = walletRepository.findByUserId(userId);

        wallets.stream()
                .filter(wallet -> !wallet.isDefaultWallet())
                .forEach(walletRepository::delete);
    }

    private void validateDefaultWallet(Wallet wallet) {
        if (wallet.isDefaultWallet() && walletRepository.existsByUserAndDefaultWalletTrue(wallet.getUser())){
            throw new WalletDefaultExistException(DEFAULT_WALLET_EXIST);
        }
    }
}

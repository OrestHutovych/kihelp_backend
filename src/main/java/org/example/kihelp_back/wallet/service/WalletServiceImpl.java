package org.example.kihelp_back.wallet.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.wallet.exception.WalletDefaultExistException;
import org.example.kihelp_back.wallet.exception.WalletExistException;
import org.example.kihelp_back.wallet.exception.WalletIsNotDefaultException;
import org.example.kihelp_back.wallet.exception.WalletNotFoundException;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.wallet.util.ErrorMessage.*;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void save(Wallet wallet) {
        log.info("Start saving wallet with name {} for user with ID: {}", wallet.getName(), wallet.getUser().getId());

        var existByName = walletRepository.existsByNameAndUserId(wallet.getName(), wallet.getUser().getId());
        if (existByName) {
            throw new WalletExistException(String.format(
                    WALLET_EXIST_BY_NAME, wallet.getName()
            ));
        }

        log.info("Start validating default wallet.");
        validateDefaultWallet(wallet);

        log.info("Wallet with name {} successfully saved for user with ID: {}", wallet.getName(), wallet.getUser().getId());
        walletRepository.save(wallet);
    }

    @Override
    public void validateDefaultWallet(Wallet wallet) {
        log.info("Start validating wallet with name {} by default wallet.", wallet.getName());

        if (wallet.isDefaultWallet() && walletRepository.existsByUserAndDefaultWalletTrue(wallet.getUser())){
            throw new WalletDefaultExistException(DEFAULT_WALLET_EXIST);
        }
    }

    @Override
    public Wallet findById(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(String.format(
                        WALLET_NOT_FOUND, id
                )));
    }

    @Override
    public List<Wallet> findByUserTelegramId(String telegramId) {
        return walletRepository.findByUserTelegramId(telegramId);
    }

    @Override
    public void updateDefaultWalletBalance(Long walletId, double amount) {
        var wallet = findById(walletId);

        if(!wallet.isDefaultWallet()){
            log.warn("Wallet with ID: {} is not default.", walletId);
            throw new WalletIsNotDefaultException(
                    String.format(WALLET_IS_NOT_DEFAULT, walletId)
            );
        }

        log.info("Set balance {} in default wallet with ID: {}", amount, wallet.getId());
        wallet.setBalance(wallet.getBalance() + amount);

        walletRepository.save(wallet);
    }

    @Override
    public void deleteByNotDefaultByUser(Long userId) {
        var wallets = walletRepository.findByUserId(userId);

        wallets.stream()
                .filter(wallet -> !wallet.isDefaultWallet())
                .forEach(walletRepository::delete);
    }
}

package org.example.kihelp_back.wallet.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.wallet.exception.WalletDefaultExistException;
import org.example.kihelp_back.wallet.exception.WalletExistException;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import static org.example.kihelp_back.wallet.util.ErrorMessage.DEFAULT_WALLET_EXIST;
import static org.example.kihelp_back.wallet.util.ErrorMessage.WALLET_EXIST_BY_NAME;

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
}

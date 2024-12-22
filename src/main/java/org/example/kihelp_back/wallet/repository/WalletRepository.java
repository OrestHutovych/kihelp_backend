package org.example.kihelp_back.wallet.repository;

import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    boolean existsByUserAndDefaultWalletTrue(User user);
    boolean existsByNameAndUserId(String name, Long userId);
    @Transactional(readOnly = true)
    List<Wallet> findByUserTelegramId(String telegramId);
    @Transactional(readOnly = true)
    List<Wallet> findByUserId(Long userId);
}

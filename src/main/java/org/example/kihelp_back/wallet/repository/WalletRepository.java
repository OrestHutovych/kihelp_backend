package org.example.kihelp_back.wallet.repository;

import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    boolean existsByUserAndDefaultWalletTrue(User user);
    boolean existsByNameAndUserId(String name, Long userId);
    Optional<Wallet> findByUserTelegramId(String telegramId);
}

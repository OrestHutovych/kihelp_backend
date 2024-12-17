package org.example.kihelp_back.wallet.repository;

import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    boolean existsByUserAndDefaultWalletTrue(User user);
    boolean existsByNameAndUserId(String name, Long userId);
    List<Wallet> findByUserTelegramId(String telegramId);
    List<Wallet> findByUserId(Long userId);
}

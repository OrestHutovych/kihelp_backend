package org.example.kihelp_back.wallet.repository;

import org.example.kihelp_back.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}

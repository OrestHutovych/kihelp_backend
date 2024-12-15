package org.example.kihelp_back.wallet.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.kihelp_back.user.model.User;

@Data
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "balance", nullable = false)
    private Double balance = 0.0;
    @Column(name = "default_wallet")
    private boolean defaultWallet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

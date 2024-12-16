package org.example.kihelp_back.wallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.kihelp_back.user.model.User;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "user_id"})
})
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "balance", nullable = false)
    private Double balance = 0.0;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "default_wallet")
    private boolean defaultWallet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

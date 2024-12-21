package org.example.kihelp_back.user.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.wallet.model.Wallet;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "telegram_id", unique = true, nullable = false)
    private String telegramId;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    private Instant createdTimeStamp = Instant.now();
    private boolean isBanned = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Wallet> wallets = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @PostPersist
    private void createDefaultWallet() {
        Wallet defaultWallet = Wallet.builder()
                .name("Загальний гаманець")
                .balance(0.0)
                .defaultWallet(true)
                .user(this)
                .build();
        this.wallets.add(defaultWallet);
    }
}

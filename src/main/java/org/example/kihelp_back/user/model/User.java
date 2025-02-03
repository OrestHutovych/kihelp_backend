package org.example.kihelp_back.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.kihelp_back.discount.model.Discount;
import org.example.kihelp_back.global.model.BaseEntity;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.wallet.model.Wallet;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "telegram_id", unique = true, nullable = false)
    private String telegramId;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
    @Column(name = "is_banned")
    private boolean isBanned = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Wallet> wallets = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactionHistory;

    @OneToMany(mappedBy = "user")
    private List<History> histories;

    @OneToMany(mappedBy = "developer")
    private List<Task> tasks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Discount> discounts;

    @PostPersist
    private void createDefaultWallet() {
        Wallet defaultWallet = Wallet.builder()
                .name("Загальний гаманець")
                .balance(BigDecimal.ZERO)
                .defaultWallet(true)
                .user(this)
                .build();
        this.wallets.add(defaultWallet);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "isBanned = " + isBanned() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "password = " + getPassword() + ", " +
                "username = " + getUsername() + ", " +
                "telegramId = " + getTelegramId() + ")";
    }
}

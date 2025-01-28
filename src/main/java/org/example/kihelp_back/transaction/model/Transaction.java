package org.example.kihelp_back.transaction.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.example.kihelp_back.global.model.BaseEntity;
import org.example.kihelp_back.user.model.User;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;
    @Column(name = "initials")
    private String initials;
    @Positive
    @Column(name = "amount", precision = 19, scale = 3)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatus status;
    @Column(name = "warning_sent")
    private boolean warningSent = false;
    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Transaction transactionHistory = (Transaction) o;
        return getId() != null && Objects.equals(getId(), transactionHistory.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

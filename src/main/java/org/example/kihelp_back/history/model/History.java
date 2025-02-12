package org.example.kihelp_back.history.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.kihelp_back.global.model.BaseEntity;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.user.model.User;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@Entity
@Table(schema = "kihelp-new", name = "histories")
@AllArgsConstructor
public class History extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "link")
    private String link;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "arguments")
    private String arguments;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private HistoryStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    @ToString.Exclude
    private Task task;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        History history = (History) o;
        return getId() != null && Objects.equals(getId(), history.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

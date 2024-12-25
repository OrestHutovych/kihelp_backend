package org.example.kihelp_back.task.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.global.model.BaseEntity;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.user.model.User;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "tasks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "teacher_id"})
})
public class Task extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "identifier", nullable = false)
    private String identifier;
    @Column(name = "price")
    private Integer price;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "visible")
    private boolean visible;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;
    @Column(name = "auto_generate", nullable = false)
    private boolean autoGenerate;
    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToMany
    @JoinTable(
            name = "tasks_arguments",
            joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "argument_id", referencedColumnName = "id")}
    )
    @OrderColumn(name = "argument_order")
    @ToString.Exclude
    private List<Argument> arguments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id", referencedColumnName = "id")
    @ToString.Exclude
    private User developer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ToString.Exclude
    private Teacher teacher;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<History> histories;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

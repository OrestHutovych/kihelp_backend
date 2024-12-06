package org.example.kihelp_back.task.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.teacher.model.Teacher;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "tasks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "teacher_id"})
})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "path", nullable = false)
    private String path;
    @Column(name = "price", nullable = false)
    private Integer price;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "visible")
    private boolean visible;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;
    private Instant createdTimeStamp;

    @ManyToMany
    @JoinTable(
            name = "tasks_arguments",
            joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "argument_id", referencedColumnName = "id")}
    )
    @OrderColumn(name = "argument_order")
    private List<Argument> arguments;


    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;
}

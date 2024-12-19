package org.example.kihelp_back.history.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.user.model.User;

import java.time.Instant;

@Data
@Entity
@Table(name = "histories")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "link", nullable = false)
    private String link;
    private Instant createdTimeStamp;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;
}

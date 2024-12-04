package org.example.kihelp_back.teacher.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.kihelp_back.subject.model.Subject;

@Entity
@Data
@Table(name = "teachers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "subject_id"})
})
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
}

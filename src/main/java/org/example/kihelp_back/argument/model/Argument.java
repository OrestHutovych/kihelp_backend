package org.example.kihelp_back.argument.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "arguments")
public class Argument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
}

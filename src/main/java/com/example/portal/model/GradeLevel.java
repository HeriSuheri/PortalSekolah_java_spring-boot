package com.example.portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grade_level")
public class GradeLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g. "Kelas 10", "Kelas 11"

    // Constructors
    public GradeLevel() {}

    public GradeLevel(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
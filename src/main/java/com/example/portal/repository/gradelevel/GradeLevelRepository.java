package com.example.portal.repository.gradelevel;

import com.example.portal.model.GradeLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeLevelRepository extends JpaRepository<GradeLevel, Long> {
    boolean existsByNameIgnoreCase(String name);
}


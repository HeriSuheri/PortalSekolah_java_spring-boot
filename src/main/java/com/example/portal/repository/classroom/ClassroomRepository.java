package com.example.portal.repository.classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portal.model.Classroom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    boolean existsByNameIgnoreCase(String name);

    Page<Classroom> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}

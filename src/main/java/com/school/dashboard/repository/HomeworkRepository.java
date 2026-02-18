package com.school.dashboard.repository;

import com.school.dashboard.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    @Query("""
    SELECT h FROM Homework h
    WHERE h.className = :className
      AND h.section = :section
      AND h.subject = :subject
    ORDER BY h.date DESC, h.id DESC
    LIMIT 1
    """)
    Optional<Homework> findLatest(String className, String section, String subject);
}

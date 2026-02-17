package com.school.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.dashboard.entity.TeacherAccessCode;

public interface TeacherAccessCodeRepository extends JpaRepository<TeacherAccessCode, Long> {
    Optional<TeacherAccessCode> findByCode(String code);
}

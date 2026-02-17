package com.school.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.dashboard.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {}

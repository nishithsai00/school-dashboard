package com.school.dashboard.repository;

import com.school.dashboard.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    List<Homework> findByClassNameAndSection(String className, String section);
}

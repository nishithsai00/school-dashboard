package com.school.dashboard.repository;

import com.school.dashboard.entity.Student;
import com.school.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUser(User user);

    List<Student> findByClassNameAndSection(String className, String section);
}

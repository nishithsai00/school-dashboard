package com.school.dashboard.repository;

import com.school.dashboard.entity.Student;
import com.school.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // used by student login
    Optional<Student> findByUser(User user);

    // used by teacher to view class students
    List<Student> findByClassNameAndSection(String className, String section);

    // used to auto detect available classes
    @Query("SELECT DISTINCT s.className, s.section FROM Student s ORDER BY s.className, s.section")
    List<Object[]> findAllClassSections();
 


}

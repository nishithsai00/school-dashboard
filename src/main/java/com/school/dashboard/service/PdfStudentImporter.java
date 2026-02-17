package com.school.dashboard.service;

import com.school.dashboard.entity.Student;
import com.school.dashboard.entity.User;
import com.school.dashboard.repository.StudentRepository;
import com.school.dashboard.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PdfStudentImporter {

    private final StudentRepository studentRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public PdfStudentImporter(StudentRepository studentRepo,
                              UserRepository userRepo,
                              PasswordEncoder passwordEncoder) {
        this.studentRepo = studentRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void importStudents(List<Map<String, String>> students) {

        // SORT BY NAME (alphabetical)
        students.sort(Comparator.comparing(s -> s.get("name")));

        int rollNumber = 26001;

        for (Map<String, String> data : students) {

            String username = String.valueOf(rollNumber++);

            // create login
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole("STUDENT");

            userRepo.save(user);

            // create student record
            Student student = new Student();
            student.setUser(user);
            student.setName(data.get("name"));
            student.setFatherName(data.get("father"));
            student.setPhone(data.get("phone"));
            student.setAadhar(data.get("aadhar"));
            student.setRollNo(username);

            // IMPORTANT (match teacher filter)
            student.setClassName("7");
            student.setSection("A");

            studentRepo.save(student);
        }

        System.out.println("STUDENT IMPORT COMPLETED");
    }
}

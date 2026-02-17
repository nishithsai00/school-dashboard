package com.school.dashboard.controller;

import com.school.dashboard.entity.Student;
import com.school.dashboard.entity.User;
import com.school.dashboard.repository.StudentRepository;
import com.school.dashboard.repository.UserRepository;
import com.school.dashboard.repository.HomeworkRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    private final UserRepository userRepo;
    private final StudentRepository studentRepo;
    private final HomeworkRepository homeworkRepo;

    public StudentController(UserRepository userRepo,
                             StudentRepository studentRepo,
                             HomeworkRepository homeworkRepo) {
        this.userRepo = userRepo;
        this.studentRepo = studentRepo;
        this.homeworkRepo = homeworkRepo;
    }

    @GetMapping("/student/dashboard")
    public String dashboard(Authentication auth, Model model) {

        // find logged in user
        User user = userRepo.findByUsername(auth.getName()).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        // find student details
        Student student = studentRepo.findByUser(user).orElse(null);

        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", student);

        // fetch ALL homework of student class
        model.addAttribute("homeworks",
                homeworkRepo.findByClassNameAndSection(
                        student.getClassName(),
                        student.getSection()
                )
        );

        return "student-dashboard";
    }
}

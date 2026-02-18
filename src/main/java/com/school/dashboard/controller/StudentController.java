package com.school.dashboard.controller;

import com.school.dashboard.entity.Homework;
import com.school.dashboard.entity.Student;
import com.school.dashboard.entity.User;
import com.school.dashboard.repository.HomeworkRepository;
import com.school.dashboard.repository.StudentRepository;
import com.school.dashboard.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.Map;

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

        if (auth == null) return "redirect:/login";

        User user = userRepo.findByUsername(auth.getName()).orElse(null);
        if (user == null) return "redirect:/login";

        Student student = studentRepo.findByUser(user).orElse(null);
        if (student == null) return "redirect:/login";

        model.addAttribute("student", student);

        String cls = student.getClassName();
        String sec = student.getSection();

        String[] subjects = {"Maths", "Science", "English", "Social"};

        Map<String, Homework> latestHomework = new LinkedHashMap<>();

        for (String subject : subjects) {
            Homework hw = homeworkRepo
                    .findLatest(cls, sec, subject)
                    .orElse(null);

            latestHomework.put(subject, hw);
        }

        model.addAttribute("latestHomework", latestHomework);

        return "student-dashboard";
    }
}

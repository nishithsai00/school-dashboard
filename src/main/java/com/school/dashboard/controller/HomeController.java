package com.school.dashboard.controller;

import com.school.dashboard.entity.User;
import com.school.dashboard.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserRepository userRepo;

    public HomeController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String home(Authentication auth) {

        if(auth == null)
            return "redirect:/login";

        User user = userRepo.findByUsername(auth.getName()).orElse(null);

        if(user == null)
            return "redirect:/login";

        if(user.getRole().equals("TEACHER"))
            return "redirect:/teacher/dashboard";

        if(user.getRole().equals("STUDENT"))
            return "redirect:/student/dashboard";

        return "redirect:/login";
    }
}

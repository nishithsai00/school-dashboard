package com.school.dashboard.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class LoginRedirectController {

    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication auth) {

        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());

        if (roles.contains("ROLE_TEACHER")) {
            return "redirect:/teacher/dashboard";
        }

        if (roles.contains("ROLE_STUDENT")) {
            return "redirect:/student/dashboard";
        }

        return "redirect:/login";
    }
}

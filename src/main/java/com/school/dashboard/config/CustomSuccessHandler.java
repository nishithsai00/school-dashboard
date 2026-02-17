package com.school.dashboard.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_TEACHER")) {
            response.sendRedirect("/teacher/dashboard");
        } else {
            response.sendRedirect("/student/dashboard");
        }
    }
}

package com.school.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DashboardApplication {

    public static void main(String[] args) {

        // TEMPORARY: prints encrypted password once
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("ENCRYPTED PASSWORD FOR admin123:");
        System.out.println(encoder.encode("admin123"));

        SpringApplication.run(DashboardApplication.class, args);
    }
}

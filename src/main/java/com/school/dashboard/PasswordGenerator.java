package com.school.dashboard;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String admin = encoder.encode("admin5678");
        String teacherCreate = encoder.encode("09876");

        System.out.println("Admin password:");
        System.out.println(admin);

        System.out.println("\nTeacher creation code:");
        System.out.println(teacherCreate);
    }
}

package com.school.dashboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String rollNo;
    private String phone;
    private String fatherName;
    private String aadhar;
    private String className;
    private String section;

    private LocalDate dob;
    private String gender;
    private String address;
}

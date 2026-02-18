package com.school.dashboard.controller;

import com.school.dashboard.entity.*;
import com.school.dashboard.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;


import java.time.LocalDate;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final HomeworkRepository homeworkRepo;
    private final TeacherAccessCodeRepository codeRepo;
    private final UserRepository userRepo;
    private final TeacherRepository teacherRepo;
    private final StudentRepository studentRepo;
    private final PasswordEncoder passwordEncoder;

    public TeacherController(HomeworkRepository homeworkRepo,
                             TeacherAccessCodeRepository codeRepo,
                             UserRepository userRepo,
                             TeacherRepository teacherRepo,
                             StudentRepository studentRepo,
                             PasswordEncoder passwordEncoder) {

        this.homeworkRepo = homeworkRepo;
        this.codeRepo = codeRepo;
        this.userRepo = userRepo;
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // ================= DASHBOARD =================
   @GetMapping("/dashboard")
public String dashboard(Model model) {

    List<Object[]> sections = studentRepo.findAllClassSections();
    model.addAttribute("sections", sections);

    return "teacher-dashboard";
}


    // ================= VERIFY TEACHER ACCESS =================
    @GetMapping("/verify-teacher")
    public String verifyTeacherPage() {
        return "verify-teacher";
    }

    @PostMapping("/verify-code")
    public String verifyCode(@RequestParam String accessCode, Model model) {

        if (codeRepo.findByCode(accessCode).isPresent()) {
            return "create-teacher";
        }

        model.addAttribute("error", "Wrong Access Code");
        return "verify-teacher";
    }

    // ================= CREATE NEW TEACHER =================
    @PostMapping("/save-teacher")
    public String saveTeacher(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String name,
                              @RequestParam String phone) {

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(null, username, encodedPassword, "TEACHER");
        userRepo.save(user);

        Teacher teacher = new Teacher(null, user, name, phone);
        teacherRepo.save(teacher);

        return "redirect:/teacher/dashboard";
    }

    // ================= ADD STUDENT PAGE =================
    @GetMapping("/add-student")
    public String addStudentPage() {
        return "add-student";
    }

    // ================= SAVE STUDENT =================
    @PostMapping("/save-student")
    public String saveStudent(@RequestParam String name,
                              @RequestParam String rollNo,
                              @RequestParam String phone,
                              @RequestParam String fatherName,
                              @RequestParam String aadhar,
                              @RequestParam String className,
                              @RequestParam String section) {

        // username = roll number
        String username = rollNo;

        // default student password
        String rawPassword = "123456";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // create login
        User user = new User(null, username, encodedPassword, "STUDENT");
        userRepo.save(user);

        // create student record
        Student student = new Student();
        student.setUser(user);
        student.setName(name);
        student.setRollNo(rollNo);
        student.setPhone(phone);
        student.setFatherName(fatherName);
        student.setAadhar(aadhar);
        student.setClassName(className);
        student.setSection(section);

        studentRepo.save(student);

        return "redirect:/teacher/dashboard";
    }

    // ================= ADD HOMEWORK =================
    @PostMapping("/add-homework")
    public String addHomework(@RequestParam String className,
                              @RequestParam String subject,
                              @RequestParam String homework) {

        // className comes like "10-A"
        String[] parts = className.split("-");
        String cls = parts[0];
        String sec = parts[1];

        Homework hw = new Homework();
        hw.setClassName(cls);
        hw.setSection(sec);
        hw.setSubject(subject);
        hw.setHomework(homework);
        hw.setDate(LocalDate.now());

        homeworkRepo.save(hw);

        return "redirect:/teacher/dashboard";
    }
    // ================= SELECT SECTION =================
@GetMapping("/students")
public String selectSectionPage(Model model) {

    List<Object[]> sections = studentRepo.findAllClassSections();

    model.addAttribute("sections", sections);

    return "select-section";
}


// ================= VIEW STUDENTS IN SECTION =================
@GetMapping("/view-students")
public String viewStudents(@RequestParam String className,
                           @RequestParam String section,
                           Model model) {

    model.addAttribute("students",
            studentRepo.findByClassNameAndSection(className, section));

    return "student-list";
}

// ================= EDIT STUDENT PAGE =================
@GetMapping("/edit-student/{id}")
public String editStudent(@PathVariable Long id, Model model) {

    Student student = studentRepo.findById(id).orElse(null);

    if (student == null) {
        return "redirect:/teacher/dashboard";
    }

    model.addAttribute("student", student);
    return "edit-student";
}

// ================= UPDATE STUDENT =================
@PostMapping("/update-student")
public String updateStudent(@RequestParam Long id,
                            @RequestParam String name,
                            @RequestParam String phone,
                            @RequestParam String fatherName,
                            @RequestParam String aadhar) {

    Student student = studentRepo.findById(id).orElse(null);

    if (student != null) {
        student.setName(name);
        student.setPhone(phone);
        student.setFatherName(fatherName);
        student.setAadhar(aadhar);

        studentRepo.save(student);
    }

    return "redirect:/teacher/dashboard";
}
// ================= DELETE STUDENT =================
@GetMapping("/delete-student/{id}")
public String deleteStudent(@PathVariable Long id) {

    Student student = studentRepo.findById(id).orElse(null);

    if (student != null) {
        User user = student.getUser();

        studentRepo.delete(student);   // delete student record
        userRepo.delete(user);         // delete login account
    }

    return "redirect:/teacher/students";
}
// ================= CHANGE PASSWORD PAGE =================
@GetMapping("/change-password/{id}")
public String changePasswordPage(@PathVariable Long id, Model model) {

    Student student = studentRepo.findById(id).orElse(null);

    if (student == null) {
        return "redirect:/teacher/dashboard";
    }

    model.addAttribute("student", student);
    return "change-password";
}

// ================= UPDATE PASSWORD =================
@PostMapping("/update-password")
public String updatePassword(@RequestParam Long id,
                             @RequestParam String newPassword) {

    Student student = studentRepo.findById(id).orElse(null);

    if (student != null) {
        User user = student.getUser();

        String encoded = passwordEncoder.encode(newPassword);
        user.setPassword(encoded);

        userRepo.save(user);
    }

    return "redirect:/teacher/students";
}

}

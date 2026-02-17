package com.school.dashboard;

import com.school.dashboard.service.PdfStudentImporter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataLoader {

private static final boolean ENABLE_IMPORT = false;


    @Bean
    CommandLineRunner loadStudents(PdfStudentImporter importer) {
        return args -> {

            if(!ENABLE_IMPORT) return;

            List<Map<String, String>> students = new ArrayList<>();

            students.add(student("Bala Subramanya Bhuvan Parasu","P.Bharat Kumar","9441415358","418745137249"));
            students.add(student("Bhanu Hari Nandan Vuppuluri","V.Hema Kumar","7386616450","625176125128"));
            students.add(student("Charvik Reddy Manda","M.Sridhar Reddy","9392546342","240770626501"));
            students.add(student("Dipankar Porey","Kalipada Porey","9515310583","840518987325"));
            students.add(student("Divya Kranthi Mylavarapu","M.Ramesh","9966718323","856287561039"));
            students.add(student("Guna Shekar Uggina","U.V.Durga Rao","9705959639","573250557470"));
            students.add(student("Durga Satwik Gorli","Poli Naidu","9533860785","772481484608"));
            students.add(student("Hitesh Manikanta Potina","P.Srinivasa Rao","9063293910","327046686497"));
            students.add(student("Mohan Shanmukha Datta Maddali","M.Venkata Siva Pavan","7285958858","759234828592"));
            students.add(student("Pavan Sai Teja Nallabilli","Kanaka Rao","9676823323","911465340254"));

            importer.importStudents(students);
        };
    }

    private Map<String, String> student(String name, String father, String phone, String aadhar) {
        Map<String, String> s = new HashMap<>();
        s.put("name", name);
        s.put("father", father);
        s.put("phone", phone);
        s.put("aadhar", aadhar);
        return s;
    }
}

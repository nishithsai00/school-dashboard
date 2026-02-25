School Management Portal

A full-stack web application for managing student records, attendance, and academic data.
The system allows administrators and teachers to securely manage school operations through a browser or installable mobile-like PWA.

---

Features

- Student registration and record management
- Attendance tracking
- Role-based login (Admin / Teacher)
- Secure authentication
- REST API backend
- Persistent MySQL database
- Installable Progressive Web App (PWA)

---

Tech Stack

Backend: Spring Boot, JPA/Hibernate, REST APIs
Database: MySQL
Server: Linux VPS, Nginx Reverse Proxy, Cloudflare Tunnel
Build Tool: Maven

---

System Architecture

Client (Browser / Mobile PWA) → Nginx → Spring Boot Application → MySQL Database

---

How to Run Locally

1. Clone the repository
   git clone https://github.com/nishithsai00/school-dashboard

2. Create MySQL database
   CREATE DATABASE school;

3. Update "application.properties"
   spring.datasource.url=jdbc:mysql://localhost:3306/school
   spring.datasource.username= (your database user name)
   spring.datasource.password= (your database password)

4. Run the application
   mvn spring-boot:run

Server starts on:
http://localhost:8080

---

Deployment

The application is deployed on a Linux server using:

- Nginx as reverse proxy
- Cloudflare Tunnel for public HTTPS access

---

Future Improvements

- Online fee payment integration
- Parent login portal
- Notifications system

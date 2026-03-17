package com.example.BaiKT.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Sinh viên nào đăng ký?
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Đăng ký môn nào?
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDate enroll_date; // Ngày đăng ký
}
package com.example.BaiKT.repository;

import com.example.BaiKT.model.Enrollment;
import com.example.BaiKT.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(Student student); // Câu 7: Lấy môn đã đăng ký
}
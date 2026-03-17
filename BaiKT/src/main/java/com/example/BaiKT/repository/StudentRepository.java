package com.example.BaiKT.repository;

import com.example.BaiKT.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username); // Phục vụ cho đăng nhập lát nữa
}
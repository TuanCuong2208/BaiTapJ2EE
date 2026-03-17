package com.example.BaiKT.controller;

import com.example.BaiKT.model.Role;
import com.example.BaiKT.model.Student;
import com.example.BaiKT.repository.RoleRepository;
import com.example.BaiKT.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RoleRepository roleRepository;

    // Hiện form đăng ký
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    // Xử lý lưu tài khoản
    @PostMapping("/register")
    public String registerStudent(@ModelAttribute("student") Student student) {
        // 1. Mã hóa mật khẩu (Chuẩn bị sẵn cho Câu 5)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        // 2. Tự động gán quyền mặc định là STUDENT
        Role studentRole = roleRepository.findByName("STUDENT");
        if (studentRole != null) {
            student.getRoles().add(studentRole);
        }

        // 3. Lưu vào Database
        studentRepository.save(student);
        return "redirect:/home"; // Đăng ký xong văng ra trang chủ
    }
}
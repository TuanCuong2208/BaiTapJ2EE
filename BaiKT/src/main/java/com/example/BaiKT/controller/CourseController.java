package com.example.BaiKT.controller;

import com.example.BaiKT.model.Course;
import com.example.BaiKT.repository.CategoryRepository;
import com.example.BaiKT.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Xem danh sách (Read)
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "course-list";
    }

    // 2. Hiện form Thêm mới (Create)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryRepository.findAll());
        return "course-form";
    }

    // 3. Xử lý Lưu dữ liệu (Dùng chung cho cả Thêm và Sửa)
    @PostMapping("/save")
    public String saveCourse(@ModelAttribute("course") Course course) {
        courseRepository.save(course);
        return "redirect:/admin/courses";
    }

    // 4. Hiện form Sửa (Update)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Course course = courseRepository.findById(id).orElse(null);
        model.addAttribute("course", course);
        model.addAttribute("categories", categoryRepository.findAll());
        return "course-form";
    }

    // 5. Nút Xóa (Delete)
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseRepository.deleteById(id);
        return "redirect:/admin/courses";
    }
}
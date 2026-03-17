package com.example.BaiKT.controller;

import com.example.BaiKT.model.Course;
import com.example.BaiKT.repository.CourseRepository;
import com.example.BaiKT.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/home")
    public String viewHomePage(@RequestParam(value = "page", defaultValue = "1") int pageNo,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            // Câu 8: Xử lý khi có từ khóa tìm kiếm
            List<Course> searchResult = courseRepository.findByNameContainingIgnoreCase(keyword);
            model.addAttribute("courses", searchResult);
            model.addAttribute("totalPages", 1);
        } else {
            // Hiển thị bình thường có phân trang
            Page<Course> page = courseService.findPaginated(pageNo);
            model.addAttribute("courses", page.getContent());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
        }
        model.addAttribute("keyword", keyword);
        return "home";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
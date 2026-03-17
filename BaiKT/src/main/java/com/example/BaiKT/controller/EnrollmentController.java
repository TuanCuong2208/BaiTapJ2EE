package com.example.BaiKT.controller;

import com.example.BaiKT.model.Course;
import com.example.BaiKT.model.Enrollment;
import com.example.BaiKT.model.Student;
import com.example.BaiKT.repository.CourseRepository;
import com.example.BaiKT.repository.EnrollmentRepository;
import com.example.BaiKT.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/enroll")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    // Câu 6: Nút Enroll
    @GetMapping("/course/{id}")
    public String enrollCourse(@PathVariable("id") Long courseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(auth.getName());
        Course course = courseRepository.findById(courseId).orElse(null);

        if (student != null && course != null) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnroll_date(LocalDate.now());
            enrollmentRepository.save(enrollment);
        }
        return "redirect:/enroll/my-courses";
    }

    // Câu 7: Trang My Courses
    @GetMapping("/my-courses")
    public String myCourses(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(auth.getName());

        if (student != null) {
            List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);
            model.addAttribute("enrollments", enrollments);
        }
        return "my-courses";
    }
}
package com.example.BaiKT.service;

import com.example.BaiKT.model.Course;
import com.example.BaiKT.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    // Phân trang: 5 học phần 1 trang theo đúng đề bài
    public Page<Course> findPaginated(int pageNo) {
        int pageSize = 5;
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return courseRepository.findAll(pageable);
    }
}
package com.example.BaiKT.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private int credits;
    private String lecturer;

    // Khóa ngoại liên kết với bảng Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
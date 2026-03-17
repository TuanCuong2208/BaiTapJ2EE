package com.example.BaiKT.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id; // Đặt đúng tên theo yêu cầu đề bài

    private String name;
}
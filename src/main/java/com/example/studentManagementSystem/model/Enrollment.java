package com.example.studentManagementSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name="enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="student_id",nullable = false)
    private Students student;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="course_id",nullable = false)
    private Course course;

    @Column(nullable = false)
    private LocalDateTime enrolledDate = LocalDateTime.now();
}

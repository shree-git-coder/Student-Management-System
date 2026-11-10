package com.example.studentManagementSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false,unique = true)
    private String courseCode;

    private String duration;

    @Column(name="active", nullable = false)
    private boolean active= true;

    @Column(precision =12,  scale= 2 ,nullable = false)
    private BigDecimal fee;

    @Column(length =200)
    private String description;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @OneToMany (mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment>enrollments=new HashSet<>();

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
    }

}

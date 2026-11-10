package com.example.studentManagementSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    @Column(length = 500)
    private String address;

    @Column(name="active",nullable = false)
    private boolean active= true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany (mappedBy = "student",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments = new HashSet<>();

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
        active= true;
    }

}

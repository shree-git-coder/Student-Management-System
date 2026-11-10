package com.example.studentManagementSystem.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
public class CourseDto {

    private Long id;

    @NotBlank(message = " coursename is required ")
    @Size(max = 150,message = "max 150 characters is allowed")
    private String courseName;

    @NotBlank(message = " coursecode is required ")
    private String courseCode;

    @Size(max = 500,message = "max 500 characters is allowed")
    private String duration;

    @NotNull(message = "fee is required ")
    private BigDecimal fee;

    private String description;

    private  boolean active;
}

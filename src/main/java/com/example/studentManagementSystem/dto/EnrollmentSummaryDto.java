package com.example.studentManagementSystem.dto;

import com.example.studentManagementSystem.model.Course;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EnrollmentSummaryDto {
    private Long studentId;
    private String studentName;
    private String email;
    private int courseCount;
    private BigDecimal totalFee;
    private List<CourseDto> courseList=new ArrayList<>();

}

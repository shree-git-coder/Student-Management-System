package com.example.studentManagementSystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardStatsDto {
    private long totalStudents;
    private long totalCourses;
    private String topPerformingCourse;
    private long studentsEnrolledThisMonth;

}

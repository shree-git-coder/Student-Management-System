package com.example.studentManagementSystem.service.serviceImpl;

import com.example.studentManagementSystem.dto.enrollmentDto;
import com.example.studentManagementSystem.dto.EnrollmentSummaryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EnrollmentService {
    void enrollStudentToCourses(enrollmentDto enrollmentDto);

    Page<EnrollmentSummaryDto> getEnrolledStudents(int page , int size);
   EnrollmentSummaryDto findEnrolledStudentCourseDetails(Long studentId);
   List<EnrollmentSummaryDto> getRecentlyEnrolledStudents();

}

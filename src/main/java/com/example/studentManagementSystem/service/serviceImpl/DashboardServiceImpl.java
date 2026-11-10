package com.example.studentManagementSystem.service.serviceImpl;

import com.example.studentManagementSystem.dto.DashboardStatsDto;
import com.example.studentManagementSystem.repository.CourseRepository;
import com.example.studentManagementSystem.repository.EnrollmentRepository;
import com.example.studentManagementSystem.repository.StudentsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@Transactional
public class DashboardServiceImpl implements DashboardService{
    private EnrollmentRepository enrollmentRepository;
    private CourseRepository courseRepository;
    private StudentsRepository studentsRepository;

    public DashboardServiceImpl(EnrollmentRepository enrollmentRepository,
                                CourseRepository courseRepository,
                                StudentsRepository studentsRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentsRepository = studentsRepository;
    }

    @Override
    public DashboardStatsDto getDashboardStats() {
     long totalStudents=studentsRepository.count();
     long totalCourse= courseRepository.count();

     String topPerformingCourse= getTopPerformingCourse();

        YearMonth currentMonth= YearMonth.now();
        LocalDateTime startDate= currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate=currentMonth.atEndOfMonth().atTime(LocalTime.MAX);
     long studentEnrolledThisMonth= enrollmentRepository.countDistinctStudentEnrollDateBetween(startDate,endDate);

     DashboardStatsDto dashboardStatsDto= new DashboardStatsDto();
     dashboardStatsDto.setTotalStudents(totalStudents);
     dashboardStatsDto.setTotalCourses(totalCourse);
     dashboardStatsDto.setTopPerformingCourse(topPerformingCourse);
     dashboardStatsDto.setStudentsEnrolledThisMonth(studentEnrolledThisMonth);

     return dashboardStatsDto;
    }
    private String getTopPerformingCourse(){
        return enrollmentRepository.findAll()
                .stream().collect(Collectors.groupingBy(e->e.getCourse().getCourseCode(),Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()) //(java,6) (react,5) (angular,4)
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}

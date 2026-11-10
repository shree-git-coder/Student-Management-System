package com.example.studentManagementSystem.service.serviceImpl;

import com.example.studentManagementSystem.dto.CourseDto;
import com.example.studentManagementSystem.dto.enrollmentDto;
import com.example.studentManagementSystem.dto.EnrollmentSummaryDto;
import com.example.studentManagementSystem.model.Course;
import com.example.studentManagementSystem.model.Enrollment;
import com.example.studentManagementSystem.model.Students;
import com.example.studentManagementSystem.repository.CourseRepository;
import com.example.studentManagementSystem.repository.EnrollmentRepository;
import com.example.studentManagementSystem.repository.StudentsRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {
    private static final Logger log = LoggerFactory.getLogger(StudentsServiceImpl.class);

    private final EnrollmentRepository enrollmentRepository;
    private final StudentsRepository studentsRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 StudentsRepository studentsRepository,
                                 CourseRepository courseRepository,
                                 ModelMapper mapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentsRepository = studentsRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public void enrollStudentToCourses(enrollmentDto enrollmentDto) {
        log.info("request from enrollStudentToCourses");
        Students student = studentsRepository.findById(enrollmentDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("student not found"));

        for (Long courseId : enrollmentDto.getCourseIds()) {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("student not found"));

            if (enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDto.getStudentId(), courseId)) {
                continue;
            }
            Enrollment enrollment = new Enrollment();
            enrollment.setCourse(course);
            enrollment.setStudent(student);

            enrollmentRepository.save(enrollment);
        }
    }

    @Override
    public Page<EnrollmentSummaryDto> getEnrolledStudents(int page, int size) {
        log.info("list of enrolled students from :", page);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return studentsRepository.findEnrolledStudents(pageRequest)
                .map(student -> {
                    EnrollmentSummaryDto dto = new EnrollmentSummaryDto();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName() + " " + student.getLastName());
                    dto.setEmail(student.getEmail());
                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream().
                            map(enrollment -> enrollment.getCourse().getFee()).
                            filter(fee -> fee != null).reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);
                    return dto;
                });


    }

    @Override
    public EnrollmentSummaryDto findEnrolledStudentCourseDetails(Long studentId) {

        return studentsRepository.findEnrolledStudentCourseDetails(studentId)
                .map(student -> {
                    EnrollmentSummaryDto dto = new EnrollmentSummaryDto();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName() + " " + student.getLastName());
                    dto.setEmail(student.getEmail());
                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream().
                            map(enrollment -> enrollment.getCourse().getFee()).
                            filter(fee -> fee != null).reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);

                    List<CourseDto> courseList = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse())
                            .map(course -> mapper.map(course, CourseDto.class))
                            .collect(Collectors.toList());
                    dto.setCourseList(courseList);
                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("student not found"));
    }

    @Override
    public List<EnrollmentSummaryDto> getRecentlyEnrolledStudents() {
        log.info("list of enrolled students ");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        return studentsRepository.findEnrolledStudents(pageRequest)
                .map(student -> {
                    EnrollmentSummaryDto dto = new EnrollmentSummaryDto();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName() + " " + student.getLastName());
                    dto.setEmail(student.getEmail());
                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream().
                            map(enrollment -> enrollment.getCourse().getFee()).
                            filter(fee -> fee != null).reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);
                    return dto;
                })
                .getContent();
    }
}

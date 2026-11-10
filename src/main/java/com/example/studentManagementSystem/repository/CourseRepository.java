package com.example.studentManagementSystem.repository;

import com.example.studentManagementSystem.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCourseCodeIgnoreCase(String code);

   Page<Course> findByActiveTrue(Pageable pageable);

    boolean existsByCourseCodeIgnoreCaseAndIdNot(String code, Long id);
    List<Course> findByActiveTrue(Sort sort);
}

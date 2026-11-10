package com.example.studentManagementSystem.service.serviceImpl;

import com.example.studentManagementSystem.dto.CourseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    CourseDto createCourse(CourseDto courseDto);
   boolean existsByCourseCode(String code);
   boolean  existsByCourseCodeAndIdNot(String code, Long Id);

  Page<CourseDto> getCourse(int page , int size);
  CourseDto getCourseById(Long id);

    CourseDto updateCourse( Long id ,CourseDto courseDto);
    List<CourseDto>getAllCourse();


}

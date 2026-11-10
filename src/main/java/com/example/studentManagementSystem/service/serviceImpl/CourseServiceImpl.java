package com.example.studentManagementSystem.service.serviceImpl;

import com.example.studentManagementSystem.controller.CourseController;
import com.example.studentManagementSystem.dto.CourseDto;
import com.example.studentManagementSystem.model.Course;
import com.example.studentManagementSystem.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.modelmapper.Converters.Collection.map;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{
   private final CourseRepository courseRepository;
   private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        log.info("creating course with code: {}",courseDto.getCourseCode());

        Course course = modelMapper.map(courseDto,Course.class);
        courseRepository.save(course);
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public boolean existsByCourseCode(String code) {
        log.info("checking if code exists: {}",code);
        return courseRepository.existsByCourseCodeIgnoreCase( code);
    }

    @Override
    public boolean existsByCourseCodeAndIdNot(String code, Long id) {
        log.info("list of updated  courses from : {}, id: {}, code , id ");
       return courseRepository.existsByCourseCodeIgnoreCaseAndIdNot(code,id);

    }

    @Override
    public Page<CourseDto> getCourse(int page, int size) {
        log.info("list of courses from :",page);
        PageRequest pageRequest= PageRequest.of(page, size, Sort.by(Sort.Direction.DESC , "id"));
        return courseRepository.findByActiveTrue(pageRequest)
                .map(course -> modelMapper.map(course , CourseDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto getCourseById(Long id) {
       Course course= courseRepository.findById( id)
               .orElseThrow(()-> new RuntimeException("course not found"));

        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Course course= courseRepository.findById( id)
                .orElseThrow(()-> new RuntimeException("course not found"));
        modelMapper.map(courseDto, course);
     Course updated = courseRepository.save(course);
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public List<CourseDto> getAllCourse() {
        return courseRepository.findByActiveTrue(Sort.by("courseName")).stream()
                .map(course->modelMapper.map(course,CourseDto.class))
                .collect(Collectors.toList());

    }

}
package com.example.studentManagementSystem.service.serviceImpl;

import com.example.studentManagementSystem.dto.studentDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface StudentsService {

    boolean existsByEmailIgnoreCase(String email);
    boolean  existsByEmailIgnoreCaseAndIdNot(String email, Long Id);
    studentDto createStudent(studentDto studentsDto);
    Page<studentDto> getStudent(int page , int size);
    studentDto getStudentById(Long id);

   studentDto updateStudent(Long id , studentDto studentDto);
    List<studentDto> getAllStudent();


}

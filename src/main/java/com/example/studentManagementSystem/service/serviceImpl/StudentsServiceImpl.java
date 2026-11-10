package com.example.studentManagementSystem.service.serviceImpl;

import com.example.studentManagementSystem.dto.studentDto;
import com.example.studentManagementSystem.model.Students;
import com.example.studentManagementSystem.repository.StudentsRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentsServiceImpl implements StudentsService{
    private static final Logger log = LoggerFactory.getLogger(StudentsServiceImpl.class);
    private final StudentsRepository studentsRepository;
    private final ModelMapper mapper;

    public StudentsServiceImpl(StudentsRepository studentsRepository, ModelMapper mapper) {
        this.studentsRepository = studentsRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        log.info("email from create student");
        return studentsRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id) {
        log.info("email from update student");
        return studentsRepository.existsByEmailIgnoreCaseAndIdNot(email,id);
    }

    @Override
    public studentDto createStudent(studentDto studentsDto) {
        Students students = mapper.map(studentsDto, Students.class);
        Students saved = studentsRepository.save(students);
        return mapper.map(saved , studentDto.class);
    }

    @Override
    public Page<studentDto> getStudent(int page, int size) {
        log.info("list of students from :",page);
        PageRequest pageRequest= PageRequest.of(page, size, Sort.by(Sort.Direction.DESC , "id"));
        return studentsRepository.findByActiveTrue(pageRequest)
                .map(students -> mapper.map(students , studentDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public studentDto getStudentById(Long id) {
        Students student=studentsRepository.findById(id).
                           orElseThrow(()-> new RuntimeException("No student found"));
        return mapper.map(student, studentDto.class);
    }

    @Override
    public studentDto updateStudent(Long id, studentDto studentDto) {
      Students student = studentsRepository.findById(id).
                orElseThrow(()->new RuntimeException("student not found"));
      mapper.map(studentDto, student);
      Students updated =studentsRepository.save(student);
      return mapper.map(updated, studentDto.class);
    }
    @Override
    public List<studentDto> getAllStudent() {
        return studentsRepository.findByActiveTrue().stream()
                .map(students -> mapper.map(students, studentDto.class))
                .collect(Collectors.toList());
    }

}

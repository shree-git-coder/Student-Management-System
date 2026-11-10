package com.example.studentManagementSystem.controller;

import com.example.studentManagementSystem.dto.studentDto;
import com.example.studentManagementSystem.service.serviceImpl.StudentsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentsService studentsService;

    public StudentController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping("/new")
    private String showCreateStudent(Model model) {
        log.info("Get/new-showing create student page");
        model.addAttribute("studentDto", new studentDto());
        return "add-student";
    }

    @GetMapping("/list")
    private String listStudent(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "3")int size,
                                Model model,
                                @RequestParam(value = "message",required = false) String message) {

        log.info("Get/list-showing list student page");
        Page<studentDto>students= studentsService.getStudent(page,size);
        model.addAttribute("students" ,students);
        model.addAttribute("message", message);
        return "students";
    }

    @PostMapping("/save")
    private String createStudent(@Valid @ModelAttribute("studentDto") studentDto studentsDto,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "add-student";
        }
        if (studentsService.existsByEmailIgnoreCase(studentsDto.getEmail())) {
            log.info("Post/save-email must be unique");
            bindingResult.rejectValue("email", null, "email must be unique");
            return "add-student";

        }
        studentsService.createStudent(studentsDto);
        redirectAttributes.addAttribute("message", "student is added successfully");
        return "redirect:/students/list";
    }



    @GetMapping("/{id}")
    public String getStudentById(@PathVariable Long id, Model model) {
        studentDto student = studentsService.getStudentById(id);
        model.addAttribute("student", student);
        return "view-student";
    }

    @GetMapping("/{id}/edit")
    public String editStudent(@PathVariable Long id, Model model) {
        studentDto student = studentsService.getStudentById(id);
        model.addAttribute("studentDto", student);
        return "edit-student";
    }

    @PostMapping("/{id}/update")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute("studentDto") studentDto studentDto,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        log.info("Post/{id}/update- update student request received");
        if (bindingResult.hasErrors()) {
            log.error("Post/{id}/update-page return due to validation error");
            return "edit-student";
        }
        if (studentsService.existsByEmailIgnoreCaseAndIdNot(studentDto.getEmail(), id)) {
            bindingResult.rejectValue("studentDto", null, "email must be unique");
            log.error("Post/{id}/update- email must be unique");
            return "edit-student";
        }
        studentsService.updateStudent(id, studentDto);
        redirectAttributes.addAttribute("message", "student is updated successfully");
        log.info("Post/{id}/update- updated student successfully");
        return "redirect:/students/list";

    }
}

package com.example.studentManagementSystem.controller;

import com.example.studentManagementSystem.dto.CourseDto;
import com.example.studentManagementSystem.exception.GlobalException;
import com.example.studentManagementSystem.service.serviceImpl.CourseService;
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
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/new")
    public String showCreateCourse(Model model) {
        model.addAttribute("courseDto", new CourseDto());
        log.info("Get/course/new- showing create course page");
        return "add-course";
    }

    @GetMapping("/list")
    public String listCourse(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size,
                             Model model,
                             @RequestParam(value = "message", required = false) String message) {
        log.info("Get/course/list- showing course list");
        Page<CourseDto> courses = courseService.getCourse(page, size);
        model.addAttribute("courses", courses);
        model.addAttribute("message", message);
        return "courses";

    }

    @PostMapping
    public String createCourse(@Valid @ModelAttribute("courseDto") CourseDto courseDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        log.info("Post/course- create course request received");
        if (bindingResult.hasErrors()) {
            log.error("Post/course-page return due to validation error");
            return "add-course";
        }
        if (courseService.existsByCourseCode(courseDto.getCourseCode())) {
            bindingResult.rejectValue("courseCode", null, "code must be unique");
            log.error("Post/course- course must be unique");
            return "add-course";
        }
        courseService.createCourse(courseDto);
        redirectAttributes.addAttribute("message", "course is created successfully");
        log.info("Post/course- create course succesfully created");
        return "redirect:/course/list";


    }

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id, Model model) {
        CourseDto course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "view-course";

    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id, Model model) {
        CourseDto course = courseService.getCourseById(id);
        model.addAttribute("courseDto", course);
        return "edit-course";
    }

    @PostMapping("/{id}/update")
    public String updateCourse(@PathVariable Long id ,@Valid @ModelAttribute("courseDto") CourseDto courseDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){
        log.info("Post/{id}/update- update course request received");
        if (bindingResult.hasErrors()) {
            log.error("Post/{id}/update-page return due to validation error");
            return "edit-course";
        }
        if (courseService.existsByCourseCodeAndIdNot(courseDto.getCourseCode(),id)) {
            bindingResult.rejectValue("courseCode", null, "code must be unique");
            log.error("Post/{id}/update- course must be unique");
            return "edit-course";
        }
        courseService.updateCourse( id, courseDto);
        redirectAttributes.addAttribute("message", "course is created successfully");
        log.info("Post/{id}/update- updated course successfully");
        return "redirect:/course/list";

    }
}

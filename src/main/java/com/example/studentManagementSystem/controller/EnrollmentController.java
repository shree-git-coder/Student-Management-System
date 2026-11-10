package com.example.studentManagementSystem.controller;

import com.example.studentManagementSystem.dto.enrollmentDto;
import com.example.studentManagementSystem.dto.EnrollmentSummaryDto;
import com.example.studentManagementSystem.service.serviceImpl.CourseService;
import com.example.studentManagementSystem.service.serviceImpl.EnrollmentService;
import com.example.studentManagementSystem.service.serviceImpl.StudentsService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EnrollmentController.class);
    private final CourseService courseService;
    private final StudentsService studentsService;
    private final EnrollmentService enrollmentService;

    public EnrollmentController(CourseService courseService,
                                StudentsService studentsService,
                                EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.studentsService = studentsService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/showEnroll")
    public String showEnroll(Model model) {
        model.addAttribute("enrollmentDto", new enrollmentDto());
        model.addAttribute("courseList", courseService.getAllCourse());
        model.addAttribute("studentList",studentsService.getAllStudent());
        log.info("Get/showEnroll- showing enrollment page");
        return "enroll-course";
    }

     @GetMapping("/enrollmentList")
    public String enrollmentList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "3")int size,
                                 Model model,
                                 @RequestParam(value = "message",required = false) String message) {

         log.info("Get/enrollments/enrollmentlist-showing  enrolled students list page");
         Page<EnrollmentSummaryDto> students= enrollmentService.getEnrolledStudents(page,size);
         model.addAttribute("students" ,students);
         model.addAttribute("message", message);
         return "enroll-students";
     }

    @PostMapping("/enrollCourse")
    public String enrollCourse(@Valid @ModelAttribute("enrollmentDto") enrollmentDto enrollmentDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){
        log.info("Post/enrollCourse-enrollment request recieved");
        if(bindingResult.hasErrors()){
            model.addAttribute("courseList", courseService.getAllCourse());
            model.addAttribute("studentList",studentsService.getAllStudent());
            return "enroll-course";
        }
        enrollmentService.enrollStudentToCourses(enrollmentDto);
        redirectAttributes.addAttribute("message", "enrollment successful");
        log.info("post/enrollments/enrollCourse- student is created");
        return "redirect:/enrollments/enrollmentList";

    }
    @GetMapping("/getStudentEnrollmentDetails/{id}")
    public String getStudentEnrollmentDetails(@PathVariable Long id, Model model,
                                              @RequestParam(defaultValue = "enrollments")String source){
      EnrollmentSummaryDto enrollmentSummaryDto =enrollmentService.findEnrolledStudentCourseDetails(id);
      model.addAttribute("enrollmentSummaryDto", enrollmentSummaryDto);
      model.addAttribute("source" ,source);

        return "enrollment-details";
    }

}
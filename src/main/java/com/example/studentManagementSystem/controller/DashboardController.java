package com.example.studentManagementSystem.controller;

import com.example.studentManagementSystem.service.serviceImpl.DashboardService;
import com.example.studentManagementSystem.service.serviceImpl.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class DashboardController {
    private final EnrollmentService enrollmentService;
    private final DashboardService dashboardService;

    public DashboardController(EnrollmentService enrollmentService, DashboardService dashboardService) {
        this.enrollmentService = enrollmentService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("dashboardStats" ,dashboardService.getDashboardStats());
        model.addAttribute("students" ,enrollmentService.getRecentlyEnrolledStudents());
        return "dashboard";

    }
}

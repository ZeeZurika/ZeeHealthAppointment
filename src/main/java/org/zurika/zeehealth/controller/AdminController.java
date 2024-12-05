package org.zurika.zeehealth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zurika.zeehealth.service.*;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("appointments", appointmentService.getAllAppointments(PageRequest.of(page, 10)).getContent());
        return "admin-dashboard";
    }
}


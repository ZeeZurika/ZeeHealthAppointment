package org.zurika.zeehealth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.zurika.zeehealth.model.User;
import org.zurika.zeehealth.service.*;

@Controller
public class DoctorController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User doctor = userService.getByUsername(userDetails.getUsername());
        model.addAttribute("appointments", appointmentService.getDoctorAppointments(doctor.getId(), PageRequest.of(0, 10)));
        return "doctor-dashboard";
    }
}


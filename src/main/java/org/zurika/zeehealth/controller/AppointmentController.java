package org.zurika.healthappointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zurika.healthappointment.model.Appointment;
import org.zurika.healthappointment.service.AppointmentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // View all appointments (for admin or reporting purposes)
    @GetMapping("/appointments")
    public String viewAllAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "admin-dashboard"; // Thymeleaf template for viewing appointments
    }

    // View appointments by status
    @GetMapping("/appointments/status")
    public String viewAppointmentsByStatus(@RequestParam String status, Model model) {
        List<Appointment> appointments = appointmentService.getAppointmentsByStatus(status);
        model.addAttribute("appointments", appointments);
        return "admin-dashboard"; // Reuse or create a specific template
    }

    // View appointments by date range
    @GetMapping("/appointments/date-range")
    public String viewAppointmentsByDateRange(@RequestParam String startDate,
                                              @RequestParam String endDate,
                                              Model model) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atStartOfDay();
        List<Appointment> appointments = appointmentService.getAppointmentsByDateRange(start, end);
        model.addAttribute("appointments", appointments);
        return "admin-dashboard";
    }
}

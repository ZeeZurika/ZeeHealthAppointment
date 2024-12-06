package org.zurika.zeehealth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zurika.zeehealth.model.Appointment;
import org.zurika.zeehealth.model.AppointmentStatus;
import org.zurika.zeehealth.model.User;
import org.zurika.zeehealth.service.*;

import java.time.LocalDateTime;

@Controller
public class DoctorController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(Model model,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        User doctor = userService.getByUsername(userDetails.getUsername());
        Page<Appointment> appointmentsPage = appointmentService.getDoctorAppointments(doctor.getId(), PageRequest.of(page, size));

        model.addAttribute("appointments", appointmentsPage.getContent());
        model.addAttribute("totalPages", appointmentsPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "doctor-dashboard";
    }


    // Update the status of an appointment (e.g., Confirm, Complete, Cancel, Reschedule)
    @PostMapping("/doctor/updateAppointment")
    public String updateAppointment(@RequestParam Long appointmentId,
                                    @RequestParam String status,
                                    @RequestParam(required = false) String newDate,
                                    Model model) {
        try {
            if ("RESCHEDULED".equalsIgnoreCase(status) && newDate != null) {
                LocalDateTime rescheduleDate = LocalDateTime.parse(newDate);
                appointmentService.rescheduleAppointment(appointmentId, rescheduleDate);
            } else {
                appointmentService.updateAppointmentStatus(appointmentId, AppointmentStatus.valueOf(status.toUpperCase()));
            }
            model.addAttribute("successMessage", "Appointment updated successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating appointment: " + e.getMessage());
        }
        return "redirect:/doctor/dashboard";
    }
}


package org.zurika.zeehealth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.zurika.zeehealth.model.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private AppointmentService appointmentService;

    public String generateReport(String reportType) {
        return switch (reportType.toLowerCase()) {
            case "appointments-by-patient" -> generateAppointmentsByPatientReport();
            case "appointments-by-doctor" -> generateAppointmentsByDoctorReport();
            default -> throw new IllegalArgumentException("Invalid report type: " + reportType);
        };
    }

    private String generateAppointmentsByPatientReport() {
        // Fetch all appointments (no pagination for reports)
        List<Appointment> appointments = appointmentService.getAllAppointments(PageRequest.of(0, Integer.MAX_VALUE)).getContent();

        // Group appointments by patient ID
        Map<Long, List<Appointment>> groupedByPatient = appointments.stream()
                .filter(a -> a.getPatient() != null) // Ensure patient is not null
                .collect(Collectors.groupingBy(appointment -> appointment.getPatient().getId()));

        // Build the report
        StringBuilder report = new StringBuilder("Appointments by Patient:\n");
        groupedByPatient.forEach((patientId, patientAppointments) -> {
            report.append("Patient ID: ").append(patientId).append("\nAppointments:\n");
            patientAppointments.forEach(appointment -> report.append("- Date: ").append(appointment.getAppointmentDate())
                    .append(", Status: ").append(appointment.getStatus()).append("\n"));
        });

        return report.toString();
    }

    private String generateAppointmentsByDoctorReport() {
        // Fetch all appointments (no pagination for reports)
        List<Appointment> appointments = appointmentService.getAllAppointments(PageRequest.of(0, Integer.MAX_VALUE)).getContent();

        // Group appointments by doctor ID
        Map<Long, List<Appointment>> groupedByDoctor = appointments.stream()
                .filter(a -> a.getDoctor() != null) // Ensure doctor is not null
                .collect(Collectors.groupingBy(appointment -> appointment.getDoctor().getId()));

        // Build the report
        StringBuilder report = new StringBuilder("Appointments by Doctor:\n");
        groupedByDoctor.forEach((doctorId, doctorAppointments) -> {
            report.append("Doctor ID: ").append(doctorId).append("\nAppointments:\n");
            doctorAppointments.forEach(appointment -> report.append("- Date: ").append(appointment.getAppointmentDate())
                    .append(", Status: ").append(appointment.getStatus()).append("\n"));
        });

        return report.toString();
    }
}

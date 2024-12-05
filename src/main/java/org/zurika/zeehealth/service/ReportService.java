package org.zurika.healthappointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zurika.healthappointment.model.Appointment;

import java.util.List;
import java.util.Map;
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
        List<Appointment> appointments = appointmentService.getAllAppointments();

        Map<Long, List<Appointment>> groupedByPatient = appointments.stream()
                .collect(Collectors.groupingBy(appointment -> appointment.getPatient().getId()));

        StringBuilder report = new StringBuilder("Appointments by Patient:\n");
        groupedByPatient.forEach((patientId, patientAppointments) -> {
            report.append("Patient ID: ").append(patientId).append("\nAppointments:\n");
            patientAppointments.forEach(appointment -> report.append("- Date: ").append(appointment.getAppointmentDate())
                    .append(", Status: ").append(appointment.getStatus()).append("\n"));
        });

        return report.toString();
    }

    private String generateAppointmentsByDoctorReport() {
        List<Appointment> appointments = appointmentService.getAllAppointments();

        Map<Long, List<Appointment>> groupedByDoctor = appointments.stream()
                .collect(Collectors.groupingBy(appointment -> appointment.getDoctor().getId()));

        StringBuilder report = new StringBuilder("Appointments by Doctor:\n");
        groupedByDoctor.forEach((doctorId, doctorAppointments) -> {
            report.append("Doctor ID: ").append(doctorId).append("\nAppointments:\n");
            doctorAppointments.forEach(appointment -> report.append("- Date: ").append(appointment.getAppointmentDate())
                    .append(", Status: ").append(appointment.getStatus()).append("\n"));
        });

        return report.toString();
    }
}

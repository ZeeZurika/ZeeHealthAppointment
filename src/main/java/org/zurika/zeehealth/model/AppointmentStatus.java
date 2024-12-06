package org.zurika.zeehealth.model;

public enum AppointmentStatus {
    PENDING,
    CONFIRMED,     // Appointment is confirmed
    CANCELED,      // Appointment was canceled
    COMPLETED,     // Appointment is completed
    RESCHEDULED    // Appointment has been rescheduled
}

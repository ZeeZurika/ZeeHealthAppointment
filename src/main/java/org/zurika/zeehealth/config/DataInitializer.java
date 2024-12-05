package org.zurika.healthappointment.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.zurika.healthappointment.model.User;
import org.zurika.healthappointment.model.UserRole;
import org.zurika.healthappointment.repository.UserRepository;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Initialize default data on application startup.
     */
    @PostConstruct
    public void initData() {
        // Create default admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Hash the password
            admin.setRole(UserRole.ADMIN);
            admin.setFirstName("FirstAdmin");
            admin.setLastName("LastAdmin");
            userRepository.save(admin);
        }

        // Create default doctor user if not exists
        if (!userRepository.existsByUsername("doctor")) {
            User doctor = new User();
            doctor.setUsername("doctor");
            doctor.setEmail("doctor@example.com");
            doctor.setPassword(passwordEncoder.encode("doctor123")); // Hash the password
            doctor.setRole(UserRole.DOCTOR);
            doctor.setFirstName("FirstDoctor");
            doctor.setLastName("LastDoctor");
            userRepository.save(doctor);
        }

        // Create default patient user if not exists
        if (!userRepository.existsByUsername("patient")) {
            User patient = new User();
            patient.setUsername("patient");
            patient.setEmail("patient@example.com");
            patient.setPassword(passwordEncoder.encode("patient123")); // Hash the password
            patient.setRole(UserRole.PATIENT);
            patient.setFirstName("FirstPatient");
            patient.setLastName("LastPatient");
            userRepository.save(patient);
        }
    }
}

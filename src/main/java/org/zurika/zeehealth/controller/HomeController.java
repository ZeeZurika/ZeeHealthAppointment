package org.zurika.healthappointment.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Login page
    @GetMapping("/")
    public String showLoginPage() {
        return "login"; // HTML page for login
    }

    // Redirect to the appropriate dashboard after login
    @GetMapping("/redirect-dashboard")
    public String redirectDashboard() {
        // Get the currently logged-in user's role
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().findFirst().orElseThrow().getAuthority();

        switch (role) {
            case "ROLE_ADMIN":
                return "redirect:/admin/dashboard";
            case "ROLE_DOCTOR":
                return "redirect:/doctor/dashboard";
            case "ROLE_PATIENT":
                return "redirect:/patient/dashboard";
            default:
                return "redirect:/?error=true"; // Redirect to login if the role is invalid
        }
    }
}

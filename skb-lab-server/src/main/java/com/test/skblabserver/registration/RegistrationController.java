package com.test.skblabserver.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final RegistrationService registrationService;
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("registration")
    public String registration(@Valid @ModelAttribute UserData userData,
                               Model model) {
        var status = registrationService.saveUser(userData);
        model.addAttribute("message", status.getDescription());
        return "registration";
    }
}

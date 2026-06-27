package com.paymybuddy.integration;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.UserConnectionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final UserConnectionService userConnectionService;
    private final UserRepository userRepository;

    public HomeController(UserConnectionService userConnectionService,
                          UserRepository userRepository) {
        this.userConnectionService = userConnectionService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/add-connection")
    public String addConnection() {
        return "add-connection";
    }

    @PostMapping("/add-connection")
    public String addConnection(
            Authentication authentication,
            @RequestParam String email) {

        String currentUserEmail = authentication.getName();

        userConnectionService.addConnection(
                currentUserEmail,
                email);

        return "redirect:/add-connection";
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        model.addAttribute("user", user);

        return "profile";
    }
}
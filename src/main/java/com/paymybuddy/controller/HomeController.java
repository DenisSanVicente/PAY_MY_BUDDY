package com.paymybuddy.controller;

import com.paymybuddy.service.UserConnectionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@Controller
public class HomeController {

    private final UserConnectionService userConnectionService;

    public HomeController(UserConnectionService userConnectionService) {
        this.userConnectionService = userConnectionService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/add-connection")
    public String addConnection() {
        return "add-connection";
    }

    @PostMapping("/add-connection")
    public String addConnection(
            Authentication authentication,
            @RequestParam String email) {

        String currentUserEmail =
                authentication.getName();

        userConnectionService.addConnection(
                currentUserEmail,
                email);

        return "redirect:/add-connection";
    }


    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}


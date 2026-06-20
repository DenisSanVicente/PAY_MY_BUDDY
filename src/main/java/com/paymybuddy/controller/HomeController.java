package com.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

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

    @GetMapping("/transfer")
    public String transfer() {
        return "transfer";
    }

    @GetMapping("/add-connection")
    public String addConnection() {
        return "add-connection";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}


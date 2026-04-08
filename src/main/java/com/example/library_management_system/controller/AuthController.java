package com.example.library_management_system.controller;

import com.example.library_management_system.model.LibraryUser;
import com.example.library_management_system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        LibraryUser user = userService.login(email, password);

        if (user != null) {
            return "redirect:/books";
        }

        model.addAttribute("error", "Invalid email or password");
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new LibraryUser());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") LibraryUser user) {
        user.setRole("USER");
        userService.registerUser(user);
        return "redirect:/login";
    }
}
package com.example.library_management_system.controller;

import com.example.library_management_system.model.LibraryUser;
import com.example.library_management_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        LibraryUser user = userService.login(email, password);

        if (user == null) {
            model.addAttribute("error", "Invalid email or password");
            return "auth/login";
        }

        session.setAttribute("loggedUser", user);
        return "redirect:/books";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new LibraryUser());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") LibraryUser user, Model model) {
        user.setRole("USER");

        LibraryUser savedUser = userService.registerUser(user);

        if (savedUser == null) {
            model.addAttribute("error", "Email already exists");
            return "auth/register";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
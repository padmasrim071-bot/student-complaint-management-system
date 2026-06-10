package com.example.project.controller;

import com.example.project.dto.LoginRequest;
import com.example.project.dto.LoginResponse;
import com.example.project.dto.ProfileRequest;
import com.example.project.dto.RegisterRequest;
import com.example.project.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.example.project.entity.User;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {
        return userService.verifyEmail(token);
    }
    @PutMapping("/profile/{id}")
    public User updateProfile(
            @PathVariable Long id,
            @RequestBody ProfileRequest request) {

        return userService.updateProfile(
                id,
                request);
    }
    @GetMapping("/forgot-password")
    public String forgotPassword(
            @RequestParam String email){

        return userService
                .forgotPassword(email);
    }
    @GetMapping("/profile/{id}")
    public User getProfile(
            @PathVariable Long id){

        return userService.getProfile(id);
    }
}
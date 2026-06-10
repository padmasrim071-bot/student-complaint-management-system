package com.example.project.service;

import com.example.project.dto.LoginRequest;
import com.example.project.dto.LoginResponse;
import com.example.project.dto.RegisterRequest;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import com.example.project.security.JwtUtil;
import org.springframework.stereotype.Service;
import com.example.project.dto.ProfileRequest;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       JwtUtil jwtUtil,
                       EmailService emailService) {

        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole("STUDENT");

        user.setVerified(false);

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);

        userRepository.save(user);

        emailService.sendVerificationEmail(user.getEmail(), token);

        return "Registration Successful. Please verify your email.";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return new LoginResponse(
                    "User Not Found",
                    null,
                    null,
                    null
            );
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return new LoginResponse(
                    "Invalid Password",
                    null,
                    null,
                    null
            );
        }

        if (!user.isVerified()) {
            return new LoginResponse(
                    "Please verify your email before login",
                    null,
                    null,
                    null
            );
        }

        String token =
                jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new LoginResponse(
                "Login Successful",
                user.getRole(),
                user.getId(),
                token
        );
    }

    public String verifyEmail(String token) {

        User user = userRepository
                .findByVerificationToken(token)
                .orElse(null);

        if (user == null) {
            return "Invalid verification token";
        }

        user.setVerified(true);
        user.setVerificationToken(null);

        userRepository.save(user);

        return "Email verified successfully. You can login now.";
    }
    public User updateProfile(Long userId,
                              ProfileRequest request) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow();

        user.setDepartment(
                request.getDepartment());

        user.setYear(
                request.getYear());

        user.setPhoneNumber(
                request.getPhoneNumber());

        return userRepository.save(user);
    }
    public String forgotPassword(String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElse(null);

        if(user == null) {
            return "Email Not Found";
        }

        return "Your Password is: "
                + user.getPassword();
    }
    public User getProfile(Long id){

        return userRepository
                .findById(id)
                .orElseThrow();
    }
}
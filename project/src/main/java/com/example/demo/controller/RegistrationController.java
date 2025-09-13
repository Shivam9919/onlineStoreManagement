package com.example.demo.controller;

import com.example.demo.entity.Registration;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.RegistrationRequest;
import com.example.demo.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
@Tag(name = "Registration API", description = "Endpoints for user registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    @Operation(summary = "Register a new user", description = "Stores user details in database")
    public ApiResponse<Registration> register(@Valid @RequestBody RegistrationRequest request) {
        Registration savedUser = registrationService.registerUser(request);
        return new ApiResponse<>(200, "SUCCESS", "User registered successfully", savedUser);
    }
}

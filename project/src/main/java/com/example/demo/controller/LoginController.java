package com.example.demo.controller;

import com.example.demo.model.ApiResponse;
import com.example.demo.model.OtpRequestDto;
import com.example.demo.model.VerifyOtpDto;
import com.example.demo.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@Tag(name = "Authentication API", description = "Endpoints for login using OTP verification")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * Generate OTP based on email or phone number.
     * - If user provides email → OTP will be generated, stored, and sent to that email.
     * - If user provides phone number → system finds the linked email and sends OTP there.
     */
    @PostMapping("/login-generate-otp")
    @Operation(summary = "Generate OTP for login",
               description = "Generates a 6-digit OTP and sends it to the user's registered email. "
                           + "User can provide either email or phone number.")
    public ApiResponse<String> generateOtp(@RequestBody OtpRequestDto request) {
        return loginService.generateOtp(request);
    }

    /**
     * Verify the OTP entered by the user.
     * - If user provides email → system verifies OTP against that email.
     * - If user provides phone number → system finds the linked email and verifies OTP.
     */
    @PostMapping("/verify-otp")
    @Operation(summary = "Verify user OTP",
               description = "Verifies the OTP entered by the user. "
                           + "Login succeeds only if the OTP matches the stored value.")
    public ApiResponse<String> verifyOtp(@RequestBody VerifyOtpDto request) {
        return loginService.verifyOtp(request);
    }
}

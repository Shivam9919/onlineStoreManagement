package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpDto {
    private String email;
    private String phoneNo;
    private String otp;
    // getters and setters
}

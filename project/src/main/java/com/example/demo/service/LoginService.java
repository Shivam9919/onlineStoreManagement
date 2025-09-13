package com.example.demo.service;

import com.example.demo.model.ApiResponse;
import com.example.demo.model.OtpRequestDto;
import com.example.demo.model.VerifyOtpDto;

public interface LoginService {

	ApiResponse<String> generateOtp(OtpRequestDto request);
    ApiResponse<String> verifyOtp(VerifyOtpDto request);

}

package com.example.demo.service;

import com.example.demo.entity.Registration;
import com.example.demo.model.RegistrationRequest;

public interface RegistrationService {
    Registration registerUser(RegistrationRequest request);
}

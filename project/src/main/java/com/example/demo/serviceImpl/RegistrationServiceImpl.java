package com.example.demo.serviceImpl;

import com.example.demo.entity.Registration;
import com.example.demo.model.RegistrationRequest;
import com.example.demo.repo.RegistrationRepository;
import com.example.demo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public Registration registerUser(RegistrationRequest request) {
        Registration reg = new Registration();
        reg.setFirstName(request.getFirstName());
        reg.setLastName(request.getLastName());
        reg.setAddress(request.getAddress());
        reg.setPhoneNo(request.getPhoneNo());
        reg.setEmail(request.getEmail());
        reg.setStatus("A");

        return registrationRepository.save(reg);
    }
}

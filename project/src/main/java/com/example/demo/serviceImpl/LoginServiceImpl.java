package com.example.demo.serviceImpl;

import com.example.demo.entity.Registration;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.OtpRequestDto;
import com.example.demo.model.VerifyOtpDto;
import com.example.demo.repo.RegistrationRepository;
import com.example.demo.service.LoginService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public ApiResponse<String> generateOtp(OtpRequestDto request) {
		if ((request.getEmail() == null || request.getEmail().isEmpty())
				&& (request.getPhoneNo() == null || request.getPhoneNo().isEmpty())) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "FAIL",
					"Please provide either email or phone number", null);
		}

		// Generate 6-digit OTP
		String otp = String.format("%06d", new Random().nextInt(999999));

		Registration registration;

		if (request.getEmail() != null && !request.getEmail().isEmpty()) {
			registration = registrationRepository.findByEmail(request.getEmail())
					.orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));
			registration.setOtp(otp);
		} else {
			registration = registrationRepository.findByPhoneNo(request.getPhoneNo())
					.orElseThrow(() -> new RuntimeException("User not found with phone: " + request.getPhoneNo()));
			registration.setOtp(otp);
		}

		registrationRepository.save(registration);

		// Send OTP to email if present
		if (registration.getEmail() != null && !registration.getEmail().isEmpty()) {
			sendOtpEmail(registration.getEmail(), otp);
		}

		return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", "OTP generated and sent successfully!", otp); // otp
	}

	private void sendOtpEmail(String email, String otp) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(email);
			helper.setSubject("Your OTP Code");
			helper.setText("Dear user,\n\nYour OTP is: " + otp + "\n\nRegards,\nTeam");

			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send OTP email", e);
		}
	}

	@Override
	public ApiResponse<String> verifyOtp(VerifyOtpDto request) {
		Registration registration;

		if (request.getEmail() != null && !request.getEmail().isEmpty()) {
			registration = registrationRepository.findByEmail(request.getEmail())
					.orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));
		} else if (request.getPhoneNo() != null && !request.getPhoneNo().isEmpty()) {
			registration = registrationRepository.findByPhoneNo(request.getPhoneNo())
					.orElseThrow(() -> new RuntimeException("User not found with phone: " + request.getPhoneNo()));

			if (registration.getEmail() == null || registration.getEmail().isEmpty()) {
				return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "FAIL",
						"No email linked with this phone number!", null);
			}
		} else {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "FAIL",
					"Please provide either email or phone number", null);
		}

		// Check OTP
		if (registration.getOtp() != null && registration.getOtp().equals(request.getOtp())) {
			return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", "OTP verified successfully!", null);
		} else {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "FAIL", "Invalid OTP!", null);
		}
	}
}

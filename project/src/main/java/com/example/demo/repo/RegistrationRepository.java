package com.example.demo.repo;

import com.example.demo.entity.Registration;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	Optional<Registration> findByEmail(String email);

	Optional<Registration> findByPhoneNo(String phoneNo);
}

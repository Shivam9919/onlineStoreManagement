package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "registration")
public class Registration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long registrationId;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNo;
	private String email;
	private String otp;
	private String status;
}

package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI registrationOpenAPI() {
		return new OpenAPI().info(new Info().title("Registration API")
				.description("Spring Boot Registration API Documentation with Swagger").version("1.0.0")
				.contact(new Contact().name("Shivam Keshari").email("shivam@example.com"))
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}

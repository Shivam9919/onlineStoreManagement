package com.example.demo.exception;

import com.example.demo.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handle validation errors (@Valid)
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).findFirst()
				.orElse("Validation failed");

		ApiResponse<Object> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "FAILURE", errorMessage, null);

		return ResponseEntity.badRequest().body(response);
	}

	/**
	 * Handle custom runtime exceptions
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
		ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "FAIL", ex.getMessage(), null);
		return ResponseEntity.badRequest().body(response);
	}

	/**
	 * Handle all other exceptions
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
		ApiResponse<String> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "FAIL",
				"Something went wrong: " + ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}

package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int statusCode;   // HTTP status code (200, 400, 500 etc.)
    private String status;    // SUCCESS / FAIL
    private String message;   // error/success message
    private T data;           // response data
}

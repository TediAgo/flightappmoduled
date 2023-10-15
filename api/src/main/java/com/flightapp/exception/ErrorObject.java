package com.flightapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorObject {
    private String message;
    private HttpStatus status;
}
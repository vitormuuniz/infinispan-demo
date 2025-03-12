package com.example.application.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;
}

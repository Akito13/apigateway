package com.example.bookshop.apigateway.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class InvalidJwtTokenException extends ResponseStatusException {
    public InvalidJwtTokenException(HttpStatusCode status, String message) {
        super(status, message);
    }
}

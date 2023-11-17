package com.example.bookshop.apigateway.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class InvalidBodyException extends ResponseStatusException {
    public InvalidBodyException(HttpStatusCode statusCode, String reason) {
        super(statusCode, reason);
    }


}

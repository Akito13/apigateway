package com.example.bookshop.apigateway.exception;


import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ServiceNotFoundException extends ResponseStatusException {
    public ServiceNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}

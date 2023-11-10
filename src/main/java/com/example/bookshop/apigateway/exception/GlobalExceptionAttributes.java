package com.example.bookshop.apigateway.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalExceptionAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = super.getError(request);

        Map<String, Object> errorResponse = super.getErrorAttributes(request, options);
//        errorResponse.remove("error")
        errorResponse.put("apiPath", request.path());
        errorResponse.put("message", error.getMessage());

        MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations
                .from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
        errorResponse.put("statusCode", this.getHttpStatus(error, responseStatusAnnotation));
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.remove("path");
        errorResponse.remove("error");
        errorResponse.remove("requestId");
        return errorResponse;
    }

    private HttpStatus getHttpStatus(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation){
//        if(error instanceof ResponseStatusException) {
//            return ((ResponseStatusException) error).getStatusCode().hashCode();
//        }
        return responseStatusAnnotation.getValue("code", HttpStatus.class).orElseGet(() -> {
            if(error instanceof ConnectException) {
                return HttpStatus.SERVICE_UNAVAILABLE;
            }
            return HttpStatus.INTERNAL_SERVER_ERROR;
        });
    }
}
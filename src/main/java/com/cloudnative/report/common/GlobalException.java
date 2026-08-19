package com.cloudnative.report.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(V1Exception.class)
    public ResponseEntity<String> handleException(V1Exception e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }
}

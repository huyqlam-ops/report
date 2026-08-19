package com.cloudnative.report.common;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class V1Exception extends ResponseStatusException {
    public V1Exception(HttpStatusCode statusCode) {
        super(statusCode);
    }
    public V1Exception(HttpStatusCode statusCode, String message) {
        super(statusCode, message);
    }
}

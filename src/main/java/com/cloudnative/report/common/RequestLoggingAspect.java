package com.cloudnative.report.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Aspect
@Slf4j
@Component
public class RequestLoggingAspect {

    @Around("@within(AudiLog) || @annotation(AudiLog)")
    public Object auditLog(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String datetime = LocalDateTime.now().toString();

        String httpMethodAndUri = "UNKNOWN";
        String javaMethod = joinPoint.getSignature().getName();
        int responseStatus = 500;

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            httpMethodAndUri = request.getMethod() + " " + request.getRequestURI();
        }

        Object result;
        try {
            result = joinPoint.proceed();

            if (attributes != null) {
                HttpServletResponse response = attributes.getResponse();
                if (response != null) {
                    responseStatus = response.getStatus();
                }
            }
        } finally {
            log.info("datetime={}, method={}, responseStatus={}, javaMethod={}, processingTimeMs={}",
                    datetime, httpMethodAndUri, responseStatus, javaMethod, System.currentTimeMillis());
        }

        return result;
    }
}

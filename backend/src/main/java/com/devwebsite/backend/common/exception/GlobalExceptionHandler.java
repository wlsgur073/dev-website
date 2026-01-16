package com.devwebsite.backend.common.exception;

import com.devwebsite.backend.common.filter.TraceIdFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed"
        );
        problemDetail.setTitle("Validation Error");
        problemDetail.setType(URI.create("https://api.devwebsite.com/errors/validation"));
        problemDetail.setProperty("traceId", getTraceId());
        problemDetail.setProperty("errors", errors);

        log.warn("Validation error: {}", errors);
        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problemDetail.setTitle("Bad Request");
        problemDetail.setType(URI.create("https://api.devwebsite.com/errors/bad-request"));
        problemDetail.setProperty("traceId", getTraceId());

        log.warn("Bad request: {}", ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                "Authentication failed"
        );
        problemDetail.setTitle("Unauthorized");
        problemDetail.setType(URI.create("https://api.devwebsite.com/errors/unauthorized"));
        problemDetail.setProperty("traceId", getTraceId());

        log.warn("Authentication error: {}", ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                "Access denied"
        );
        problemDetail.setTitle("Forbidden");
        problemDetail.setType(URI.create("https://api.devwebsite.com/errors/forbidden"));
        problemDetail.setProperty("traceId", getTraceId());

        log.warn("Access denied: {}", ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ProblemDetail handleNotFoundException(NoHandlerFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                "Resource not found"
        );
        problemDetail.setTitle("Not Found");
        problemDetail.setType(URI.create("https://api.devwebsite.com/errors/not-found"));
        problemDetail.setProperty("traceId", getTraceId());

        return problemDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        problemDetail.setTitle("Not Found");
        problemDetail.setType(URI.create("https://api.devwebsite.com/errors/not-found"));
        problemDetail.setProperty("traceId", getTraceId());

        log.warn("Resource not found: {}", ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred"
        );
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://api.devwebsite.com/errors/internal"));
        problemDetail.setProperty("traceId", getTraceId());

        log.error("Unexpected error", ex);
        return problemDetail;
    }

    private String getTraceId() {
        String traceId = MDC.get(TraceIdFilter.TRACE_ID_MDC_KEY);
        return traceId != null ? traceId : "unknown";
    }
}

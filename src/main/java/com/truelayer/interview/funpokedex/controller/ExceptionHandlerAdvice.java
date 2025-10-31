package com.truelayer.interview.funpokedex.controller;

import com.truelayer.interview.funpokedex.model.dto.ErrorResponse;
import com.truelayer.interview.funpokedex.service.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


/**
 * Global exception handler for validation and generic errors
 */
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerAdvice {

    private final UtilService utilService;

    private ErrorResponse buildErrorResponse(String error, String message, String path, HttpStatus status) {
        return ErrorResponse.builder()
                .error(error)
                .message(message)
                .timestamp(LocalDateTime.now().toString())
                .status(String.valueOf(status.value()))
                .path(path)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {

        log.warn("Validation constraint violation: {}", ex.getMessage());

        String violations = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Invalid input parameters: " + violations,
                request.getDescription(false).replace("uri=", ""),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        log.warn("Illegal argument provided: {}", utilService.sanitizeForLogging(ex.getMessage()));

        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Invalid request parameters: " + ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleClientException(
            HttpClientErrorException ex, WebRequest request) {

        log.error("HTTP client error occurred: {}", utilService.sanitizeForLogging(ex.getMessage()), ex);

        ErrorResponse errorResponse = buildErrorResponse(
                ex.getStatusText(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                HttpStatus.valueOf(ex.getStatusCode().value())
        );

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {

        log.error("Unexpected error occurred: {}", utilService.sanitizeForLogging(ex.getMessage()), ex);

        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error occurred",
                request.getDescription(false).replace("uri=", ""),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(
            NoResourceFoundException ex, WebRequest request) {

        log.warn("Resource not found: {}", request.getDescription(false).replace("uri=", ""));

        ErrorResponse errorResponse = buildErrorResponse(
                "Resource Not Found",
                "The requested resource was not found",
                request.getDescription(false).replace("uri=", ""),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

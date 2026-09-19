package com.techjourney.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.techjourney.api.dto.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TimelineEventNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleTimelineEventNotFound(
            TimelineEventNotFoundException exception,
            HttpServletRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        exception.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    
    @ExceptionHandler(SandboxEventNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleSandboxEventNotFound(
            SandboxEventNotFoundException exception,
            HttpServletRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        exception.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    
    
    /*
     * Handles invalid configuration submitted to an Engineering Lab experiment.
     *
     * Examples include:
     *
     * - an unknown timeline category
     * - a zero or negative result limit
     * - other invalid experiment input
     *
     * These are client request errors, so they are returned as
     * HTTP 400 BAD REQUEST rather than HTTP 500 INTERNAL SERVER ERROR.
     */
    @ExceptionHandler(EngineeringLabInvalidRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleEngineeringLabInvalidRequest(
            EngineeringLabInvalidRequestException exception,
            HttpServletRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        exception.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
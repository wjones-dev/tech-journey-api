package com.techjourney.api.exception;

/*
 * Thrown when an Engineering Lab experiment receives configuration
 * that cannot be processed safely or meaningfully.
 *
 * Examples include:
 *
 * - an unknown TimelineCategory value
 * - a zero or negative result limit
 * - other invalid experiment configuration added in the future
 *
 * This exception represents a client-side request problem rather than
 * an unexpected server failure. The GlobalExceptionHandler will convert
 * it into a 400 BAD REQUEST response for the Angular frontend.
 *
 * Keeping validation failures in a dedicated exception also prevents
 * implementation exceptions such as IllegalArgumentException from
 * leaking directly through the REST API.
 */
public class EngineeringLabInvalidRequestException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EngineeringLabInvalidRequestException(String message) {
        super(message);
    }
}
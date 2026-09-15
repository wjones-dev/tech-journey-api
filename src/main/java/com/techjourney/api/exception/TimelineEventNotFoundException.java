package com.techjourney.api.exception;

public class TimelineEventNotFoundException extends RuntimeException {

    public TimelineEventNotFoundException(Long id) {
        super("Timeline event not found with id: " + id);
    }
}
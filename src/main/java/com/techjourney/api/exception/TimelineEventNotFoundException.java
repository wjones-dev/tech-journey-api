package com.techjourney.api.exception;

public class TimelineEventNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimelineEventNotFoundException(Long id) {
        super("Timeline event not found with id: " + id);
    }
}
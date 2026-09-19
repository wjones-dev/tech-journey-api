package com.techjourney.api.exception;

public class SandboxEventNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SandboxEventNotFoundException(Long id) {
        super("Sandbox event not found with id: " + id);
    }
}
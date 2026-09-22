package com.techjourney.api.dto;

import jakarta.validation.constraints.NotBlank;

public record SecurityLoginRequest(

        @NotBlank
        String username,

        @NotBlank
        String password

) {
}
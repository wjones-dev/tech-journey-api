package com.techjourney.api.dto;

import java.util.List;

public record SecurityLoginResponse(

        boolean authenticated,

        String username,

        List<String> roles,

        String message

) {
}
package com.techjourney.api.dto;

import java.util.List;

public record SecuritySessionResponse(

        boolean authenticated,

        String username,

        List<String> roles

) {
}
package com.StajProject.Company.dto;

import java.util.UUID;

public record ContactDto(
        UUID id,
        String firstName,
        String email,
        String subject,
        String message

) {
}

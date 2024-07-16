package com.StajProject.Company.dto;

import java.util.UUID;

public record AdminDto(
        UUID id,
        String email,
        String role
) {
}

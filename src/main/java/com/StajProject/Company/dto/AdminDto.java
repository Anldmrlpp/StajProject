package com.StajProject.Company.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String imageUrl,
        String phoneNumber,
        String role,
        String statuses,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
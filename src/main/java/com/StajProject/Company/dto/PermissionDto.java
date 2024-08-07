package com.StajProject.Company.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PermissionDto(
        UUID id,
        String email,
        String description,
        int numberOfDays,
        LocalDate startDate,
        LocalDate endDate
) {
}
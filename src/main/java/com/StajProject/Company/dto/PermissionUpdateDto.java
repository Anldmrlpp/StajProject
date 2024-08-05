package com.StajProject.Company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record PermissionUpdateDto(
        @NotNull
        UUID employeeId,
        @NotNull
        String description,
        @NotNull
        LocalDate startDate,
        @NotBlank
        @NotNull
        LocalDate endDate
) {
}

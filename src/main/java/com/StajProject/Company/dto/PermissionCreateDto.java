package com.StajProject.Company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import java.util.UUID;

public record PermissionCreateDto(
        @NotNull
        UUID employeeId,
        @NotNull
        @NotBlank
        String description,
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate

) {
}

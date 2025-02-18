package com.StajProject.Company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record PermissionUpdateDto(
        @NotBlank
        @NotNull
        String email,
        @NotBlank
        @NotNull
        String description,
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate
) {
}
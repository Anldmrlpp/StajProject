package com.StajProject.Company.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import java.util.UUID;

public record PermissionCreateDto(
        @NotNull
        UUID employeeId,
        @NotNull
        String description,
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate

) {
}

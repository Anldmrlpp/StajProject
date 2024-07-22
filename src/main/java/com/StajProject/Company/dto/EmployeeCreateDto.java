package com.StajProject.Company.dto;

import jakarta.validation.constraints.NotNull;

public record EmployeeCreateDto(
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        String email,
        @NotNull
        String department
) {
}

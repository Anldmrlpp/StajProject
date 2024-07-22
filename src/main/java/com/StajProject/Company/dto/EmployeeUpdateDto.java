package com.StajProject.Company.dto;

import jakarta.validation.constraints.NotNull;

public record EmployeeUpdateDto(
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        String department
) {
}

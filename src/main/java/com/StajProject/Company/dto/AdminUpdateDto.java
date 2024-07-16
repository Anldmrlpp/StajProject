package com.StajProject.Company.dto;

import jakarta.validation.constraints.NotNull;

public record AdminUpdateDto(
        @NotNull
        String password,
        @NotNull
        String role
) {
}

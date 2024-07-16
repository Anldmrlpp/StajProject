package com.StajProject.Company.dto;

import jakarta.validation.constraints.NotNull;

public record AdminCreateDto(
        @NotNull
        String email,
        @NotNull
        String password,
        @NotNull
        String role
) {
}

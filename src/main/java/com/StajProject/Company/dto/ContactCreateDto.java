package com.StajProject.Company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactCreateDto(
        @NotBlank
        @NotNull
        String name,

        @NotBlank
        @NotNull
        @Email
        String email,

        @NotBlank
        @NotNull
        String subject,

        @NotBlank
        @NotNull
        String message
) {
}

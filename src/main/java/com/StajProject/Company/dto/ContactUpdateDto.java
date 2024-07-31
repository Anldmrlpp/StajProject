package com.StajProject.Company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactUpdateDto(
        @NotBlank
        @NotNull
        @Size(max = 100)
        String firstName,

        @NotBlank
        @NotNull
        @Size(max = 100)
        String subject,

        @NotBlank
        @NotNull
        @Size(max = 1000)
        String message
) {}

package com.StajProject.Company.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record EmployeeDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String imageUrl,
        String biography,
        String phoneNumber,
        String department,
        LocalDate birthday,
        int leaveBalance,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
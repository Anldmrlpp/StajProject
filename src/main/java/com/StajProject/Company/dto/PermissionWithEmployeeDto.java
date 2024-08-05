package com.StajProject.Company.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PermissionWithEmployeeDto(
        UUID id,
        UUID employeeId,
        String firstName,
        String lastName,
        String email,
        String department,
        String description,
        int numberOfDays,
        LocalDate startDate,
        LocalDate endDate
) {
}

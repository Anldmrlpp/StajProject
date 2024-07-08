package com.StajProject.Company.dto;

import java.util.UUID;

public record EmployeeDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String department,
        int leaveBalance
) {
}

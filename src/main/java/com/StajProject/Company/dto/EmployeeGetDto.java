package com.StajProject.Company.dto;

public record EmployeeGetDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String department,
        int leaveBalance
) {
}

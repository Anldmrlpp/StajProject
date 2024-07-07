package com.StajProject.Company.dto;

public record EmployeeUpdateDto(
        String firstName,
        String lastName,
        String email,
        String department
) {
}

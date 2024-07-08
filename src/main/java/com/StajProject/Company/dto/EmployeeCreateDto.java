package com.StajProject.Company.dto;

public record EmployeeCreateDto(
        String firstName,
        String lastName,
        String email,
        String department
) {
}

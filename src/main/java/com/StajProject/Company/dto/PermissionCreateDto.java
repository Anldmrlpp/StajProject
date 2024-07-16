package com.StajProject.Company.dto;

import java.time.LocalDate;

import java.util.UUID;

public record PermissionCreateDto(//Yeni bir izin talebi oluşturmak için gereken bilgiler gösterildi.
        UUID employeeId,
        String description,
        LocalDate startDate,
        LocalDate endDate

) {
}

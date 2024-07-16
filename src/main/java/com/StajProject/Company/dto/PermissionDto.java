package com.StajProject.Company.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PermissionDto(// Bir izin talebinin tüm bilgileri yazıldı.
        UUID id,
        UUID employeeId,
        String description,
        int numberOfDays,
        LocalDate startDate,
        LocalDate endDate
) {
}

package com.StajProject.Company.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PermissionUpdateDto(//Mevcut bir izni güncellemek için kullanıldı.
        UUID employeeId,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}

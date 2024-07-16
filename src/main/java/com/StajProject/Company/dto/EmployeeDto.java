package com.StajProject.Company.dto;

import java.util.UUID;

public record EmployeeDto( //Bir çalışanın tüm bilgilerini ve kalan izin günleri içerildi.
        UUID id,
        String firstName,
        String lastName,
        String email,
        String department,
        int leaveBalance
) {
}

package com.StajProject.Company.dto;

public record EmployeeUpdateDto( // Mevcut bir çalışanın bilgilerini güncellemek için yazıldı.
        String firstName,
        String lastName,
        String department
) {
}

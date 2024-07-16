package com.StajProject.Company.dto;

public record EmployeeCreateDto( //Yeni bir çalışan oluşturmak için gereken bilgiler görüntülendi.
        String firstName,
        String lastName,
        String email,
        String department
) {
}

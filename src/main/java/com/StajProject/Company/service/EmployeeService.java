package com.StajProject.Company.service;

import com.StajProject.Company.dto.EmployeeCreateDto;
import com.StajProject.Company.dto.EmployeeDto;
import com.StajProject.Company.dto.EmployeeUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface EmployeeService { //çalışanlarla ilgili işlemler tanımlandı.

    UUID signUpEmployee(EmployeeCreateDto employeeCreateDto);
    EmployeeDto loginEmployee(String email, String password);
    EmployeeDto getEmployee(String email);
    EmployeeDto updateEmployee(UUID id, EmployeeUpdateDto employeeUpdateDto, MultipartFile file);
    Boolean deleteEmployee(UUID id);
    Page<EmployeeDto> getAllEmployees(Pageable pageable);

    //Calısanlar bulunamazsa PermissionException fırlatılır.
}

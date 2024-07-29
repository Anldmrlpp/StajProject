package com.StajProject.Company.service;

import com.StajProject.Company.dto.EmployeeCreateDto;
import com.StajProject.Company.dto.EmployeeDto;
import com.StajProject.Company.dto.EmployeeUpdateDto;

import java.util.List;
import java.util.UUID;

public interface EmployeeService { //çalışanlarla ilgili işlemler tanımlandı.

    UUID createEmployee(EmployeeCreateDto employeeCreateDto);
    EmployeeDto getEmployee(String email);
    EmployeeDto updateEmployee(UUID id, EmployeeUpdateDto employeeUpdateDto);
    Boolean deleteEmployee(UUID id);

    EmployeeDto updateEmployee(String email, EmployeeUpdateDto employeeUpdateDto);

    Boolean deleteEmployee(String email);

    List<EmployeeDto> getAllEmployees();

    UUID signUpEmployee(EmployeeCreateDto employeeCreateDto);

    EmployeeDto loginEmployee(String email, String password);

    //Calısanlar bulunamazsa PermissionException fırlatılır.
}

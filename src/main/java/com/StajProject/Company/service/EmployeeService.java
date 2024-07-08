package com.StajProject.Company.service;

import com.StajProject.Company.dto.EmployeeCreateDto;
import com.StajProject.Company.dto.EmployeeDto;
import com.StajProject.Company.dto.EmployeeUpdateDto;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    UUID createEmployee(EmployeeCreateDto employeeCreateDto);
    EmployeeDto getEmployee(String email);
    EmployeeDto updateEmployee(String email, EmployeeUpdateDto employeeUpdateDto);
    Boolean deleteEmployee(String email);


}

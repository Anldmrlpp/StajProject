package com.StajProject.Company.controller;

import com.StajProject.Company.api.EmployeeApi;
import com.StajProject.Company.dto.EmployeeCreateDto;
import com.StajProject.Company.dto.EmployeeDto;
import com.StajProject.Company.dto.EmployeeUpdateDto;
import com.StajProject.Company.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class EmployeeController implements EmployeeApi {

    private final EmployeeService service;

    @Override
    public ResponseEntity<UUID> createEmployee(EmployeeCreateDto employeeCreateDto) {
        return ResponseEntity.ok(service.createEmployee(employeeCreateDto));
    }

    @Override
    public ResponseEntity<EmployeeDto> getEmployee(String email) {

        return ResponseEntity.ok(service.getEmployee(email));
    }

    @Override
    public ResponseEntity<EmployeeDto> updateEmployee(String email, EmployeeUpdateDto employeeUpdateDto) {
        return ResponseEntity.ok(service.updateEmployee(email, employeeUpdateDto));
    }

    @Override
    public ResponseEntity<Boolean> deleteEmployee(String email) {
        return ResponseEntity.ok(service.deleteEmployee(email));
    }
    @Override
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        return ResponseEntity.ok(service.getAllEmployees());
    }

}

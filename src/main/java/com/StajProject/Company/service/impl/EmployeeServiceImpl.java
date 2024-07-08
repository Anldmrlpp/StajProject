package com.StajProject.Company.service.impl;

import com.StajProject.Company.dto.EmployeeCreateDto;
import com.StajProject.Company.dto.EmployeeDto;
import com.StajProject.Company.dto.EmployeeUpdateDto;
import com.StajProject.Company.exception.ErrorMessages;
import com.StajProject.Company.exception.PermissionException;
import com.StajProject.Company.mapper.EmployeeMapper;
import com.StajProject.Company.model.Employee;
import com.StajProject.Company.repository.EmployeeRepository;
import com.StajProject.Company.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public UUID createEmployee(EmployeeCreateDto employeeCreateDto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeCreateDto, employee);
        employee.setLeaveBalance(15);

        Employee response = employeeRepository.save(employee);

        return response.getId();
    }

    @Override
    public EmployeeDto getEmployee(String email) {

        Optional<Employee> existEmployee = employeeRepository.findByEmail(email);

        if(existEmployee.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }
        System.out.println(existEmployee.get());
        return employeeMapper.toDto(existEmployee.get());

    }

    @Override
    public EmployeeDto updateEmployee(String email, EmployeeUpdateDto employeeUpdateDto) {
        Optional<Employee> existResponse = employeeRepository.findByEmail(email);

        if(existResponse.isPresent()) {
            Employee existEmployee = existResponse.get();
            BeanUtils.copyProperties(employeeUpdateDto, existEmployee);

            Employee response = employeeRepository.save(existEmployee);

            return employeeMapper.toDto(response);
        }
        else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }

    }

    @Override
    public Boolean deleteEmployee(String email) {
        Optional<Employee> existResponse = employeeRepository.findByEmail(email);

        if(existResponse.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }

        Employee existEmployee = existResponse.get();
        employeeRepository.delete(existEmployee);

        return true;
    }


}

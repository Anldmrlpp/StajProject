package com.StajProject.Company.service.impl;

import com.StajProject.Company.dto.EmployeeCreateDto;
import com.StajProject.Company.dto.EmployeeDto;
import com.StajProject.Company.dto.EmployeeUpdateDto;
import com.StajProject.Company.exception.ErrorMessages;
import com.StajProject.Company.exception.PermissionException;
import com.StajProject.Company.mapper.EmployeeMapper;
import com.StajProject.Company.model.Employee;
import com.StajProject.Company.repository.EmployeeRepository;
import com.StajProject.Company.service.AuthService;
import com.StajProject.Company.service.EmployeeService;
import com.StajProject.Company.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    @Value("${file.allowed-formats}")
    private String[] allowedFormats;

    @Override
    public UUID signUpEmployee(EmployeeCreateDto employeeCreateDto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeCreateDto, employee);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setLeaveBalance(15);
        employee.setStatuses("EMPLOYEE");

        Employee response = employeeRepository.save(employee);

        return response.getId();
    }

    @Override
    public EmployeeDto loginEmployee(String email, String password) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            if (passwordEncoder.matches(password, existingEmployee.getPassword())) {
                return employeeMapper.toDto(existingEmployee);
            }
            else {
                throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.INCORRECT_LOGIN);
            }
        }
        else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }
    }

    @Override
    public EmployeeDto getEmployee(String email) {
        Optional<Employee> existEmployee = employeeRepository.findByEmail(email);

        if(existEmployee.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }

        return employeeMapper.toDto(existEmployee.get());
    }

    @Override
    public EmployeeDto updateEmployee(UUID id, EmployeeUpdateDto employeeUpdateDto, MultipartFile file) {
        if(!authService.verifyUserIdMatchesAuthenticatedUser(id)) {
            throw PermissionException.withStatusAndMessage(HttpStatus.FORBIDDEN, ErrorMessages.UNAUTHORIZED);
        }

        Optional<Employee> existResponse = employeeRepository.findById(id);

        if(existResponse.isPresent()) {
            Employee existEmployee = existResponse.get();
            BeanUtils.copyProperties(employeeUpdateDto, existEmployee);
            existEmployee.setPassword(passwordEncoder.encode(existEmployee.getPassword()));

            if(file != null && !file.isEmpty()) {
                String fileType = Objects.requireNonNull(file.getContentType()).split("/")[1];
                if(!Arrays.asList(allowedFormats).contains(fileType)) {
                    throw PermissionException.withStatusAndMessage(HttpStatus.BAD_REQUEST, ErrorMessages.UNSUPPORTED_FILE_TYPE);
                }

                String currentImageUrl = existEmployee.getImageUrl();

                if(currentImageUrl != null && !currentImageUrl.isEmpty()) {
                    fileService.deleteFile(currentImageUrl);
                }
                String newFileName = fileService.uploadFile(file);
                existEmployee.setImageUrl(newFileName);
            }
            try {
                return employeeMapper.toDto(employeeRepository.save(existEmployee));
            } catch (Exception e) {
                throw e;
            }

        }
        else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }

    }

    @Override
    public Boolean deleteEmployee(UUID id) {
        if(!authService.verifyUserIdMatchesAuthenticatedUser(id)) {
            throw PermissionException.withStatusAndMessage(HttpStatus.FORBIDDEN, ErrorMessages.UNAUTHORIZED);
        }

        Optional<Employee> existResponse = employeeRepository.findById(id);

        if(existResponse.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }

        Employee existEmployee = existResponse.get();
        try {
            employeeRepository.delete(existEmployee);
        } catch (Exception e) {
            throw e;
        }
        if(existEmployee.getImageUrl() != null && !existEmployee.getImageUrl().isEmpty()) {
            fileService.deleteFile(existEmployee.getImageUrl());
        }

        return true;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> existEmployees = employeeRepository.findAll();
        return employeeMapper.toDtoList(existEmployees);
    }
}
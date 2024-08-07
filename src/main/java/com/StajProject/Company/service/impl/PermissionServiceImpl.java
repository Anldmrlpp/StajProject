package com.StajProject.Company.service.impl;

import com.StajProject.Company.dto.PermissionCreateDto;
import com.StajProject.Company.dto.PermissionDto;
import com.StajProject.Company.dto.PermissionUpdateDto;
import com.StajProject.Company.dto.PermissionWithEmployeeDto;
import com.StajProject.Company.exception.ErrorMessages;
import com.StajProject.Company.exception.PermissionException;
import com.StajProject.Company.mapper.PermissionMapper;
import com.StajProject.Company.model.Employee;
import com.StajProject.Company.model.Permission;
import com.StajProject.Company.repository.EmployeeRepository;
import com.StajProject.Company.repository.PermissionRepository;
import com.StajProject.Company.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final EmployeeRepository employeeRepository;
    private final PermissionMapper mapper;

    @Override
    public PermissionDto addPermission(PermissionCreateDto permissionCreateDto) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionCreateDto, permission);
        int numberOfPermissionDay = (int)ChronoUnit.DAYS.between(permissionCreateDto.startDate(), permissionCreateDto.endDate());

        Optional<Employee> responseEmployee = employeeRepository.findByEmail(permissionCreateDto.email());

        if(responseEmployee.isPresent()) {
            Employee existEmployee = responseEmployee.get();

            if(existEmployee.getLeaveBalance() - (numberOfPermissionDay+1) >= 0) {
                permission.setNumberOfDays(numberOfPermissionDay+1);
                Permission response = permissionRepository.save(permission);
                existEmployee.setLeaveBalance(existEmployee.getLeaveBalance() - (numberOfPermissionDay+1));
                employeeRepository.save(existEmployee);

                return mapper.toDto(response);
            }
            else {
                throw PermissionException.withStatusAndMessage(HttpStatus.BAD_REQUEST, ErrorMessages.DONT_HAVE_ENOUGH_PERMISSION);
            }
        }
        else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }

    }

    @Override
    public PermissionDto getPermission(UUID id) {
        Optional<Permission> existPermission = permissionRepository.findById(id);

        if(existPermission.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.PERMISSION_NOT_FOUND);
        }

        return mapper.toDto(existPermission.get());
    }

    @Override
    public Page<PermissionWithEmployeeDto> getPermissions(Pageable pageable) {
        Page<Permission> existPermissions = permissionRepository.findAll(pageable);

        if(existPermissions.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.PERMISSION_NOT_FOUND);
        }

        List<PermissionWithEmployeeDto> dtoList = new ArrayList<>();

        for(Permission permission : existPermissions) {
            Optional<Employee> responseEmployee = employeeRepository.findByEmail(permission.getEmail());

            if(responseEmployee.isEmpty()) {
                throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.DEFAULT_ERROR_MESSAGE);
            }

            Employee existEmployee = responseEmployee.get();

            PermissionWithEmployeeDto dto = new PermissionWithEmployeeDto(
                    permission.getId(),
                    permission.getEmail(),
                    existEmployee.getFirstName(),
                    existEmployee.getLastName(),
                    existEmployee.getDepartment(),
                    permission.getDescription(),
                    permission.getNumberOfDays(),
                    permission.getStartDate(),
                    permission.getEndDate()
            );

            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, existPermissions.getTotalElements());
    }

    @Override
    public Page<PermissionWithEmployeeDto> getPermissionsForEmployee(String email, Pageable pageable) {
        Page<Permission> existPermissions = permissionRepository.findAllByEmail(email, pageable);

        if(existPermissions.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.PERMISSION_NOT_FOUND_FOR_EMPLOYEE);
        }

        Optional<Employee> responseEmployee = employeeRepository.findByEmail(email);

        if(responseEmployee.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.DEFAULT_ERROR_MESSAGE);
        }

        Employee existEmployee = responseEmployee.get();
        List<PermissionWithEmployeeDto> dtoList = new ArrayList<>();

        for (Permission permission : existPermissions) {
            PermissionWithEmployeeDto dto = new PermissionWithEmployeeDto(
                    permission.getId(),
                    permission.getEmail(),
                    existEmployee.getFirstName(),
                    existEmployee.getLastName(),
                    existEmployee.getDepartment(),
                    permission.getDescription(),
                    permission.getNumberOfDays(),
                    permission.getStartDate(),
                    permission.getEndDate()
            );

            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, existPermissions.getTotalElements());
    }

    @Override
    public PermissionDto updatePermission(UUID id, PermissionUpdateDto permissionUpdateDto) {
        Optional<Permission> responsePermission = permissionRepository.findById(id);

        if(responsePermission.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.PERMISSION_NOT_FOUND);
        }

        Permission existPermission = responsePermission.get();

        if(!existPermission.getEmail().equals(permissionUpdateDto.email())) {
            Optional<Employee> responseWrongEmployee = employeeRepository.findByEmail(existPermission.getEmail());
            Optional<Employee> responseTrueEmployee = employeeRepository.findByEmail(permissionUpdateDto.email());

            if(responseWrongEmployee.isEmpty() || responseTrueEmployee.isEmpty()) {
                throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
            }

            Employee wrongEmployee = responseWrongEmployee.get();
            Employee trueEmployee = responseTrueEmployee.get();

            int numberOfPermissionDay = (int)ChronoUnit.DAYS.between(permissionUpdateDto.startDate(), permissionUpdateDto.endDate());

            wrongEmployee.setLeaveBalance(wrongEmployee.getLeaveBalance() + existPermission.getNumberOfDays());
            if(trueEmployee.getLeaveBalance() - (numberOfPermissionDay+1) >= 0) {
                trueEmployee.setLeaveBalance(trueEmployee.getLeaveBalance() - (numberOfPermissionDay+1));

                BeanUtils.copyProperties(permissionUpdateDto, existPermission);
                existPermission.setNumberOfDays(numberOfPermissionDay+1);

                Permission responseUpdate = permissionRepository.save(existPermission);
                employeeRepository.save(wrongEmployee);
                employeeRepository.save(trueEmployee);

                return mapper.toDto(responseUpdate);
            }
            else {
                throw PermissionException.withStatusAndMessage(HttpStatus.BAD_REQUEST, ErrorMessages.DONT_HAVE_ENOUGH_PERMISSION);
            }

        }
        else {
            Optional<Employee> responseEmployee = employeeRepository.findByEmail(existPermission.getEmail());

            if(responseEmployee.isEmpty()) {
                throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
            }

            Employee existEmployee = responseEmployee.get();
            existEmployee.setLeaveBalance(existEmployee.getLeaveBalance() + existPermission.getNumberOfDays());

            int numberOfPermissionDay = (int)ChronoUnit.DAYS.between(permissionUpdateDto.startDate(), permissionUpdateDto.endDate());
            if(existEmployee.getLeaveBalance() - (numberOfPermissionDay+1) >= 0) {
                existEmployee.setLeaveBalance(existEmployee.getLeaveBalance() - (numberOfPermissionDay+1));

                BeanUtils.copyProperties(permissionUpdateDto, existPermission);
                existPermission.setNumberOfDays(numberOfPermissionDay+1);

                Permission responseUpdate = permissionRepository.save(existPermission);
                employeeRepository.save(existEmployee);

                return mapper.toDto(responseUpdate);
            }
            else {
                throw PermissionException.withStatusAndMessage(HttpStatus.BAD_REQUEST, ErrorMessages.DONT_HAVE_ENOUGH_PERMISSION);
            }

        }
    }

    @Override
    public Boolean deletePermission(UUID id) {

        Optional<Permission> responsePermission = permissionRepository.findById(id);

        if(responsePermission.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.PERMISSION_NOT_FOUND);
        }

        Permission existPermission = responsePermission.get();

        Optional<Employee> responseEmployee = employeeRepository.findByEmail(existPermission.getEmail());

        if(responseEmployee.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }

        Employee existEmployee = responseEmployee.get();

        permissionRepository.delete(existPermission);

        existEmployee.setLeaveBalance(existEmployee.getLeaveBalance() + existPermission.getNumberOfDays());
        employeeRepository.save(existEmployee);

        return true;
    }

    @Override
    public Boolean deletePermissionsForEmployee(String email) {

        List<Permission> existPermissions = permissionRepository.findByEmail(email);

        if(existPermissions.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.PERMISSION_NOT_FOUND_FOR_EMPLOYEE);
        }

        Optional<Employee> responseEmployee = employeeRepository.findByEmail(email);

        if(responseEmployee.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }

        permissionRepository.deleteAll(existPermissions);

        Employee existEmployee = responseEmployee.get();
        existEmployee.setLeaveBalance(15);
        employeeRepository.save(existEmployee);

        return true;
    }

}
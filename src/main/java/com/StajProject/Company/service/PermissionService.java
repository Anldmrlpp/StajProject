package com.StajProject.Company.service;

import com.StajProject.Company.dto.PermissionCreateDto;
import com.StajProject.Company.dto.PermissionDto;
import com.StajProject.Company.dto.PermissionUpdateDto;
import com.StajProject.Company.dto.PermissionWithEmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PermissionService {
    PermissionDto addPermission(PermissionCreateDto permissionCreateDto);
    PermissionDto getPermission(UUID id);
    Page<PermissionWithEmployeeDto> getPermissions(Pageable pageable);
    Page<PermissionWithEmployeeDto> getPermissionsForEmployee(UUID employeeId, Pageable pageable);
    PermissionDto updatePermission(UUID id, PermissionUpdateDto permissionUpdateDto);
    Boolean deletePermission(UUID id);
    Boolean deletePermissionsForEmployee(UUID employeeId);
}
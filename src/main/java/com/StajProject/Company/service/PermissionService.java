package com.StajProject.Company.service;

import com.StajProject.Company.dto.PermissionCreateDto;
import com.StajProject.Company.dto.PermissionDto;
import com.StajProject.Company.dto.PermissionUpdateDto;
import org.hibernate.query.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface PermissionService {
    PermissionDto addPermission(PermissionCreateDto permissionCreateDto);
    PermissionDto getPermission(UUID id);

    List<PermissionDto> getPermissionsForEmployee(UUID employeeId);

    PermissionDto updatePermission(UUID id, PermissionUpdateDto permissionUpdateDto);
    Boolean deletePermission(UUID id);
    Boolean deletePermissionsForEmployee(UUID employeeId);
    Page<PermissionDto> getPermissions (Pageable pageable);
    Page<PermissionDto> getPermissionsForEmployee(UUID employeeId, Pageable pageable);



}

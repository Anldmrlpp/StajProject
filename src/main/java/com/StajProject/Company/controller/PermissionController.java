package com.StajProject.Company.controller;

import com.StajProject.Company.api.PermissionApi;
import com.StajProject.Company.dto.PermissionCreateDto;
import com.StajProject.Company.dto.PermissionDto;
import com.StajProject.Company.dto.PermissionUpdateDto;
import com.StajProject.Company.dto.PermissionWithEmployeeDto;
import com.StajProject.Company.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PermissionController implements PermissionApi {

    private final PermissionService permissionService;

    @Override
    public ResponseEntity<PermissionDto> addPermission(PermissionCreateDto permissionCreateDto) {
        return ResponseEntity.ok(permissionService.addPermission(permissionCreateDto));
    }

    @Override
    public ResponseEntity<PermissionDto> getPermission(UUID id) {
        return ResponseEntity.ok(permissionService.getPermission(id));
    }

    @Override
    public ResponseEntity<Page<PermissionWithEmployeeDto>> getPermissions(Pageable pageable) {
        return ResponseEntity.ok(permissionService.getPermissions(pageable));
    }

    @Override
    public ResponseEntity<Page<PermissionWithEmployeeDto>> getPermissionsForEmployee(String email, Pageable pageable) {
        return ResponseEntity.ok(permissionService.getPermissionsForEmployee(email, pageable));
    }

    @Override
    public ResponseEntity<PermissionDto> updatePermission(UUID id, PermissionUpdateDto permissionUpdateDto) {
        return ResponseEntity.ok(permissionService.updatePermission(id, permissionUpdateDto));
    }

    @Override
    public ResponseEntity<Boolean> deletePermission(UUID id) {
        return ResponseEntity.ok(permissionService.deletePermission(id));
    }

    @Override
    public ResponseEntity<Boolean> deletePermissionsForEmployee(String email) {
        return ResponseEntity.ok(permissionService.deletePermissionsForEmployee(email));
    }

}
package com.StajProject.Company.controller;

import com.StajProject.Company.api.PermissionApi;
import com.StajProject.Company.dto.PermissionCreateDto;
import com.StajProject.Company.dto.PermissionDto;
import com.StajProject.Company.dto.PermissionUpdateDto;
import com.StajProject.Company.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PermissionController implements PermissionApi { //PermissionController da ise izinlerle ilgili işlemler yürütüldü.
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
    public ResponseEntity<List<PermissionDto>> getPermissionsForEmployee(UUID employeeId) {
        return ResponseEntity.ok(permissionService.getPermissionsForEmployee(employeeId));
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
    public ResponseEntity<Boolean> deletePermissionsForEmployee(UUID employeeId) {
        return ResponseEntity.ok(permissionService.deletePermissionsForEmployee(employeeId));
    }
}

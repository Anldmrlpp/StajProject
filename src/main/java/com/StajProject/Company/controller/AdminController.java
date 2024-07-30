package com.StajProject.Company.controller;

import com.StajProject.Company.api.AdminApi;
import com.StajProject.Company.dto.AdminCreateDto;
import com.StajProject.Company.dto.AdminDto;
import com.StajProject.Company.dto.AdminUpdateDto;
import com.StajProject.Company.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AdminController implements AdminApi {

    private final AdminService service;

    @Override
    public ResponseEntity<AdminDto> signUpAdmin(String key, AdminCreateDto adminCreateDto) {
        return ResponseEntity.ok(service.signUpAdmin(key, adminCreateDto));
    }

    @Override
    public ResponseEntity<AdminDto> loginAdmin(String key, String email, String password) {
        return ResponseEntity.ok(service.loginAdmin(key, email, password));
    }

    @Override
    public ResponseEntity<AdminDto> getAdmin(String email) {
        return ResponseEntity.ok(service.getAdmin(email));
    }

    @Override
    public ResponseEntity<Page<AdminDto>> getAdmins(Pageable pageable) {
        return ResponseEntity.ok(service.getAdmins(pageable));
    }

    @Override
    public ResponseEntity<AdminDto> updateAdmin(String key, UUID id, AdminUpdateDto adminUpdateDto, MultipartFile file) {
        return ResponseEntity.ok(service.updateAdmin(key, id, adminUpdateDto, file));
    }

    @Override
    public ResponseEntity<Boolean> deleteAdmin(String key, UUID id) {
        return ResponseEntity.ok(service.deleteAdmin(key, id));
    }

}
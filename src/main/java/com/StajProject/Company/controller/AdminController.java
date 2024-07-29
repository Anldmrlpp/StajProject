package com.StajProject.Company.controller;

import com.StajProject.Company.api.AdminApi;
import com.StajProject.Company.dto.AdminCreateDto;
import com.StajProject.Company.dto.AdminDto;
import com.StajProject.Company.dto.AdminUpdateDto;
import com.StajProject.Company.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdminController implements AdminApi {

    private final AdminService service;

    @Override
    public ResponseEntity<AdminDto> signUpAdmin(AdminCreateDto adminCreateDto) {
        return ResponseEntity.ok(service.signUpAdmin(adminCreateDto));
    }

    @Override
    public ResponseEntity<AdminDto> loginAdmin(String email, String password) {
        return ResponseEntity.ok(service.loginAdmin(email,password));
    }

    @Override
    public ResponseEntity<List<AdminDto>> getAdmins() {
        return ResponseEntity.ok(service.getAdmins());
    }

    @Override
    public ResponseEntity<AdminDto> updateAdmin(String email, AdminUpdateDto adminUpdateDto) {
        return ResponseEntity.ok(service.updateAdmin(email, adminUpdateDto));
    }

    @Override
    public ResponseEntity<Boolean> deleteAdmin(String email) {
        return ResponseEntity.ok(service.deleteAdmin(email));
    }

}

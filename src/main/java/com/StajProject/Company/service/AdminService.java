package com.StajProject.Company.service;

import com.StajProject.Company.dto.AdminCreateDto;
import com.StajProject.Company.dto.AdminDto;
import com.StajProject.Company.dto.AdminUpdateDto;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    AdminDto signUpAdmin(AdminCreateDto adminCreateDto);
    List<AdminDto> getAdmins();
    AdminDto updateAdmin(String email, AdminUpdateDto adminUpdateDto);
    Boolean deleteAdmin(String email);
 }

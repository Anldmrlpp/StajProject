package com.StajProject.Company.service;

import com.StajProject.Company.dto.AdminCreateDto;
import com.StajProject.Company.dto.AdminDto;
import com.StajProject.Company.dto.AdminUpdateDto;

import java.util.List;

public interface AdminService {
    AdminDto signUpAdmin(String key,AdminCreateDto adminCreateDto);
    List<AdminDto> getAdmins();
    AdminDto updateAdmin(String key,String email, AdminUpdateDto adminUpdateDto);
    Boolean deleteAdmin(String key, String email);

    AdminDto loginAdmin(String key,String email, String password);

    AdminDto getAdmin(String email);
}

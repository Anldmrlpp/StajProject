package com.StajProject.Company.service;

import com.StajProject.Company.dto.AdminCreateDto;
import com.StajProject.Company.dto.AdminDto;
import com.StajProject.Company.dto.AdminUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    AdminDto signUpAdmin(String key,AdminCreateDto adminCreateDto);
    List<AdminDto> getAdmins();
    AdminDto loginAdmin(String key,String email, String password);
    AdminDto getAdmin(String email);
    AdminDto updateAdmin(String key, UUID id, AdminUpdateDto adminUpdateDto, MultipartFile file);
    Boolean deleteAdmin(String key, UUID id);
}

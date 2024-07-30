package com.StajProject.Company.service.impl;

import com.StajProject.Company.dto.AdminCreateDto;
import com.StajProject.Company.dto.AdminDto;
import com.StajProject.Company.dto.AdminUpdateDto;
import com.StajProject.Company.exception.ErrorMessages;
import com.StajProject.Company.exception.PermissionException;
import com.StajProject.Company.mapper.AdminMapper;
import com.StajProject.Company.mapper.PageMapperHelper;
import com.StajProject.Company.model.Admin;
import com.StajProject.Company.repository.AdminRepository;
import com.StajProject.Company.service.AdminService;
import com.StajProject.Company.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequiredArgsConstructor
@Service
@PropertySource("classpath:config.properties")
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repository;
    private final AdminMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    @Value("${adminSignUpKey}")
    private String adminKey;
    @Value("${file.allowed-formats}")
    private String[] allowedFormats;

    @Override
    public AdminDto signUpAdmin(String key, AdminCreateDto adminCreateDto) {
        if(adminKey.equals(key)) {
            Admin admin = new Admin();
            BeanUtils.copyProperties(adminCreateDto, admin);
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setStatuses("ADMIN");

            Admin response = repository.save(admin);

            return mapper.toDto(response);
        }
        else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.WRONG_ADMIN_KEY);
        }

    }

    @Override
    public AdminDto loginAdmin(String key, String email, String password) {
        if(adminKey.equals(key)) {
            Optional<Admin> admin = repository.findByEmail(email);

            if (admin.isPresent()) {
                Admin existAdmin = admin.get();

                if (passwordEncoder.matches(password, existAdmin.getPassword())) {
                    return mapper.toDto(existAdmin);
                }
                else {
                    throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.INCORRECT_LOGIN);
                }
            }
            else {
                throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.ADMIN_NOT_FOUND);
            }
        }
        else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.WRONG_ADMIN_KEY);
        }
    }

    @Override
    public AdminDto getAdmin(String email) {
        Optional<Admin> responseAdmin = repository.findByEmail(email);

        if(responseAdmin.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.ADMIN_NOT_FOUND);
        }

        return mapper.toDto(responseAdmin.get());
    }

    @Override
    public Page<AdminDto> getAdmins(Pageable pageable) {
        Page<Admin> responseAdmin = repository.findAll(pageable);

        if(responseAdmin.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.ADMIN_NOT_FOUND);
        }

        return PageMapperHelper.mapEntityPageToDtoPage(responseAdmin, mapper);
    }

    @Override
    public AdminDto updateAdmin(String key, UUID id, AdminUpdateDto adminUpdateDto, MultipartFile file) {
        if(adminKey.equals(key)) {
            Optional<Admin> responseAdmin = repository.findById(id);

            if(responseAdmin.isEmpty()) {
                throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.ADMIN_NOT_FOUND);
            }

            Admin existAdmin = responseAdmin.get();
            BeanUtils.copyProperties(adminUpdateDto, existAdmin);
            existAdmin.setPassword(passwordEncoder.encode(existAdmin.getPassword()));

            if(file != null && !file.isEmpty()) {
                String fileType = Objects.requireNonNull(file.getContentType()).split("/")[1];
                if(!Arrays.asList(allowedFormats).contains(fileType)) {
                    throw PermissionException.withStatusAndMessage(HttpStatus.BAD_REQUEST, ErrorMessages.UNSUPPORTED_FILE_TYPE);
                }

                String currentImageUrl = existAdmin.getImageUrl();

                if(currentImageUrl != null && !currentImageUrl.isEmpty()) {
                    fileService.deleteFile(currentImageUrl);
                }
                String newFileName = fileService.uploadFile(file);
                existAdmin.setImageUrl(newFileName);
            }

            try {
                return mapper.toDto(repository.save(existAdmin));
            } catch (Exception e) {
                throw e;
            }

        }
        else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.WRONG_ADMIN_KEY);
        }
    }

    @Override
    public Boolean deleteAdmin(String key, UUID id) {
        if(adminKey.equals(key)) {
            Optional<Admin> responseAdmin = repository.findById(id);

            if(responseAdmin.isEmpty()) {
                throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.ADMIN_NOT_FOUND);
            }

            Admin existAdmin = responseAdmin.get();
            try {
                repository.delete(existAdmin);
            } catch (Exception e) {
                throw e;
            }
            if(existAdmin.getImageUrl() != null && !existAdmin.getImageUrl().isEmpty()) {
                fileService.deleteFile(existAdmin.getImageUrl());
            }

            return true;
        }
        else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.WRONG_ADMIN_KEY);
        }
    }

}
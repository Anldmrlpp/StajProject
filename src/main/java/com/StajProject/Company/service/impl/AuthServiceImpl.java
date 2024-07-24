package com.StajProject.Company.service.impl;

import com.StajProject.Company.exception.ErrorMessages;
import com.StajProject.Company.exception.PermissionException;
import com.StajProject.Company.model.Admin;
import com.StajProject.Company.repository.AdminRepository;
import com.StajProject.Company.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AdminRepository repository;

    // Login Process
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Admin> response = repository.findByEmail(email);

        if(response.isPresent()) {
            Admin existAdmin = response.get();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(existAdmin.getEmail())
                    .password(existAdmin.getPassword())
                    .roles("ADMIN")
                    .build();
        }
        else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMAIL_NOT_FOUND);
        }
    }

}
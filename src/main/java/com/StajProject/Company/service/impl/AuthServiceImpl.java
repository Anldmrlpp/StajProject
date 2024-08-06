package com.StajProject.Company.service.impl;

import com.StajProject.Company.exception.ErrorMessages;
import com.StajProject.Company.exception.PermissionException;
import com.StajProject.Company.model.Admin;
import com.StajProject.Company.model.Employee;
import com.StajProject.Company.repository.AdminRepository;
import com.StajProject.Company.repository.EmployeeRepository;
import com.StajProject.Company.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository employeeRepository;
    private final AdminRepository adminRepository;

    // Login Process
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> responseEmployee = employeeRepository.findByEmail(email);

        if(responseEmployee.isPresent()) {
            Employee existEmployee = responseEmployee.get();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(existEmployee.getEmail())
                    .password(existEmployee.getPassword())
                    .roles("USER")
                    .build();
        }

        Optional<Admin> responseAdmin = adminRepository.findByEmail(email);

        if(responseAdmin.isPresent()) {
            Admin existAdmin = responseAdmin.get();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(existAdmin.getEmail())
                    .password(existAdmin.getPassword())
                    .roles("ADMIN")
                    .build();
        }

        throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMAIL_NOT_FOUND);
    }

    @Override
    public Boolean verifyUserIdMatchesAuthenticatedUser(UUID id) {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Employee> authenticatedUserOpt = employeeRepository.findByEmail(authenticatedUserEmail);
        if (authenticatedUserOpt.isPresent()) {
            Employee authenticatedUser = authenticatedUserOpt.get();
            return authenticatedUser.getId().equals(id);
        }

        Optional<Admin> authenticatedAdminOpt = adminRepository.findByEmail(authenticatedUserEmail);

        return authenticatedAdminOpt.isPresent();
    }

}
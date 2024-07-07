package com.StajProject.Company.service;


import com.StajProject.Company.model.Employee;
import com.StajProject.Company.model.Permission;
import com.StajProject.Company.repository.EmployeeRepository;
import com.StajProject.Company.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Transactional
    public Permission createPermission(Long employeeId, String type, int numberOfDays, Date startDate, Date endDate) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Çalışan bulunamadı..."));

        if (employee.getLeaveBalance() < numberOfDays) {
            throw new RuntimeException("Yeterli izin bakiyesi yok !");
        }

        employee.setLeaveBalance(employee.getLeaveBalance() - numberOfDays);
        employeeRepository.save(employee);

        Permission permission = new Permission();
        permission.setEmployeeId(employeeId);
        permission.setType(type);
        permission.setNumberOfDays(numberOfDays);
        permission.setStartDate(startDate);
        permission.setEndDate(endDate);

        return permissionRepository.save(permission);
    }

}

package com.StajProject.Company.service;

import com.StajProject.Company.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    public EmployeeRepository employeeRepository;

        public Long createEmployee(){
            return null;
        }


}

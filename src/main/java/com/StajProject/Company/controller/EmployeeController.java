package com.StajProject.Company.controller;

import com.StajProject.Company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employess")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
}
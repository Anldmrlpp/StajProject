package com.StajProject.Company.controller;

import com.StajProject.Company.model.Permission;
import com.StajProject.Company.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public Permission createPermission(
            @RequestParam Long employeeId,
            @RequestParam String type,
            @RequestParam int numberOfDays,
            @RequestParam String startDate,
            @RequestParam String endDate) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start = formatter.parse(startDate);
        Date end = formatter.parse(endDate);

        return permissionService.createPermission(employeeId, type, numberOfDays, start, end);
    }
}

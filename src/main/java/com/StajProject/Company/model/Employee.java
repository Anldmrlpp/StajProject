package com.StajProject.Company.model;

import com.StajProject.Company.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Data
@Table(name = "employee_data", schema = "util_sch")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "biography")
    private String biography;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "department")
    private String department;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "leave_balance")
    private int leaveBalance;
    @Column(name = "statuses")
    private String statuses;
}
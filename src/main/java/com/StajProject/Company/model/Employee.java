package com.StajProject.Company.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Entity
@Data
@Table(name = "employee_data", schema = "util_sch")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee { //çalışanların bilgilerini tutmak için yazıldı , employee_data tablosu ile eşleştirildi.
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "department")
    private String department;

    @Column(name = "leave_balance")
    private int leaveBalance;
}

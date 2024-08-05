package com.StajProject.Company.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Data
@Table(name = "permission_data", schema = "util_sch")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "employee_id")
    private UUID employeeId;
    @Column(name = "description")
    private String description;
    @Column(name = "number_of_days")
    private int numberOfDays;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
}
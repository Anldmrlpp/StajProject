package com.StajProject.Company.repository;

import com.StajProject.Company.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    //Employee varlıkları üzerinde CRUD işlemlerini gerçekleştirmek için yazıldı.
    Optional<Employee> findByEmail(UUID email);

}

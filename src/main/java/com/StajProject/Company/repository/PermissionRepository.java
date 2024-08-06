
package com.StajProject.Company.repository;

import com.StajProject.Company.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    Page<Permission> findAllByEmployeeId(UUID employeeId, Pageable pageable);
    List<Permission> findByEmployeeId(UUID employeeId);

}

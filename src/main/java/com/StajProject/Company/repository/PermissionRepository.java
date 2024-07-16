package com.StajProject.Company.repository;

import com.StajProject.Company.dto.PermissionDto;
import com.StajProject.Company.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    //Permission varlıkları üzerinde CRUD işlemlerini gerçekleştirmek için yazıldı.
    List<Permission> findByEmployeeId(UUID id);
}

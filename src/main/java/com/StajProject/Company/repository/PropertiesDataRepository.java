package com.StajProject.Company.repository;

import com.StajProject.Company.model.PropertiesData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertiesDataRepository extends JpaRepository<PropertiesData, UUID> {

}

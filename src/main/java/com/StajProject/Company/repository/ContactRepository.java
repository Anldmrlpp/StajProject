package com.StajProject.Company.repository;

import com.StajProject.Company.model.Admin;
import com.StajProject.Company.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
    Optional <Contact> findByEmail (String email);
}

package com.StajProject.Company.service;

import com.StajProject.Company.dto.ContactCreateDto;
import com.StajProject.Company.dto.ContactDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ContactService {
    UUID createContact (ContactCreateDto contactCreateDto);
    ContactDto getContactById(UUID id);
    Page<ContactDto> getAllContacts(Pageable pageable);
    Boolean deleteContactById(UUID id);
}

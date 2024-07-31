package com.StajProject.Company.service;

import com.StajProject.Company.dto.ContactCreateDto;
import com.StajProject.Company.dto.ContactDto;
import com.StajProject.Company.dto.ContactUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ContactService {
    UUID createContact (ContactCreateDto contactCreateDto);
    ContactDto getContactById(UUID id);
    Page<ContactDto> getAllContacts(Pageable pageable);
    Boolean updateContact(ContactUpdateDto contactUpdateDto);
    Boolean deleteContact(UUID id);
}

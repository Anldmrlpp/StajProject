package com.StajProject.Company.service.impl;

import com.StajProject.Company.dto.ContactCreateDto;
import com.StajProject.Company.dto.ContactDto;
import com.StajProject.Company.dto.ContactUpdateDto;
import com.StajProject.Company.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {
    @Override
    public UUID createContact(ContactCreateDto contactCreateDto) {
        return null;
    }

    @Override
    public ContactDto getContactById(UUID id) {
        return null;
    }

    @Override
    public Page<ContactDto> getAllContacts(Pageable pageable) {
        return null;
    }

    @Override
    public Boolean updateContact(ContactUpdateDto contactUpdateDto) {
        return null;
    }

    @Override
    public Boolean deleteContact(UUID id) {
        return null;
    }
}

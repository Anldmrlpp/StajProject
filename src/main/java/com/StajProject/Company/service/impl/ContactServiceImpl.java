package com.StajProject.Company.service.impl;

import com.StajProject.Company.dto.ContactCreateDto;
import com.StajProject.Company.dto.ContactDto;
import com.StajProject.Company.exception.ErrorMessages;
import com.StajProject.Company.exception.PermissionException;
import com.StajProject.Company.mapper.ContactMapper;
import com.StajProject.Company.mapper.PageMapperHelper;
import com.StajProject.Company.model.Contact;
import com.StajProject.Company.repository.ContactRepository;
import com.StajProject.Company.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;
    private final ContactMapper mapper;

    @Override
    public UUID createContact(ContactCreateDto contactCreateDto) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactCreateDto, contact);
        Contact response = repository.save(contact);

        return response.getId();
    }

    @Override
    public ContactDto getContactById(UUID id) {
        Optional<Contact> response = repository.findById(id);

        if(response.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.CONTACT_NOT_FOUND);
        }

        return mapper.toDto(response.get());
    }

    @Override
    public Page<ContactDto> getAllContacts(Pageable pageable) {
        Page<Contact> response = repository.findAll(pageable);

        if(response.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.CONTACT_NOT_FOUND);
        }

        return PageMapperHelper.mapEntityPageToDtoPage(response, mapper);
    }

    @Override
    public Boolean deleteContactById(UUID id) {
        Optional<Contact> response = repository.findById(id);

        if(response.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.CONTACT_NOT_FOUND);
        }

        repository.delete(response.get());

        return true;
    }
}
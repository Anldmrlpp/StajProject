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

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public UUID createContact(ContactCreateDto contactCreateDto) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactCreateDto, contact);
        Contact savedContact = contactRepository.save(contact);

        return savedContact.getId();
    }

    @Override
    public ContactDto getContactById(UUID id) {
        Optional<Contact> contactOptional = contactRepository.findById(id);

        if (contactOptional.isPresent()){
            return contactMapper.toDto(contactOptional.get());
        }else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.CONTACT_NOT_FOUND);
        }

    }

    @Override
    public Page<ContactDto> getAllContacts(Pageable pageable) {
        Page<Contact> contactsPage = contactRepository.findAll(pageable);

        if(contactsPage.isEmpty()){
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.CONTACT_NOT_FOUND);
        }

        return PageMapperHelper.mapEntityPageToDtoPage(contactsPage,contactMapper);
    }

    @Override
    public Boolean deleteContactById(UUID id) {
        Optional<Contact> contactOptional = contactRepository.findById(id);

        if(contactOptional.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.CONTACT_NOT_FOUND);
        }

        contactRepository.delete(contactOptional.get());

        return true;
    }

}

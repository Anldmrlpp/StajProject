package com.StajProject.Company.controller;

import com.StajProject.Company.api.ContactApi;
import com.StajProject.Company.dto.ContactCreateDto;
import com.StajProject.Company.dto.ContactDto;
import com.StajProject.Company.dto.ContactUpdateDto;
import com.StajProject.Company.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ContactController implements ContactApi {
    private final ContactService service;

    @Override
    public ResponseEntity<UUID> createContact(ContactCreateDto contactCreateDto){
        return ResponseEntity.ok(service.createContact(contactCreateDto));
    }

    @Override
    public ResponseEntity<ContactDto> getContactById(UUID id) {
        return ResponseEntity.ok(service.getContactById(id));
    }

    @Override
    public ResponseEntity<Page<ContactDto>> getAllContacts(Pageable pageable) {
        return ResponseEntity.ok(service.getAllContacts(pageable));
    }

    @Override
    public ResponseEntity<Boolean> updateContact(ContactUpdateDto contactUpdateDto) {
        return ResponseEntity.ok(service.updateContact(contactUpdateDto));
    }

    @Override
    public ResponseEntity<Boolean> deleteContact(UUID id) {
        return ResponseEntity.ok(service.deleteContact(id));
    }

}

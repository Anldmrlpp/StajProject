package com.StajProject.Company.mapper;

import com.StajProject.Company.dto.ContactDto;
import com.StajProject.Company.model.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper extends BaseMapper<Contact, ContactDto> {



}
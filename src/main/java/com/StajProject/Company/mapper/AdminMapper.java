package com.StajProject.Company.mapper;

import com.StajProject.Company.dto.AdminDto;
import com.StajProject.Company.model.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper extends BaseMapper<Admin, AdminDto> {

}

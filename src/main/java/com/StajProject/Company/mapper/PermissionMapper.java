package com.StajProject.Company.mapper;

import com.StajProject.Company.dto.PermissionDto;
import com.StajProject.Company.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends BaseMapper<Permission, PermissionDto> {
//Permission ile PermissionDto arasında dönüşüm yapıldı


}
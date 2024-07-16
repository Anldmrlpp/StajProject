package com.StajProject.Company.mapper;

import com.StajProject.Company.dto.EmployeeDto;
import com.StajProject.Company.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends BaseMapper<Employee, EmployeeDto> {
//BaseMapper arayüzünü genişletildi ve Employee ile EmployeeDto arasında dönüşüm işlemleri gerçekleştirildi.


}
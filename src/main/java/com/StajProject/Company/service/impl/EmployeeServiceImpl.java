package com.StajProject.Company.service.impl;

import com.StajProject.Company.dto.EmployeeCreateDto;
import com.StajProject.Company.dto.EmployeeDto;
import com.StajProject.Company.dto.EmployeeUpdateDto;
import com.StajProject.Company.exception.ErrorMessages;
import com.StajProject.Company.exception.PermissionException;
import com.StajProject.Company.mapper.EmployeeMapper;
import com.StajProject.Company.model.Employee;
import com.StajProject.Company.repository.EmployeeRepository;
import com.StajProject.Company.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository; // EmployeeRepository arayüzü, veritabanı işlemleri için kullanıldı.
    private final EmployeeMapper employeeMapper; // EmployeeMapper arayüzü, Employee ve EmployeeDto dönüşümlerini sağladı.

    @Override
    public UUID createEmployee(EmployeeCreateDto employeeCreateDto) {
        // Yeni bir Employee nesnesi oluşturuldu ve employeeCreateDto'dan gelen veriler kopyalandı.
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeCreateDto, employee);

        // Yeni çalışanın varsayılan izin bakiyesi 15 olarak ayarlandı.
        employee.setLeaveBalance(15);

        // Oluşturulan Employee nesnesi veritabanına kaydedildi.
        Employee savedEmployee = employeeRepository.save(employee);

        // Kaydedilen çalışanın kimliği (id) döndürüldü.
        return savedEmployee.getId();
    }

    @Override
    public EmployeeDto getEmployee(String email) {
        // Verilen e-posta adresine sahip çalışanın veritabanında varlığı kontrol edildi.
        Optional<Employee> existEmployee = employeeRepository.findByEmail(email);

        // Eğer çalışan bulunamazsa PermissionException fırlatılır mesajı verildi.
        if (existEmployee.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }

        // Bulunan çalışan EmployeeDto'ya dönüştürülerek döndürüldü.
        return employeeMapper.toDto(existEmployee.get());
    }

    @Override
    public EmployeeDto updateEmployee(String email, EmployeeUpdateDto employeeUpdateDto) {
        // Verilen e-posta adresine sahip çalışanın varlığı kontrol edildi.
        Optional<Employee> existResponse = employeeRepository.findByEmail(email);

        // Eğer çalışan bulunursa güncelleme işlemi yapıldı.
        if (existResponse.isPresent()) {
            Employee existEmployee = existResponse.get();

            // Yeni verilerle mevcut çalışan nesnesi güncellendi.
            BeanUtils.copyProperties(employeeUpdateDto, existEmployee);

            // Güncellenmiş çalışan veritabanına kaydedildi.
            Employee updatedEmployee = employeeRepository.save(existEmployee);

            // Güncellenmiş çalışan EmployeeDto'ya dönüştürülerek döndürüldü.
            return employeeMapper.toDto(updatedEmployee);
        } else {
            // Eğer çalışan bulunamazsa PermissionException fırlatıldı.
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }
    }

    @Override
    public Boolean deleteEmployee(String email) {
        // Verilen e-posta adresine sahip çalışanın varlığını kontrol edildi.
        Optional<Employee> existResponse = employeeRepository.findByEmail(email);

        // Eğer çalışan bulunursa silme işlemi yapıldı.
        if (existResponse.isEmpty()) {
            // Eğer çalışan bulunamazsa PermissionException fırlatıldı.
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_NOT_FOUND);
        }

        // Silinecek çalışan nesnesi elde edildi.
        Employee existEmployee = existResponse.get();

        // Çalışan veritabanından silindi.
        employeeRepository.delete(existEmployee);

        // İşlem başarılı olduğunda true döndürüldü.
        return true;
    }

    public List<EmployeeDto> getAllEmployees() {
        // Tüm çalışanlar veritabanından alındı.
        List<Employee> existEmployees = employeeRepository.findAll();

        // Tüm çalışanlar EmployeeDto listesine dönüştürülerek döndürüldü.
        return employeeMapper.toDtoList(existEmployees);
    }
}

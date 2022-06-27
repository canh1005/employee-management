package com.exercise.services;

import com.exercise.dto.EmployeeDTO;
import com.exercise.entity.AvatarUpload;
import com.exercise.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDTO> findAll();

    Page<EmployeeDTO> findAllEmployeeWithPage(Integer page);

    EmployeeDTO findEmployeeById(Integer id) throws Exception;

    String deleteById(Integer employeeId);


    EmployeeDTO saveEmployee(EmployeeDTO employee);

    List<Employee> findByPhone(String phone);

    <S extends Employee> S save(S entity);

    List<EmployeeDTO> findEmployeeByNameContaining(String name);

    Page<EmployeeDTO> findEmployeeByNameWithPage(String name, Integer page) throws Exception;

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws Exception;

    String saveImage(MultipartFile file, Integer employeeId) throws Exception;

    Integer deleteMultipleEmployees(List<Integer> ids);

    Page<EmployeeDTO> findAllEmployeesByTeamID(Integer teamID, Integer page);

    Employee uploadAvatarThumbnail(Employee employee, @ModelAttribute AvatarUpload songThumbnailUpload) throws IOException;
}

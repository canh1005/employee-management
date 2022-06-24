package com.exercise.services.impl;

import com.exercise.dto.EmployeeDTO;
import com.exercise.entity.Advances;
import com.exercise.entity.Employee;
import com.exercise.entity.Working;
import com.exercise.repository.AdvanceRepository;
import com.exercise.repository.EmployeeRepository;
import com.exercise.repository.TeamRespository;
import com.exercise.repository.WorkingRepository;
import com.exercise.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TeamRespository teamRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private WorkingRepository workingRepository;
    @Autowired
    private AdvanceRepository advanceRepository;

    @Override
    public List<EmployeeDTO> findAll() {
        List<EmployeeDTO> listEmployeeDTO = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()
        ) {
            EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
            listEmployeeDTO.add(employeeDTO);
        }
        return listEmployeeDTO;
    }


    @Override
    public Page<EmployeeDTO> findAllEmployeeWithPage(Integer page) {
        Integer pageSize = 5;
        Page<Employee> pageOfEmployeeEntity = employeeRepository.findAll(PageRequest.of(page, pageSize));
        return pageOfEmployeeEntity.map(employee -> mapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public EmployeeDTO findEmployeeById(Integer id) {
        Optional<Employee> employeeEntity = employeeRepository.findById(id);
        logger.info("Employee" + employeeEntity);
        if (employeeEntity.isPresent()) {
            EmployeeDTO employeeDTO = mapper.map(employeeEntity, EmployeeDTO.class);
            return employeeDTO;
        } else {
            return null;
        }
    }

    @Override
    public String deleteById(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        List<Working> working = workingRepository.findByEmployeeId(employeeId);
        List<Advances> advance = advanceRepository.findByEmployeeId(employeeId);
        if (employee.isPresent()) {
            logger.info("employee: " + employee.get().getId());
            if (working.size() > 0 || advance.size() > 0) {
                workingRepository.deleteByEmployeeId(employeeId);
                logger.info("Deleted working success....");
                advanceRepository.deleteByEmployeeId(employeeId);
                logger.info("Deleted advance success....");
            }
            employeeRepository.deleteById(employeeId);
            return null;
        } else {
            return "Eployee NOT_FOUND!";
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

        employeeRepository.deleteAllById(integers);
    }

    @Override
    public List<Employee> findByPhone(String phone) {
        return employeeRepository.findByPhone(phone);
    }

    @Override
    public <S extends Employee> S save(S entity) {
        return employeeRepository.save(entity);
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO newEmployeeDTO) {
        Employee employeeEntity = mapper.map(newEmployeeDTO, Employee.class);
        this.save(employeeEntity);
        return newEmployeeDTO;
    }

    @Override
    public List<EmployeeDTO> findEmployeeByNameContaining(String name) {
        if (name != "") {
            List<EmployeeDTO> listOfEmployeeDTO = new ArrayList<>();
            for (Employee employee : employeeRepository.findByFullNameContaining(name)
            ) {
                EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
                listOfEmployeeDTO.add(employeeDTO);
            }
            return listOfEmployeeDTO;
        } else {
            return this.findAll();
        }
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws Exception {
        Optional<Employee> employeeEntity = employeeRepository.findById(employeeDTO.getId());
        if (employeeEntity.isPresent()) {
            employeeEntity.get().setId(employeeDTO.getId());
            employeeEntity.get().setPhone(employeeDTO.getPhone());
            employeeEntity.get().setStartDay(employeeDTO.getStartDay());
            employeeEntity.get().setAddress(employeeDTO.getAddress());
            employeeEntity.get().setAge(employeeDTO.getAge());
            employeeEntity.get().setFullName(employeeDTO.getFullName());
            employeeEntity.get().setMale(employeeDTO.getMale());
            employeeEntity.get().setMoneyPerHour(employeeDTO.getMoneyPerHour());
            employeeEntity.get().getTeam().setId(employeeDTO.getTeamID());
            this.save(employeeEntity.get());
            return employeeDTO;
        } else {
            throw new Exception("Employee not found!" + employeeDTO.getId());
        }
    }

    @Override
    public String saveImage(MultipartFile file, Integer employeeId) throws Exception {
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if (employee.isPresent()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            employee.get().setData(file.getBytes());
            employee.get().setImgName(fileName);
            employeeRepository.save(employee.get());
            return "save success";
        } else {
            throw new Exception("Employee NOT_FOUND!");
        }
    }

    @Override
    @Transactional
    public Integer deleteMultipleEmployees(List<Integer> ids) {
        employeeRepository.deleteMultipleEmployeesWithIds(ids);
        return 1;
    }
}

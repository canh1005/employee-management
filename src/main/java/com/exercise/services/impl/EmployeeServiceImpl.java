package com.exercise.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.exercise.dto.EmployeeDTO;
import com.exercise.entity.*;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "cncloud",
            "api_key", "885459265841563",
            "api_secret", "InwTu_XGPh8cg8GWT9DsMOrni3M"));

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
    public EmployeeDTO findEmployeeById(Integer id) throws Exception {
        Optional<Employee> employeeEntity = employeeRepository.findById(id);
        logger.info("EmployeeEntity: " + employeeEntity);
        if (employeeEntity.isPresent()) {
            EmployeeDTO employeeDTO = mapper.map(employeeEntity.get(), EmployeeDTO.class);
            logger.info("Employee: " + employeeDTO);
            return employeeDTO;
        } else {
            throw new Exception("Employee NOT_FOUND!");
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
    public Page<EmployeeDTO> findEmployeeByNameWithPage(String name, Integer page) throws Exception {
        if (name != "") {
            Integer pageSize = 5;
            Page<Employee> listOfEmployee = employeeRepository.findByFullNameContaining(name, PageRequest.of(page, pageSize));
            if (listOfEmployee.getContent().isEmpty()) {
                throw new Exception("No employeee found with: " + name);
            }
            return listOfEmployee.map(employee -> mapper.map(employee, EmployeeDTO.class));
        } else {
            return this.findAllEmployeeWithPage(page);
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
            Optional<Team> team = teamRepository.findById(employeeDTO.getTeamID());
            employeeEntity.get().setTeam(team.get());
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
            AvatarUpload avatarUpload = new AvatarUpload();
            avatarUpload.setFile(file);
            this.uploadAvatarThumbnail(employee.get(), avatarUpload);
            employeeRepository.save(employee.get());
            return "Upload Success";
        } else {
            throw new Exception("Employee NOT_FOUND!");
        }
    }

    @Override
    @Transactional
    public Integer deleteMultipleEmployees(List<Integer> ids) {
        List<Working> working = workingRepository.findAllById(ids);
        List<Advances> advances = advanceRepository.findAllById(ids);
        if (working.size() > 0 || advances.size() > 0) {
            workingRepository.deleteMultipleEmployeesWithIds(ids);
            logger.info("delete working susscess: ");
            advanceRepository.deleteMultipleEmployeesWithIds(ids);
            logger.info("delete advances susscess: ");
        }
        employeeRepository.deleteMultipleEmployeesWithIds(ids);
        return 1;
    }

    @Override
    public Page<EmployeeDTO> findAllEmployeesByTeamID(Integer teamID, Integer page) {
        Integer pageSize = 5;
        Page<Employee> listEmployee = employeeRepository.findAllEmployeesByTeamId(teamID, PageRequest.of(page, pageSize));
        logger.info("listEmployee: " + listEmployee);
        return listEmployee.map(employee -> mapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Employee uploadAvatarThumbnail(Employee employee, @ModelAttribute AvatarUpload avatarThumbnailUpload) throws IOException {
        Map uploadResult = null;

        if (avatarThumbnailUpload.getFile() != null && !avatarThumbnailUpload.getFile().isEmpty()) {
            uploadResult = cloudinary.uploader().upload(avatarThumbnailUpload.getFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "auto", "folder", "/avatar"));
            avatarThumbnailUpload.setPublicId((String) uploadResult.get("public_id"));
            Object version = uploadResult.get("version");

            logger.info("Upload source success: " + uploadResult);

            if (version instanceof Integer) {
                avatarThumbnailUpload.setVersion(Long.valueOf(((Integer) version)));
            } else {
                avatarThumbnailUpload.setVersion((Long) version);
            }

            avatarThumbnailUpload.setSignature((String) uploadResult.get("signature"));
            avatarThumbnailUpload.setFormat((String) uploadResult.get("format"));
            avatarThumbnailUpload.setResourceType((String) uploadResult.get("resource_type"));
        }

        String avatarThumbUrl = avatarThumbnailUpload.getUrl(cloudinary);
        employee.setImgName(avatarThumbUrl);

        return employee;
    }

}

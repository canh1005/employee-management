package com.exercise.services.impl;

import com.exercise.dto.AdvanceDTO;
import com.exercise.dto.WorkingDTO;
import com.exercise.entity.Advances;
import com.exercise.entity.Employee;
import com.exercise.entity.Working;
import com.exercise.repository.EmployeeRepository;
import com.exercise.repository.WorkingRepository;
import com.exercise.services.WorkingService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkingServiceImpl implements WorkingService {
    @Autowired
    private WorkingRepository workingRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper mapper;
    private final static Logger logger = LoggerFactory.getLogger(WorkingServiceImpl.class);


    @Override
    public List<WorkingDTO> findAllWorking() {
        List<WorkingDTO> listOfWorking = new ArrayList<>();
        for (Working working : workingRepository.findAll()
        ) {
            WorkingDTO workingDTO = mapper.map(working, WorkingDTO.class);
            listOfWorking.add(workingDTO);
        }
        logger.info("listWorking" + listOfWorking);
        return listOfWorking;
    }


    @Override
    public Page<Working> findAll(Pageable pageable) {
        return workingRepository.findAll(pageable);
    }

    @Override
    public String deleteWorkingById(Integer workingId) {
        Optional<Working> working = workingRepository.findById(workingId);
        if (working.isPresent()) {
            workingRepository.deleteById(workingId);
        } else {
            return "NOT_FOUND";
        }
        return null;
    }

    @Override
    public WorkingDTO addWorking(WorkingDTO workingDTO) throws Exception {
        Optional<Employee> employeeEntity = employeeRepository.findById(workingDTO.getEmployeeID());
        if (employeeEntity.isEmpty()) {
            throw new Exception("Employee is not existed!");
        }
        if (workingRepository.existsByDate(workingDTO.getDate())) {
            List<Working> workings = workingRepository.findByDate(workingDTO.getDate());
            for (Working working : workings
            ) {
                if (working.getEmployee().getId() == workingDTO.getEmployeeID()) {
                    throw new Exception("Working date is exist!");
                }

            }

        }
//        if (workingRepository.existsByDateAndEmployeeId(workingDTO.getDate(), workingDTO.getEmployeeID())) {
//            throw new Exception("Working date is exist!");
//        }
        Working workingEntity = mapper.map(workingDTO, Working.class);
        workingRepository.save(workingEntity);
        return workingDTO;

    }

    @Override
    public List<WorkingDTO> getWorkingDTOByEmployeeID(Integer employeeID) {
        List<Working> workings = workingRepository.findByEmployeeIdOrderByDateAsc(employeeID);
        List<WorkingDTO> workingDTOS = new ArrayList<>();

        for (Working working : workings
        ) {
            WorkingDTO workingDTO = mapper.map(working, WorkingDTO.class);
            workingDTOS.add(workingDTO);
        }
        return workingDTOS;
    }

    @Override
    public Page<WorkingDTO> findAdvancesWithPagination(Integer employeeId, Integer page) throws Exception {
        boolean working = workingRepository.existsByEmployeeId(employeeId);
        Integer pageSize = 5;
        if (working) {
            Page<Working> listOfWorking = workingRepository.findAllByEmployeeIdOrderByDateAsc(employeeId, PageRequest.of(page, pageSize));
            return listOfWorking.map(workingItem -> mapper.map(workingItem, WorkingDTO.class));
        }
        throw new Exception("Working NOT_FOUND!");
    }


}

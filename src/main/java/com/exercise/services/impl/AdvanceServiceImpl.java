package com.exercise.services.impl;

import com.exercise.dto.AdvanceDTO;
import com.exercise.entity.Advances;
import com.exercise.entity.Employee;
import com.exercise.repository.AdvanceRepository;
import com.exercise.repository.EmployeeRepository;
import com.exercise.services.AdvanceService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdvanceServiceImpl implements AdvanceService {
    private final static Logger logger = LoggerFactory.getLogger(AdvanceServiceImpl.class);
    @Autowired
    private AdvanceRepository advanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<AdvanceDTO> findAllAdvance() {
        List<AdvanceDTO> listOfAdvanceDTO = new ArrayList<>();
        for (Advances advance : advanceRepository.findAll()
        ) {
            AdvanceDTO advanceDTO = mapper.map(advance, AdvanceDTO.class);
            listOfAdvanceDTO.add(advanceDTO);
        }
        return listOfAdvanceDTO;
    }

    @Override
    public AdvanceDTO addAdvance(AdvanceDTO newAdvanceDTO) throws Exception {
        logger.info("advanceEntity" + newAdvanceDTO);

        Optional<Employee> employeeEntity = employeeRepository.findById(newAdvanceDTO.getEmployeeID());
        if (employeeEntity.isEmpty()) {
            throw new Exception("Employee is not existed!");
        }
//        if (advanceRepository.existsByDate(newAdvanceDTO.getDate())) {
//            List<Advances> advances = advanceRepository.findByDate(newAdvanceDTO.getDate());
//            for (Advances advance : advances
//            ) {
//                if (advance.getEmployee().getId() == newAdvanceDTO.getEmployeeID()) {
//                    throw new Exception("Advance date is exist!");
//                }
//            }
//        }
        Advances advancesEntity = mapper.map(newAdvanceDTO, Advances.class);
        logger.info("advanceEntity" + advancesEntity);
        advanceRepository.save(advancesEntity);
        return newAdvanceDTO;
    }

    @Override
    public String deleteAdvance(LocalDate date, Integer employeeId) {
        boolean advances = advanceRepository.existsByEmployeeId(employeeId);
        if (advances) {
            advanceRepository.deleteAdvance(date, employeeId);
        } else {
            return "EmployeeID NOT_FOUND!";
        }
        return null;
    }

    @Override
    public List<AdvanceDTO> findAllAdvanceByEmployeeId(Integer employeeId) {
        List<AdvanceDTO> listOfAdvanceDTO = new ArrayList<>();
        for (Advances advance : advanceRepository.findByEmployeeIdOrderByDateAsc(employeeId)
        ) {
            AdvanceDTO advanceDTO = mapper.map(advance, AdvanceDTO.class);
            listOfAdvanceDTO.add(advanceDTO);
        }
        return listOfAdvanceDTO;
    }

    @Override
    public Page<AdvanceDTO> findAdvancesWithPagination(Integer employeeId, Integer page) throws Exception {
        Integer pageSize = 5;
        Page<Advances> listOfAdvances = advanceRepository.findAllEmployeeWithPage(employeeId, PageRequest.of(page, pageSize));
//        Page<Advances> listOfAdvances = advanceRepository.findAllByEmployeeIdOrderByDateAsc(employeeId, PageRequest.of(page, pageSize));
        return listOfAdvances.map(advances -> mapper.map(advances, AdvanceDTO.class));
    }

}

package com.exercise.services;

import com.exercise.dto.WorkingDTO;
import com.exercise.entity.Working;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WorkingService {


    List<WorkingDTO> findAllWorking();

    Page<Working> findAll(Pageable pageable);

    String deleteWorkingById(Integer integer);

    WorkingDTO addWorking(WorkingDTO workingDTO) throws Exception;


    List<WorkingDTO> getWorkingDTOByEmployeeID(Integer employeeID);
}

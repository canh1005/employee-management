package com.exercise.services;

import com.exercise.dto.AdvanceDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdvanceService {
    List<AdvanceDTO> findAllAdvance();

    AdvanceDTO addAdvance(AdvanceDTO newAdvanceDTO) throws Exception;

    String deleteAdvance(Integer advanceID);

    List<AdvanceDTO> findAllAdvanceByEmployeeId(Integer employeeId);

    Page<AdvanceDTO> findAdvancesWithPagination(Integer employeeId, Integer page) throws Exception;
}

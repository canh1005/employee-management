package com.exercise.services;

import com.exercise.dto.AdvanceDTO;

import java.util.List;

public interface AdvanceService {
    List<AdvanceDTO> findAllAdvance();

    AdvanceDTO addAdvance(AdvanceDTO newAdvanceDTO) throws Exception;

    String deleteAdvance(Integer advanceID);

    List<AdvanceDTO> findAllAdvanceByEmployeeId(Integer employeeId);
}

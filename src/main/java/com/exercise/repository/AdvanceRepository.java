package com.exercise.repository;

import com.exercise.dto.AdvanceDTO;
import com.exercise.entity.Advances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface AdvanceRepository extends JpaRepository<Advances, Integer> {
    boolean existsByDate(Date date);

    boolean existsByEmployeeId(Integer employeeId);

    List<Advances> findByEmployeeId(Integer employeeId);

    List<Advances> findByDate(Date date);

    @Transactional
    void deleteByEmployeeId(Integer employeeID);
}

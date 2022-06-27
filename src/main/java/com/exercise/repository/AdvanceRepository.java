package com.exercise.repository;

import com.exercise.dto.AdvanceDTO;
import com.exercise.entity.Advances;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AdvanceRepository extends JpaRepository<Advances, Integer> {
    boolean existsByDate(Date date);

    boolean existsByEmployeeId(Integer employeeId);

    List<Advances> findByEmployeeId(Integer employeeId);

    List<Advances> findByDate(Date date);

    Page<Advances> findAllByEmployeeId(Integer employeeId, Pageable of);

    @Transactional
    void deleteByEmployeeId(Integer employeeID);

    @Modifying
    @Query(value = "delete from advances where advances.employee_id in ?1", nativeQuery = true)
    Integer deleteMultipleEmployeesWithIds(List<Integer> ids);

    @Query(value = "SELECT sum(money) FROM employee_db.advances where advances.employee_id=?1 and date between ?2 and ?3 ", nativeQuery = true)
    Double totalAdvancesMoney(Integer employeeId, LocalDate startDay, LocalDate endDay);
}

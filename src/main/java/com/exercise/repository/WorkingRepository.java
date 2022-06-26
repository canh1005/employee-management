package com.exercise.repository;

import com.exercise.entity.Advances;
import com.exercise.entity.Employee;
import com.exercise.entity.Working;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WorkingRepository extends JpaRepository<Working, Integer> {
    boolean existsByDate(Date date);

    boolean existsByEmployeeId(Integer employeeId);

    @Transactional
    void deleteByEmployeeId(Integer employeeID);

    List<Working> findByEmployeeId(Integer employeeID);

    List<Working> findByDate(Date date);

    Page<Working> findAllByEmployeeId(Integer employeeId, Pageable of);

    @Query(value = "select COUNT(date) from working where employee_id=?1 and date between ?1 and ?2", nativeQuery = true)
    Integer countDayOfWork(Integer employeeId, LocalDate startDay, LocalDate endDay);
    @Modifying
    @Query(value = "delete from working where working.employee_id in ?1", nativeQuery = true)
    Integer deleteMultipleEmployeesWithIds(List<Integer> ids);
}

package com.exercise.repository;

import com.exercise.entity.Advances;
import com.exercise.entity.Employee;
import com.exercise.entity.Working;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WorkingRepository extends JpaRepository<Working, Integer> {
    boolean existsByDate(Date date);

    boolean existsByEmployeeId(Integer employeeId);

    boolean existsByDateAndEmployeeId(Date date, Integer employeeId);

    @Transactional
    void deleteByEmployeeId(Integer employeeID);

    List<Working> findByEmployeeIdOrderByDateAsc(Integer employeeID);

    @Query(value = "select * from working where working.employee_id in ?1", nativeQuery = true)
    List<Working> findAllByEmployeeIdWithIds(List<Integer> ids);

    List<Working> findByDate(Date date);

    Page<Working> findAllByEmployeeIdOrderByDateAsc(Integer employeeId, Pageable of);

    @Query(value = "select COUNT(date) from working where employee_id=?1 and Date(date) between ?2 and ?3", nativeQuery = true)
    Integer countDayOfWork(Integer employeeId, LocalDate startDay, LocalDate endDay);

    @Query(value = "SELECT sum(hour) * money_per_hour FROM employee_db.working inner join employee_db.employees on working.employee_id = employees.employee_id where working.employee_id=?1 and date between ?2 and ?3 ;", nativeQuery = true)
    Double totalGet(Integer employeeId, LocalDate startDay, LocalDate endDay);

    @Modifying
    @Query(value = "delete from working where working.employee_id in ?1", nativeQuery = true)
    Integer deleteMultipleEmployeesWithIds(List<Integer> ids);
}

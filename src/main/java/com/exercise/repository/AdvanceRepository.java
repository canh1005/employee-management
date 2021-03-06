package com.exercise.repository;

import com.exercise.dto.AdvanceDTO;
import com.exercise.entity.Advances;
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

public interface AdvanceRepository extends JpaRepository<Advances, Integer> {
    boolean existsByDate(Date date);

    boolean existsByEmployeeId(Integer employeeId);

    @Query(value = "select advance_id,date,sum(money) as money, employee_id from employee_db.advances where advances.employee_id=?1 group by Date(advances.date) order by Date(advances.date) asc", nativeQuery = true,
            countQuery = "select count(*) from employee_db.advances where advances.employee_id=?1")
    Page<Advances> findAllEmployeeWithPage(Integer employeeId, Pageable of);

    List<Advances> findByEmployeeIdOrderByDateAsc(Integer employeeId);

    @Query(value = "select * from advances where advances.employee_id in ?1", nativeQuery = true)
    List<Advances> findAllByEmployeeIdWithIds(List<Integer> ids);

    List<Advances> findByDate(Date date);

    Page<Advances> findAllByEmployeeIdOrderByDateAsc(Integer employeeId, Pageable of);

    @Transactional
    void deleteByEmployeeId(Integer employeeID);

    @Modifying
    @Transactional
    @Query(value = "delete from employee_db.advances where Date(date)=?1 and employee_id=?2", nativeQuery = true)
    void deleteAdvance(LocalDate date, Integer employeeId);

    @Modifying
    @Query(value = "delete from advances where advances.employee_id in ?1", nativeQuery = true)
    Integer deleteMultipleEmployeesWithIds(List<Integer> ids);

    @Query(value = "SELECT sum(money) FROM employee_db.advances where advances.employee_id=?1 and date between ?2 and ?3 ", nativeQuery = true)
    Double totalAdvancesMoney(Integer employeeId, LocalDate startDay, LocalDate endDay);
}

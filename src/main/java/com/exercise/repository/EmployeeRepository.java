package com.exercise.repository;

import com.exercise.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByPhone(String phone);

    List<Employee> findByFullNameContaining(String fullName);
    Page<Employee> findByFullNameContaining(String fullName, Pageable of);
    @Modifying
    @Query(value = "delete from employees e where e.employee_id in ?1", nativeQuery = true)
    Integer deleteMultipleEmployeesWithIds(List<Integer> ids);
}

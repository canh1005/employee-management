package com.exercise;


import com.exercise.entity.Employee;
import com.exercise.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.text.ParseException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class EmployeeRepositoryTests {
    @Autowired private EmployeeRepository repo;
    @Test
    public void testAddEmp() throws ParseException {
        Employee emp = new Employee();
        emp.setFullName("Thu Nguyen");
        emp.setAge(20);
        emp.setAddress("Ha Noi");
        emp.setMale(false);
        emp.setPhone("01234567");
        emp.setStartDay("10-10-1997");
        emp.setMoneyPerHour(5.0);

        repo.save(emp);
    }

}

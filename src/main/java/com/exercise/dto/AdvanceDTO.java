package com.exercise.dto;

import com.exercise.entity.AdvanceID;
import com.exercise.entity.Employee;
import com.exercise.entity.EmployeeID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.MapsId;
import java.util.Date;

@Component
@Data
@NoArgsConstructor
public class AdvanceDTO {
    private Integer id;
    private Date date;
    //    private AdvanceID id;
    private Double money;
    //    private Employee employee;


    private Integer employeeID;
}

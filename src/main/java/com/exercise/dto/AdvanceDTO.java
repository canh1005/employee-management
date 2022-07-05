package com.exercise.dto;

import com.exercise.entity.AdvanceID;
import com.exercise.entity.Employee;
import com.exercise.entity.EmployeeID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.MapsId;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Component
@Data
@NoArgsConstructor
public class AdvanceDTO {
    private Integer id;
    @NotNull(message = "Date can not be null")
    private Date date;
    @Min(value = 1, message = "Money must be over 0")
    private Double money;
    @NotNull(message = "Employee Id can not be null")
    private Integer employeeID;
}

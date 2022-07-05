package com.exercise.dto;

import com.exercise.entity.Working;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Component
@NoArgsConstructor
public class WorkingDTO {
    private Integer id;
    @Min(value = 0, message = "Hour must be over 0")
    @Max(value = 24, message = "Hour must be less than 24")
    private Double hour;
    @NotNull(message = "Date can not be null")
    private Date date;
    @NotNull(message = "Employee can not not null")
    private Integer employeeID;
}

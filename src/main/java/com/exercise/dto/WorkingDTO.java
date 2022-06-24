package com.exercise.dto;

import com.exercise.entity.Working;
import lombok.*;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Component
@NoArgsConstructor
public class WorkingDTO {
    private Integer id;
    private Double hour;
    private Date date;
    private Integer employeeID;
}

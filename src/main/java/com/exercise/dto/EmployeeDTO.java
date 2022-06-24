package com.exercise.dto;

import com.exercise.entity.Employee;
import com.exercise.entity.Team;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private String fullName;
    private String address;
    private Integer age;
    private Double moneyPerHour;
    private Date startDay;
    private String phone;
    private Boolean male;
    private Integer teamID;
    private String imgName;

}

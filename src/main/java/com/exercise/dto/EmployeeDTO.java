package com.exercise.dto;

import com.exercise.entity.Employee;
import com.exercise.entity.Team;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
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
    @Min(value = 18, message = "Age must be over 17")
    private Integer age;
    @Positive(message = "money per hour must be over 0")
    private Double moneyPerHour;
    private Date startDay;
    @Pattern(regexp = "\\d{10}", message = "Phone number is invalid")
    private String phone;
    private Boolean male;
    private Integer teamID;
    private String imgName;

}

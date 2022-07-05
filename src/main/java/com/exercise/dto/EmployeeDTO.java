package com.exercise.dto;

import com.exercise.entity.Employee;
import com.exercise.entity.Team;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id;
    @NotEmpty(message = "Name can not be empty")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 32 characters long")
    private String fullName;
    private String address;
    @Min(value = 18, message = "Age must be over 17")
    private Integer age;
    @Positive(message = "money per hour must be over 0")
    private Double moneyPerHour;
    @NotNull(message = "Date can not be null")
    private Date startDay;
    @NotBlank(message = "Phone can not be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone number is invalid")
    private String phone;
    private Boolean male;
    @NotNull(message = "Team id can not null")
    private Integer teamID;
    private String imgName;

}

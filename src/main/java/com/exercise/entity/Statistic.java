package com.exercise.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class Statistic {
    private Integer numberOfWorkingDay;
    private Double totalGet;
    private Double totalAdvances;
    private Double totalSalary;

}

package com.exercise.services;

import com.exercise.entity.Statistic;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@Service
public interface StatisticService {


//    Statistic getTotalSalary(Integer employeeId);

    Statistic getTotalSalary(Integer employeeId, YearMonth yearMonth);
}

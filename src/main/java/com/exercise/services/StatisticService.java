package com.exercise.services;

import com.exercise.entity.Statistic;
import org.springframework.stereotype.Service;

@Service
public interface StatisticService {


    Statistic getTotalSalary(Integer employeeId);
}

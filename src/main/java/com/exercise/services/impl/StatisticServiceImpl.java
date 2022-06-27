package com.exercise.services.impl;

import com.exercise.entity.Statistic;
import com.exercise.repository.AdvanceRepository;
import com.exercise.repository.WorkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

@Service
public class StatisticServiceImpl implements com.exercise.services.StatisticService {
    private final static Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);
    @Autowired
    private WorkingRepository workingRepository;
    @Autowired
    private AdvanceRepository advanceRepository;


    @Override
    public Statistic getTotalSalary(Integer employeeId, YearMonth yearMonth) {
        Statistic statistic = new Statistic();
        Double totalSalary = 0.0;
        Double totalGet = 0.0;
        Double totalAdvancesMoney = 0.0;
        YearMonth month = yearMonth;
        LocalDate startOfMonth = month.atDay(1);
        LocalDate endOfMonth = month.atEndOfMonth();
        Integer count = workingRepository.countDayOfWork(employeeId, startOfMonth, endOfMonth);
        totalGet = workingRepository.totalGet(employeeId, startOfMonth, endOfMonth);
        totalAdvancesMoney = advanceRepository.totalAdvancesMoney(employeeId, startOfMonth, endOfMonth);
        if(totalAdvancesMoney != null){
            totalSalary = totalGet - totalAdvancesMoney;
        }
        else{
            totalSalary = totalGet;
            totalAdvancesMoney = 0.0;
        }
        logger.info("month: " + month);
        logger.info("startOfMonth: " + startOfMonth);
        logger.info("endOfMonth: " + endOfMonth);

        logger.info("Count: " + count);
        logger.info("totalGet: " + totalGet);
        logger.info("totalAdvancesMoney: " + totalAdvancesMoney);
        logger.info("totalAdvancesMoney: " + totalSalary);
        statistic.setNumberOfWorkingDay(count);
        statistic.setTotalGet(totalGet);
        statistic.setTotalAdvances(totalAdvancesMoney);
        statistic.setTotalSalary(totalSalary);
        logger.info("Statistics: " + statistic);
        return statistic;
    }

}

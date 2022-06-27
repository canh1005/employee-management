package com.exercise.services.impl;

import com.exercise.entity.Statistic;
import com.exercise.repository.AdvanceRepository;
import com.exercise.repository.WorkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StatisticServiceImpl implements com.exercise.services.StatisticService {
    private final static Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);
    @Autowired
    private WorkingRepository workingRepository;
    @Autowired
    private AdvanceRepository advanceRepository;


    @Override
    public Statistic getTotalSalary(Integer employeeId) {
        Statistic statistic = new Statistic();
        LocalDate currentDate = LocalDate.now();
        LocalDate startDay = currentDate.withDayOfMonth(1);
        LocalDate endDay = currentDate.withDayOfMonth(30);
        Integer count = workingRepository.countDayOfWork(employeeId, startDay, endDay);
        Double totalGet = workingRepository.totalGet(employeeId, startDay, endDay);
        Double totalAdvancesMoney = advanceRepository.totalAdvancesMoney(employeeId, startDay, endDay);
        Double totalSalary = totalGet - totalAdvancesMoney;
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

package com.exercise.services.impl;

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

    //    public Statistic getStatistic(Integer employeeId) {
//        return 1;
//    }
    @Override
    public Integer countDate() {
        LocalDate startDay = LocalDate.of(2022, 06, 10);
        LocalDate endDay = LocalDate.of(2022, 06, 30);
        Integer count = workingRepository.countDayOfWork(3, startDay, endDay);
        logger.info("Count: " + count);
        return 1;
    }

}

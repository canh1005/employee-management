package com.exercise.controller;

import com.exercise.entity.ResponseObject;
import com.exercise.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getStatistic(@RequestParam(value = "employee_id") Integer employeeId) {
        try {
            return ResponseEntity.ok().body(new ResponseObject("Ok", "Get Statistic Success!", statisticService.getTotalSalary(employeeId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", e.getMessage(), null));
        }
    }
}

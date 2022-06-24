package com.exercise.controller;

import com.exercise.dto.WorkingDTO;
import com.exercise.entity.Employee;
import com.exercise.entity.ResponseObject;
import com.exercise.entity.Working;
import com.exercise.services.EmployeeService;
import com.exercise.services.StatisticService;
import com.exercise.services.WorkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/working")
public class WorkingController {
    private final static Logger logger = LoggerFactory.getLogger(WorkingController.class);

    @Autowired
    private WorkingService workingService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StatisticService statisticService;

    @GetMapping("/getAll")
    ResponseEntity<ResponseObject> getAllWorking(@RequestParam(value = "employee_id") Integer id) {
        try {
            return ResponseEntity.ok().body(new ResponseObject("Ok", "Get Working Success!", workingService.getWorkingDTOByEmployeeID(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Failed", "Get Working Failed!" + e.getMessage(), null));
        }

    }

    @PostMapping("/create")
    ResponseEntity<ResponseObject> createNewWorking(@RequestBody WorkingDTO workingDTO) {
        try {
            return ResponseEntity.ok().body(new ResponseObject("Ok", "Get Working Success!", workingService.addWorking(workingDTO)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Failed", "Get Working Failed!" + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    ResponseEntity<ResponseObject> deleteWorking(@RequestParam(value = "working_id") Integer workingId) {
        logger.info("workingID" + workingId);
        try {
            return ResponseEntity.ok().body(new ResponseObject("Ok", "Delete working Success!", workingService.deleteWorkingById(workingId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Delete working Failed!" + e.getMessage(), null));
        }
    }

    @GetMapping("/statistic")
    public String getStatistic() {
        statisticService.countDate();
        return "";
    }
}

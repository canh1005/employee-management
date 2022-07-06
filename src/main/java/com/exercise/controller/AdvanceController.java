package com.exercise.controller;

import com.exercise.dto.AdvanceDTO;
import com.exercise.entity.ResponseObject;
import com.exercise.services.AdvanceService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/advance")
public class AdvanceController {
    @Autowired
    private AdvanceService advanceService;

    private final static Logger logger = LoggerFactory.getLogger(AdvanceController.class);

    @GetMapping("/getAll")
    ResponseEntity<ResponseObject> getAllAdvance(@RequestParam(value = "employee_id") Integer id) {
        try {
            return ResponseEntity.ok().body(new ResponseObject("OK", "Get list advance success!", advanceService.findAllAdvanceByEmployeeId(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Failed", e.getMessage(), null));
        }
    }

    @GetMapping("/getPage")
    ResponseEntity<ResponseObject> getAllAdvanceWithPagination(@RequestParam(value = "employee_id") Integer id, @RequestParam(value = "page") Integer page) {
        try {
            return ResponseEntity.ok().body(new ResponseObject("OK", "Get list advance success!", advanceService.findAdvancesWithPagination(id, page)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Failed", e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    ResponseEntity<ResponseObject> addNewAdvance(@RequestBody @Valid AdvanceDTO advanceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ResponseObject("Failed", bindingResult.getAllErrors().get(0).getDefaultMessage(), null));
        } else {
            try {
                return ResponseEntity.ok().body(new ResponseObject("OK", "Advance has been inserted!", advanceService.addAdvance(advanceDTO)));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new ResponseObject("Failed", e.getMessage(), null));
            }
        }
    }

    @DeleteMapping("/delete")
    ResponseEntity<ResponseObject> deleteAdvance(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam(value = "employee_id") Integer employeeId) {

        logger.info("date: "+date);
        try {
            return ResponseEntity.ok().body(new ResponseObject("OK", "Advance has been deleted!", advanceService.deleteAdvance(date,employeeId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Failed", e.getMessage(), null));
        }
//        return null;
    }
}

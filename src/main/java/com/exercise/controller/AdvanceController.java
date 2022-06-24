package com.exercise.controller;

import com.exercise.dto.AdvanceDTO;
import com.exercise.entity.ResponseObject;
import com.exercise.services.AdvanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.badRequest().body(new ResponseObject("OK", "Get list advance failed!", null));
        }
    }

    @PostMapping("/create")
    ResponseEntity<ResponseObject> addNewAdvance(@RequestBody AdvanceDTO advanceDTO) {
        try {
            return ResponseEntity.ok().body(new ResponseObject("OK", "Advance has been inserted!", advanceService.addAdvance(advanceDTO)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Failed", "Failed to insert!" + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    ResponseEntity<ResponseObject> deleteAdvance(@RequestParam(value = "advance_id") Integer advanceID) {
        try {
            return ResponseEntity.ok().body(new ResponseObject("OK", "Advance has been deleted!", advanceService.deleteAdvance(advanceID)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Failed", "Failed to deleted!" + e.getMessage(), null));
        }
    }
}

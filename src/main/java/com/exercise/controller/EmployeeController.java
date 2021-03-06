package com.exercise.controller;

import com.exercise.dto.EmployeeDTO;
import com.exercise.entity.Employee;
import com.exercise.entity.ResponseObject;
import com.exercise.entity.Team;
import com.exercise.repository.TeamRespository;
import com.exercise.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TeamRespository teamRespository;

    @GetMapping("/getAll")
    ResponseEntity<ResponseObject> getAllEmployee() {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Get list employee success!", employeeService.findAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Get list employee failed!" + e.getMessage(), null));
        }

    }

    @GetMapping("/getPage")
    ResponseEntity<ResponseObject> getAllEmployeeWithPagination(@RequestParam(value = "page") Integer page) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Get list employee success!", employeeService.findAllEmployeeWithPage(page)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Get list employee failed!" + e.getMessage(), null));
        }

    }

    @GetMapping("/find-by-id")
    ResponseEntity<ResponseObject> findById(@RequestParam(value = "employee_id") Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Employee Found!", employeeService.findEmployeeById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    ResponseEntity<ResponseObject> createNewEmployee(@RequestBody @Valid EmployeeDTO newEmployee, BindingResult bindingResult) {
        logger.info("Inserting ..." + newEmployee);
        if (bindingResult.hasErrors()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("failed", bindingResult.getAllErrors().get(0).getDefaultMessage(), null));

        } else {
            List<Employee> foundEmployees = employeeService.findByPhone(newEmployee.getPhone().trim());
            try {
                if (foundEmployees.size() > 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("failed", "Phone already existed!", null));
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Employee has been insert!", employeeService.saveEmployee(newEmployee)));
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("failed", e.getMessage(), null));
            }
        }
    }

    @DeleteMapping("/delete-by-id")
    ResponseEntity<ResponseObject> deleteEmployeeById(@RequestParam(value = "employee_id") Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Employee has been deleted!", employeeService.deleteById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", e.getMessage() + id, null));
        }
    }

    @GetMapping("/findByName")
    ResponseEntity<ResponseObject> findEmployeeByName(@RequestParam(value = "name") String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Employee has been found!", employeeService.findEmployeeByNameContaining(name)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Employee Not Found!", null));
        }
    }

    @GetMapping("/findByNameWithPage")
    ResponseEntity<ResponseObject> findEmployeeByNameWithPage(@RequestParam(value = "name") String name, @RequestParam(value = "page") Integer page) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Employee has been found!", employeeService.findEmployeeByNameWithPage(name, page)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    ResponseEntity<ResponseObject> updateEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("failed", bindingResult.getAllErrors().get(0).getDefaultMessage(), null));
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Employee has been update", employeeService.updateEmployee(employeeDTO)));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Failed", e.getMessage(), null));
            }
        }
    }

    @DeleteMapping("/delete")
    ResponseEntity<ResponseObject> deleteMultipleEmployees(@RequestParam List<Integer> ids) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Delete success!", employeeService.deleteMultipleEmployees(ids)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Failed", e.getMessage(), null));
        }
    }

    @GetMapping("/getEmployeeByTeam")
    ResponseEntity<ResponseObject> getEmployeeByTeam(@RequestParam(value = "team_id") Integer teamID, @RequestParam(value = "page") Integer page) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Get list employee success!", employeeService.findAllEmployeesByTeamID(teamID, page)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Failed", e.getMessage(), null));
        }
    }
}

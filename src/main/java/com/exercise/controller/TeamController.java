package com.exercise.controller;

import com.exercise.dto.TeamDTO;
import com.exercise.entity.ResponseObject;
import com.exercise.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/getAll")
    ResponseEntity<ResponseObject> getAllTeam() {
        try {
            return ResponseEntity.ok().body(new ResponseObject("OK", "Get list team success!", teamService.findAllTeam()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Get list team failed!" + e.getMessage(), null));

        }
    }

    @GetMapping("/getPage")
    ResponseEntity<ResponseObject> getAllTeamWithPagination(@RequestParam(value = "page") Integer page) {
        try {
            return ResponseEntity.ok().body(new ResponseObject("OK", "Get list team success!", teamService.findAllTeamWithPage(page)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Get list team failed!" + e.getMessage(), null));

        }
    }

    @PostMapping("/create")
    ResponseEntity<ResponseObject> createNewEmployee(@RequestBody @Valid TeamDTO newTeamDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Failed", bindingResult.getAllErrors().get(0).getDefaultMessage(), null));
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Employee has been insert!", teamService.addTeam(newTeamDTO)));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Failed", e.getMessage(), null));
            }
        }
    }
}

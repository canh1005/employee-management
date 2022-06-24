package com.exercise.controller;

import com.exercise.entity.ResponseObject;
import com.exercise.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/upload")
    ResponseEntity<ResponseObject> uploadImage(@RequestBody MultipartFile file, Integer employeeId) {
        try {
            return ResponseEntity.ok().body(new ResponseObject("OK", "Upload success!" + file.getOriginalFilename(), employeeService.saveImage(file, employeeId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Failed", "Upload failed!" + e.getMessage(), null));
        }
    }
}

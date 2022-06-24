package com.exercise.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@NoArgsConstructor
@Component
public class TeamDTO {
    private Integer id;
    private String name;
}

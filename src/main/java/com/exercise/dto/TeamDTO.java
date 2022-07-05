package com.exercise.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@Component
public class TeamDTO {
    private Integer id;
    @NotBlank(message = "Name can not be blank")
    private String name;
}

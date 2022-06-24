package com.exercise.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class ImageDTO {
    private Integer id;
    private String url;
    private String type;
    private long size;
    private Integer employeeId;


}

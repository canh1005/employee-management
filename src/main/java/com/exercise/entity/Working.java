package com.exercise.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Working {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "working_id")
    private Integer id;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Double hour;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
}

package com.exercise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.aop.Advisor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "advances")
public class Advances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advance_id")
    private Integer id;

    private Double hour;

//    @EmbeddedId
//    private AdvanceID id;

//    @EmbeddedId
//    private AdvanceID date;

    //    @Id
//    @MapsId("date")
    private Date date;

    //    @MapsId("id")
//    private Integer employeeId;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}

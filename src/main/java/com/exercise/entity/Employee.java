package com.exercise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
//    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private Integer id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false, length = 2)
    private Integer age;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Double moneyPerHour;
    @Column(nullable = false, length = 10, unique = true)
    private String phone;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "YYYY-MM-DD")
    @Column(nullable = false)
    private Date startDay;
    @Column(nullable = false)
    private Boolean male;

    private String imgName;


    public Employee(String fullName, Integer age, String address, Double moneyPerHour, String phone, Date startDay, Boolean male, Team team) {
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.moneyPerHour = moneyPerHour;
        this.phone = phone;
        this.startDay = startDay;
        this.male = male;
        this.team = team;
    }

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Working> working = new HashSet<>();

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Advances> advance = new HashSet<>();

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "image_id", referencedColumnName = "image_id")
//    private Image image;
}

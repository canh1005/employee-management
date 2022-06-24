package com.exercise.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EmployeeID implements Serializable {
    public Integer id;
}

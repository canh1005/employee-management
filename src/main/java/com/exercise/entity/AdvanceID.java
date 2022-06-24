package com.exercise.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class AdvanceID implements Serializable {
    private Date date;
//
//    @Embedded
//    private EmployeeID employeeId;


//    public EmployeeID getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(EmployeeID employeeId) {
//        this.employeeId = employeeId;
//    }
//
//    public AdvanceID() {
//    }

}

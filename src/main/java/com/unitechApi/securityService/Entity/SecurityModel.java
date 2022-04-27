package com.unitechApi.securityService.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "securitydata", schema = "security")
public class SecurityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mobileNo;
    private String meetName;
    private String purpose;
    private LocalTime inTime;
    private LocalTime outTime;
    private LocalDate entryDate;
    @Enumerated(EnumType.STRING)
    private Cstatus cstatus;

    public SecurityModel() {
        this.cstatus = Cstatus.PENDING;
    }
}

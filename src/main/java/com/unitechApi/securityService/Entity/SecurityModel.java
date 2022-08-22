package com.unitechApi.securityService.Entity;

import com.unitechApi.securityService.Enum.CheckEnumEntry;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Getter
@Setter
@Entity
@Table(name = "securitydata", schema = "security")
public class SecurityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  ticketId= UUID.randomUUID().toString();
    private String name;
    private String mobileNo;
    private String meetName;
    private String purpose;
    private LocalTime inTime;
    private LocalTime outTime;
    private LocalDate entryDate;
    @Enumerated(EnumType.STRING)
    private Cstatus cstatus;
    @Enumerated(EnumType.STRING)
    private CheckEnumEntry checkEnumEntry;

    public SecurityModel() {
        this.cstatus = Cstatus.PENDING;
    }
}

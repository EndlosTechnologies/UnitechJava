package com.unitechApi.securityService.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecuritySave {
    private Long id;
    private String name;
    private String mobileNo;
    private String meetName;
    private String purpose;
    private LocalTime inTime;
    private LocalTime outTime;
    private LocalDate entryDate;


   
}

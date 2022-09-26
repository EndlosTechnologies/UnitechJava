package com.unitechApi.user.training.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "link_corner_training",schema = "profiledetails")
public class LinkCornerTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "l_t_id", nullable = false)
    private Long l_t_id;
    private String actualProduction;
    private String hardWaste;
    private String efficiency;
    private String tubeLoading;
    private String waxRollCleaning;
    private String tailNedActivity;
    private String doffActivity;
    private String bottomClear;
    private String redLightAttend;
    private String eycCleaning;
    private String nozzleChokeUp;
    private String remarks;



}

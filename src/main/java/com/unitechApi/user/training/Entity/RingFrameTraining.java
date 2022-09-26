package com.unitechApi.user.training.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Data
@Table(name = "ringframe_training",schema = "profiledetails")
@Entity
public class RingFrameTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_t_id", nullable = false)
    private Long r_t_id;
    private String machineName;
    private int count ;
    private int peacingGate ;
    private String machineKnowladge;
    private String behavior;
    private String productionAchive;
    private String existingPost;
    private String remarks;

}

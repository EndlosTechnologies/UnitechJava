package com.unitechApi.user.training.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Data
@Entity
@Table(name = "carding_training",schema = "profiledetails")
public class CardingTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carding_id", nullable = false)
    private Long carding_id;
    private String machineName;
    private String colierTraining;
    private String peacing;
    private String castCleaning;
    private String autoCanChanger;
    private String lighting;
    private String silverBrakageAttend;
    private String autoLeveler;
    private String production;
    private String remarks;







    public Long getCarding_id() {
        return carding_id;
    }

    public void setCarding_id(Long carding_id) {
        this.carding_id = carding_id;
    }
}

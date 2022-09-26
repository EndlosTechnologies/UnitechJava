package com.unitechApi.user.training.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Data
@Entity
@Table(name = "draw_frames_training",schema = "profiledetails")
public class DrawFramesTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_t_id", nullable = false)
    private Long d_t_id;
    private String machineName;
    private String rollChangeAttend;
    private String coilerCleaning;
    private String casterCleaning;
    private String machineCleaning;
    private String exhaustCleaning;
    private String singleOrDoubleDelivery;
    private String autoChanger;
    private String production;
    private String autoLeveller;
    private String  remark;

    public Long getD_t_id() {
        return d_t_id;
    }

    public void setD_t_id(Long d_t_id) {
        this.d_t_id = d_t_id;
    }
}

package com.unitechApi.user.training.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity

@Getter
@Setter
@Data
@Table(name = "speed_frame_training",schema = "profiledetails")
public class SpeedFrameTrainingEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_t_id", nullable = false)
    private Long s_t_id;
    private String machineName;
    private String peacing;
    private String  doffingTime;
    private String spindleCheck;
    private String flyerChecking;
    private String draftingCleaning;
    private String lightAttend;
    private String productionHank;
    private String remark;





    public Long getS_t_id() {
        return s_t_id;
    }

    public void setS_t_id(Long s_t_id) {
        this.s_t_id = s_t_id;
    }
}

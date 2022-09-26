package com.unitechApi.user.training.Entity;

import javax.persistence.*;

@Entity
@Table(name = "speed_frame_training",schema = "profiledetails")

public class SpeedFrameTrainingEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_t_id", nullable = false)
    private Long s_t_id;

    public Long getS_t_id() {
        return s_t_id;
    }

    public void setS_t_id(Long s_t_id) {
        this.s_t_id = s_t_id;
    }
}

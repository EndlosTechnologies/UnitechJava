package com.unitechApi.user.training.Entity;

import javax.persistence.*;

@Entity
@Table(name = "training_period",schema = "profiledetails")
public class TrainingSession {
    @Id
    @Column(name = "trainingId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingId;

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }
}

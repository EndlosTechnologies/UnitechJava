package com.unitechApi.user.training.service;

import com.unitechApi.user.training.Entity.SpeedFrameTrainingEntity;

import java.util.List;

public interface SpeedFrameService {
    SpeedFrameTrainingEntity saveData(SpeedFrameTrainingEntity speedFrameTraining);
    SpeedFrameTrainingEntity getById(Long s_t_id);
    List<SpeedFrameTrainingEntity> getAll();
}

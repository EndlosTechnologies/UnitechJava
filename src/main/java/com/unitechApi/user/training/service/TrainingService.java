package com.unitechApi.user.training.service;

import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.user.training.Entity.TrainingSession;
import org.springframework.data.domain.Page;

public interface TrainingService {
    TrainingSession saveData(TrainingSession trainingSession);
    TrainingSession getById(Long trainingId);
    Page<TrainingSession> getAll(Pagination pagination);
    TrainingSession updateTrainingSession(Long trainingId,TrainingSession trainingSession);
}

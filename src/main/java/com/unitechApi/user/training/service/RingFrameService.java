package com.unitechApi.user.training.service;

import com.unitechApi.user.training.Entity.RingFrameTraining;

import java.util.List;

public interface RingFrameService {
    RingFrameTraining saveData(RingFrameTraining ringFrameTraining);
    RingFrameTraining getById(Long r_t_id);
    List<RingFrameTraining> getAll();
}

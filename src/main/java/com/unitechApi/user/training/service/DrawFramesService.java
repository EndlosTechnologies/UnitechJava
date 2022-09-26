package com.unitechApi.user.training.service;

import com.unitechApi.user.training.Entity.DrawFramesTraining;

import java.util.List;

public interface DrawFramesService {
    DrawFramesTraining saveData(DrawFramesTraining drawFramesTraining);

    DrawFramesTraining getById(Long d_t_id);

    List<DrawFramesTraining> getAll();
}

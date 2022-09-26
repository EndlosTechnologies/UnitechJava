package com.unitechApi.user.training.service;

import com.unitechApi.user.training.Entity.CardingTraining;

import java.util.List;

public interface CardingService {
    CardingTraining saveData(CardingTraining cardingTraining);

    CardingTraining getById(Long c_t_id);

    List<CardingTraining> getAll();
}

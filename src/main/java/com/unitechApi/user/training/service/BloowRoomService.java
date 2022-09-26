package com.unitechApi.user.training.service;

import com.unitechApi.user.training.Entity.BloowRoomTraining;

import java.util.List;

public interface BloowRoomService {
    BloowRoomTraining save(BloowRoomTraining bloowRoomTraining);

    BloowRoomTraining getById(Long b_t_id);

    List<BloowRoomTraining> getAll();
}

package com.unitechApi.user.training.service;

import com.unitechApi.user.training.Entity.LinkCornerTraining;

import java.util.List;

public interface LinkCornerService {
    LinkCornerTraining saveData(LinkCornerTraining linkCornerTraining);
    LinkCornerTraining getById(Long l_t_id);
    List<LinkCornerTraining> getAll();

}

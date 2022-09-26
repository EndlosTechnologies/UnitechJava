package com.unitechApi.user.training.Repository;

import com.unitechApi.user.training.Entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<TrainingSession,Long> {
}

package com.unitechApi.user.Repository;

import com.unitechApi.user.model.QualificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository<QualificationModel, Long> {
}

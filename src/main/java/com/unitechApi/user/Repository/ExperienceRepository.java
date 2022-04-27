package com.unitechApi.user.Repository;

import com.unitechApi.user.model.ExperienceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<ExperienceModel, Long> {
}

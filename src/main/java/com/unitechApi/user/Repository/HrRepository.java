package com.unitechApi.user.Repository;

import com.unitechApi.user.model.HrModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HrRepository extends JpaRepository<HrModel, Long> {
}

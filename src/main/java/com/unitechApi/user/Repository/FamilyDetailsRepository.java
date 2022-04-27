package com.unitechApi.user.Repository;

import com.unitechApi.user.model.FamilyDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyDetailsRepository extends JpaRepository<FamilyDetailsModel, Long> {
}

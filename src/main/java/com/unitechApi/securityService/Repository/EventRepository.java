package com.unitechApi.securityService.Repository;

import com.unitechApi.securityService.Entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity,Long> {
}

package com.unitechApi.store.unit.repository;

import com.unitechApi.store.unit.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit,Long> , JpaSpecificationExecutor<Unit> {
    List<Unit> findByUnitName(String name);
    List<Unit> findByCreated(Date date);
    Boolean existsByUnitName(String unit);
}

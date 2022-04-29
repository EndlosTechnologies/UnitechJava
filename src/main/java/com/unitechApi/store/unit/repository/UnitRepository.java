package com.unitechApi.store.unit.repository;

import com.unitechApi.store.unit.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit,Long> {
    List<Unit> findByUnitName(String name);
    List<Unit> findByCreated(Date date);
}

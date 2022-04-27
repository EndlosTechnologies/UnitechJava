package com.unitechApi.store.storeMangment.repository;

import com.unitechApi.store.storeMangment.Model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit,Long> {
    List<Unit> findByUnitName(String name);
    List<Unit> findByCreated(Date date);
}

package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.Utiliity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface UtilityRepository extends JpaRepository<Utiliity, Long> {
    @Query("select u from Utiliity u where DATE(u.createdAt) between :start and :end")
    List<Utiliity> findByCreatedAtBetween(Date start, Date end);
    @Query("select u from Utiliity u where DATE(u.createdAt) =:createdAt")
    List<Utiliity> findByCreatedAt(@Param("createdAt")Date d1);
}

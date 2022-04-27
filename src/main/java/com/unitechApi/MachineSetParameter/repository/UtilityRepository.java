package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.Utiliity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface UtilityRepository extends JpaRepository<Utiliity, Long> {
    @Query("select u from Utiliity u where DATE(u.createdAt) between :start and :end")
    Page<Utiliity> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select u from Utiliity u where DATE(u.createdAt) =:createdAt")
    Page<Utiliity> findByCreatedAt(@Param("createdAt")Date d1,Pageable pageable);
}

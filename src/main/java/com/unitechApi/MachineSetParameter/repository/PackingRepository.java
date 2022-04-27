package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.Packing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface PackingRepository extends JpaRepository<Packing, Long> {
    @Query("select p from Packing p where DATE(p.createdAt) between :start and :end")
    Page<Packing> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select p from Packing p where DATE(p.createdAt) =:createdAt")
    Page<Packing> findByCreatedAt(@Param("createdAt")Date d1,Pageable pageable);
}

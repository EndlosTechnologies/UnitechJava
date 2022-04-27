package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.Simplex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface SimplexRepository extends JpaRepository<Simplex, Long> {
    @Query("select s from Simplex s where DATE(s.createdAt) between :start and :end")
    Page<Simplex> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select s from Simplex s where DATE(s.createdAt) =:createdAt")
    Page<Simplex> findByCreatedAt(@Param("createdAt")Date d1,Pageable pageable);
    @Query("select c  from Simplex c where DATE(c.shiftDate) between :start and :end")
    List<Simplex> findByShiftdateBetween(Date start, Date end );
    @Query("select c from Simplex c where DATE(c.shiftDate) =?1")
    List<Simplex> findByShiftdate(Date start);
}

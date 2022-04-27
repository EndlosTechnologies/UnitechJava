package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.Carding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface CardingRepository extends JpaRepository<Carding, Long> {
    @Query("select c  from Carding c where DATE(c.createdAt) between :start and :end")
    Page<Carding> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select c from Carding c where DATE(c.createdAt) =:createdAt")
    Page<Carding> findByCreatedAt(@Param("createdAt")Date d1, Pageable pageable);
    @Query("select c  from Carding c where DATE(c.shiftDate) between :start and :end")
    List<Carding> findByShiftdateBetween(Date start, Date end );
    @Query("select c from Carding c where DATE(c.shiftDate) =?1")
    List<Carding> findByShiftdate(@Param("shiftDate") Date start);
}

package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.FinisherperKg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface FinisherRepository extends JpaRepository<FinisherperKg, Long> {
    @Query("select f from FinisherperKg f where DATE(f.createdAt) between :start and :end")
    Page<FinisherperKg> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select f from FinisherperKg f where DATE(f.createdAt) =:createdAt")
    Page<FinisherperKg> findByCreatedAt(@Param("createdAt")Date d1,Pageable pageable);
    @Query("select c  from FinisherperKg c where DATE(c.shiftDate) between :start and :end")
    List<FinisherperKg> findByShiftdateBetween(Date start, Date end );
    @Query("select c from FinisherperKg c where DATE(c.shiftDate) =?1")
    List<FinisherperKg> findByShiftdate(Date start);
}

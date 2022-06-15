package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.RingFrame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface RingFrameRepossitory extends JpaRepository<RingFrame, Long> {
    @Query("select r from RingFrame r where DATE(r.createdAt) between :start and :end")
    List<RingFrame> findByCreatedAtBetween(Date start, Date end );
    @Query("select r from RingFrame r where DATE(r.createdAt) =:createdAt")
    List<RingFrame> findByCreatedAt(@Param("createdAt")Date d1);
    @Query("select c  from RingFrame c where DATE(c.shiftDate) between :start and :end")
    List<RingFrame> findByShiftdateBetween(Date start, Date end );
    @Query("select c from RingFrame c where DATE(c.shiftDate) =?1")
    List<RingFrame> findByShiftdate(Date start);
}

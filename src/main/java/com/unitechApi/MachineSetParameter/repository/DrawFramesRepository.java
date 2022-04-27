package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.Drawframesperkg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface DrawFramesRepository extends JpaRepository<Drawframesperkg, Long> {
    @Query("select d from Drawframesperkg d where DATE(d.createdAt) between :start and :end")
    Page<Drawframesperkg> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select d from Drawframesperkg d where DATE(d.createdAt) =:createdAt")
    Page<Drawframesperkg> findByCreatedAt(@Param("createdAt")Date d1,Pageable pageable);
    @Query("select c  from Drawframesperkg c where DATE(c.shiftDate) between :start and :end")
    List<Drawframesperkg> findByShiftdateBetween(Date start, Date end );
    @Query("select c from Drawframesperkg c where DATE(c.shiftDate) =?1")
    List<Drawframesperkg> findByShiftdate(Date start);
}

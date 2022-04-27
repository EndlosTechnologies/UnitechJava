package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.DrawFramesPerHank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface DrawFramesPerHankRepository extends JpaRepository<DrawFramesPerHank,Long> {
    @Query("select b from DrawFramesPerHank b where DATE(b.createdAt) between :start and :end")
    Page<DrawFramesPerHank> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select b from DrawFramesPerHank b where DATE(b.createdAt) =:createdAt")
    Page<DrawFramesPerHank> findByCreatedAt(@Param("createdAt") java.util.Date d1, Pageable pageable);
    @Query("select c  from DrawFramesPerHank c where DATE(c.shiftDate) between :start and :end")
    List<DrawFramesPerHank> findByShiftdateBetween(Date start, Date end );
    @Query("select c from DrawFramesPerHank c where DATE(c.shiftDate) =?1")
    List<DrawFramesPerHank> findByShiftdate(Date start);
}

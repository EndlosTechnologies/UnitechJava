package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.FinisherperHank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface FinisherperHankRepository extends JpaRepository<FinisherperHank,Long> {
    @Query("select b from FinisherperHank b where DATE(b.createdAt) between :start and :end")
    Page<FinisherperHank> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select b from FinisherperHank b where DATE(b.createdAt) =:createdAt")
    Page<FinisherperHank> findByCreatedAt(@Param("createdAt") java.util.Date d1, Pageable pageable);
    @Query("select c  from FinisherperHank c where DATE(c.shiftDate) between :start and :end")
    List<FinisherperHank> findByShiftdateBetween(Date start, Date end );
    @Query("select c from FinisherperHank c where DATE(c.shiftDate) =?1")
    List<FinisherperHank> findByShiftdate(Date start);
}

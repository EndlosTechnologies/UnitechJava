package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.LapFormer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LapFormerRepository extends JpaRepository<LapFormer ,Long> , JpaSpecificationExecutor<LapFormer> {
    @Query("select b from LapFormer b where DATE(b.createdAt) between :start and :end")
    List<LapFormer> findByCreatedAtBetween(Date start, Date end );;
    @Query("select b from LapFormer b where DATE(b.createdAt) =:createdAt")
    List <LapFormer> findByCreatedAt(@Param("createdAt") Date d1);
    @Query("select c  from LapFormer c where DATE(c.shiftDate) between :start and :end")
    List<LapFormer> findByShiftdateBetween(java.sql.Date start, java.sql.Date end );
    @Query("select c from LapFormer c where DATE(c.shiftDate) =?1")
    List<LapFormer> findByShiftdate(java.sql.Date start);

}

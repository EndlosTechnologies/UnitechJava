package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.BloowRoom;
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
public interface BloowRoomRepository extends JpaRepository<BloowRoom, Long>, JpaSpecificationExecutor<BloowRoom> {
   @Query("select b from BloowRoom b where DATE(b.createdAt) between :start and :end")
    List<BloowRoom> findByCreatedAtBetween(Date start, Date end);
    @Query("select b from BloowRoom b where DATE(b.createdAt) =:createdAt")
    List<BloowRoom> findByCreatedAt(@Param("createdAt")Date d1);
    @Query("select c  from BloowRoom c where DATE(c.shiftDate) between :start and :end")
    List<BloowRoom> findByShiftdateBetween(java.sql.Date start, java.sql.Date end );
    @Query("select c from BloowRoom c where DATE(c.shiftDate) =?1")
    List<BloowRoom> findByShiftdate(@Param("shiftDate") Date start);


}

package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.Comber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ComberRepository extends JpaRepository<Comber ,Long>  {
    @Query("select b from Comber b where DATE(b.createdAt) between :start and :end")
    Page<Comber> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select b from Comber b where DATE(b.createdAt) =:createdAt")
    Page<Comber> findByCreatedAt(@Param("createdAt")Date d1, Pageable pageable);
    @Query("select c  from Comber c where DATE(c.shiftDate) between :start and :end")
    List<Comber> findByShiftdateBetween(Date start, Date end );
    @Query("select c from Comber c where DATE(c.shiftDate) =?1")
    List<Comber> findByShiftdate(Date start);
}

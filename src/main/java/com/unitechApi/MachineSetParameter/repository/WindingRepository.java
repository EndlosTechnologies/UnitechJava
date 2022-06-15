package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.Winding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface WindingRepository extends JpaRepository<Winding, Long> {
    @Query("select w from Winding w where DATE(w.createdAt) between :start and :end")
    List<Winding> findByCreatedAtBetween(Date start, Date end );
    @Query("select w from Winding w where DATE(w.createdAt) =:createdAt")
    List<Winding> findByCreatedAt(@Param("createdAt")Date d1);
}

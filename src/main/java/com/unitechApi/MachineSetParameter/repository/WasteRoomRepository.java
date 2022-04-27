package com.unitechApi.MachineSetParameter.repository;

import com.unitechApi.MachineSetParameter.model.Wasteroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface WasteRoomRepository extends JpaRepository<Wasteroom, Long> {
    @Query("select w from Wasteroom w where DATE(w.createdAt) between :start and :end")
    Page<Wasteroom> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select w from Wasteroom w where DATE(w.createdAt) =:createdAt")
    Page<Wasteroom> findByCreatedAt(@Param("createdAt")Date d1,Pageable pageable);
}

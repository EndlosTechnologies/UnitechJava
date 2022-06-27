package com.unitechApi.store.vendor.Repository;

import com.unitechApi.store.vendor.model.VendorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VendorRepository extends JpaRepository<VendorModel,Long> {
    @Query("select b from VendorModel b where DATE(b.createdAt) between :start and :end")
    List<VendorModel> findByCreatedAtBetween(Date start, Date end);
    @Query("select b from VendorModel b where DATE(b.createdAt) =:createdAt")
    List<VendorModel> findByCreatedAt(@Param("createdAt")Date d1);
}

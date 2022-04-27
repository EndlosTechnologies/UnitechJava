package com.unitechApi.purchase.RawMaterial.vendor.Repository;

import com.unitechApi.purchase.RawMaterial.vendor.model.VendorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface VendorRepository extends JpaRepository<VendorModel,Long> {
    @Query("select b from VendorModel b where DATE(b.createdAt) between :start and :end")
    Page<VendorModel> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select b from VendorModel b where DATE(b.createdAt) =:createdAt")
    Page<VendorModel> findByCreatedAt(@Param("createdAt")Date d1, Pageable pageable);
}

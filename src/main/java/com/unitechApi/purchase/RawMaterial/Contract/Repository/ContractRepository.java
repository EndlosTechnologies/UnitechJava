package com.unitechApi.purchase.RawMaterial.Contract.Repository;

import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface ContractRepository extends JpaRepository<ContractModel,Long> {
    @Query("select b from ContractModel b where DATE(b.createdAt) between :start and :end")
    Page<ContractModel> findByCreatedAtBetween(Date start, Date end , Pageable pageable);
    @Query("select b from ContractModel b where DATE(b.createdAt) =:createdAt")
    Page<ContractModel> findByCreatedAt(@Param("createdAt")Date d1, Pageable pageable);
}

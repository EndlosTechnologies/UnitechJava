package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceModelRepository extends JpaRepository<VendorWisePriceModel,Long> {
    @Query("select p from VendorWisePriceModel p where p.indentPrice.indentId=:indentId")
    List<VendorWisePriceModel> findByIndentId(Long indentId);
}

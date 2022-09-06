package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceModelRepository extends JpaRepository<VendorWisePriceModel,Long> {
    @Query("select p from VendorWisePriceModel p where p.indentPrice.indentId=:indentId")
    List<VendorWisePriceModel> findByIndentId(Long indentId);


    @Query("select f from  VendorWisePriceModel f where f.vendorModelData.id=:vendorId and f.itemModelPrice.itemId=:itemId and f.indentPrice.indentId=:indentId")
    List<VendorWisePriceModel> getAllByVendorIdAndItemId(Long vendorId,Long itemId,Long indentId);
    @Query("select  DISTINCT(p.vendorModelData.id) from VendorWisePriceModel p  where p.indentPrice.indentId=:indentId")
    List<?> getDistinctByVendorId(Long indentId);

    @Query("select  DISTINCT(p.itemModelPrice.itemId) from VendorWisePriceModel p  where p.indentPrice.indentId=:indentId")
    List<?> getDistinctByItemId(Long indentId);



}

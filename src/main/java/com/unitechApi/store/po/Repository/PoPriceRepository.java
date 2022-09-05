package com.unitechApi.store.po.Repository;

import com.unitechApi.store.po.Model.PoPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoPriceRepository extends JpaRepository<PoPrice,Long> {
//    @Query("select DISTINCT p.itemPriceInPersonalOrder,p.vendorWisePriceModel  from PoPrice   p")
//    List<?> findByLock();
}

package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceModelRepository extends JpaRepository<VendorWisePriceModel,Long> {
}

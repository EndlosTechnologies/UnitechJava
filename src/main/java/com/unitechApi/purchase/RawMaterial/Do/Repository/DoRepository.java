package com.unitechApi.purchase.RawMaterial.Do.Repository;

import com.unitechApi.purchase.RawMaterial.Do.Model.DeliveryOrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoRepository extends JpaRepository<DeliveryOrderModel,Long> {
}

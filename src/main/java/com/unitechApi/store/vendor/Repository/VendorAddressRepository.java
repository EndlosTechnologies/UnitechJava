package com.unitechApi.store.vendor.Repository;

import com.unitechApi.store.vendor.model.VendorAddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorAddressRepository extends JpaRepository<VendorAddressModel,Long> {
}

package com.unitechApi.store.vendor.Service;


import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.vendor.model.VendorAddressModel;

import java.util.List;

public interface VendorAddressService {
    VendorAddressModel findById(Long vendorAddressId);
    List<VendorAddressModel> getAll();

}

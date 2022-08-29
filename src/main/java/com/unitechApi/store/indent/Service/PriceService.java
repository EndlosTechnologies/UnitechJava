package com.unitechApi.store.indent.Service;

import com.unitechApi.store.indent.Model.VendorWisePriceModel;

public interface PriceService {
    VendorWisePriceModel findById(Long id);
    VendorWisePriceModel saveData(VendorWisePriceModel vendorWisePriceModel);
}

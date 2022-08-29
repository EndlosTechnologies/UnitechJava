package com.unitechApi.store.indent.Service;

import com.unitechApi.store.indent.Model.VendorWisePriceModel;

import java.util.List;

public interface PriceService {
    VendorWisePriceModel findById(Long id);
    List<VendorWisePriceModel> findByIndentId(Long indentId);
    VendorWisePriceModel saveData(VendorWisePriceModel vendorWisePriceModel);
}

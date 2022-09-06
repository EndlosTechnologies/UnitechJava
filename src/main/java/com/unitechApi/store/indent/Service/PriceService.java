package com.unitechApi.store.indent.Service;

import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.indent.view.ViewByDistinct;

import java.util.List;
import java.util.Map;

public interface PriceService {
    VendorWisePriceModel findById(Long id);
    List<VendorWisePriceModel> findByIndentId(Long indentId);
    VendorWisePriceModel saveData(VendorWisePriceModel vendorWisePriceModel);
    List<?> countDistinctByVendorModelDataId(Long id);
   List<?> getDistinctByItemId(Long indentId);
    List<VendorWisePriceModel> getAllByVendorIdAndItemId(Long vendorId,Long itemId,Long indentId);
}

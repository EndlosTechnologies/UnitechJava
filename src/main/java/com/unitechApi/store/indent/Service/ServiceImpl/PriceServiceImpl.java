package com.unitechApi.store.indent.Service.ServiceImpl;

import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.indent.Repository.IndentRepository;
import com.unitechApi.store.indent.Repository.PriceModelRepository;
import com.unitechApi.store.indent.Service.PriceService;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.vendor.Repository.VendorRepository;
import com.unitechApi.store.vendor.model.VendorModel;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PriceServiceImpl implements PriceService {
    private final PriceModelRepository priceModelRepository;
    private IndentRepository indentRepository;
    private final VendorRepository vendorRepository;

    public PriceServiceImpl(PriceModelRepository priceModelRepository, VendorRepository vendorRepository) {
        this.priceModelRepository = priceModelRepository;
        this.vendorRepository = vendorRepository;
    }

    /**
     * @param id 
     * @return get All Data By Id
     */
    @Override
    public VendorWisePriceModel findById(Long id) {
        return priceModelRepository.findById(id).orElse(null) ;
    }

    /**
     * @param vendorWisePriceModel
     * @return
     */
    @Override
    public VendorWisePriceModel saveData(VendorWisePriceModel vendorWisePriceModel) {

        return null ;
    }
}

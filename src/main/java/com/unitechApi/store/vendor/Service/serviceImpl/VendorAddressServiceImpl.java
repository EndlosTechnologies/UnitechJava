package com.unitechApi.store.vendor.Service.serviceImpl;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.vendor.Repository.VendorAddressRepository;
import com.unitechApi.store.vendor.Service.VendorAddressService;
import com.unitechApi.store.vendor.model.VendorAddressModel;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorAddressServiceImpl implements VendorAddressService {
    private final VendorAddressRepository vendorAddressRepository;

    public VendorAddressServiceImpl(VendorAddressRepository vendorAddressRepository) {
        this.vendorAddressRepository = vendorAddressRepository;
    }

    /**
     * @param vendorAddressId 
     * @return get All Data By VendorAddressId
     */
    @Override
    public VendorAddressModel findById(Long vendorAddressId) {
        return  vendorAddressRepository.findById(vendorAddressId).orElseThrow(()->new ResourceNotFound("Resource Not Found "));

    }

    /**
     * @return  get All Data Vendor Address
     */
    @Override
    public List<VendorAddressModel> getAll() {
        return vendorAddressRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(VendorAddressModel::getId))
                .collect(Collectors.toList());
    }
}

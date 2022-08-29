package com.unitechApi.store.vendor.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.vendor.Service.serviceImpl.VendorAddressServiceImpl;
import com.unitechApi.store.vendor.model.VendorAddressModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/unitech/api/v1/store/vendor/address")
public class VendorAddressController {
    private final VendorAddressServiceImpl vendorAddressServiceImpl;

    public VendorAddressController(VendorAddressServiceImpl vendorAddressServiceImpl) {
        this.vendorAddressServiceImpl = vendorAddressServiceImpl;
    }

    @GetMapping(value = "/{vendorAddressId}")
    public ResponseEntity<?> getByVendorAddresId(@PathVariable Long vendorAddressId) {
        VendorAddressModel vendorAddressModel = vendorAddressServiceImpl.findById(vendorAddressId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(vendorAddressModel), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllVendorAddress() {
        List<?> getAll = vendorAddressServiceImpl.getAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(getAll), HttpStatus.OK);
    }
}

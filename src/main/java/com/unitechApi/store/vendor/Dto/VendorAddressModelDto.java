package com.unitechApi.store.vendor.Dto;

import com.unitechApi.store.vendor.vendorEnum.VendorAddressType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Data
public class VendorAddressModelDto {
    private Long id;
    private String state;
    private String city;
    private String pincode;
    private VendorAddressType vendorAddressType;
    private String address;
}

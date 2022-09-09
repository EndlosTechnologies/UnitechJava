package com.unitechApi.store.vendor.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.vendor.model.VendorAddressModel;
import com.unitechApi.store.vendor.vendorEnum.GstStatus;
import com.unitechApi.store.vendor.vendorEnum.MsmeType;
import com.unitechApi.store.vendor.vendorEnum.PaymentCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VendorDto {
    private Long id;
    private String vendorName;
    private String gstno;
    private String panno;
    private PaymentCondition paymentTermsConditions;
    private Integer paymentDays;
    @Enumerated(EnumType.STRING)
    private GstStatus gstStatus;
    @Enumerated(EnumType.STRING)
    private MsmeType msmeType;
    private String factory;
    private LocalDate msgmeRegisterDate;
    private LocalDate gstForm;
    private LocalDate gstTo;
    private float centralgst;
    private float stategst;
    private float integratedgst;
    private String sezNumber;
    private String refrencesBy;
    private String bankName;
    private String branchName;
    private String bankCityName;
    private Long bankAccountNumber;
    private String ifscCode;
    private String micrCode;
    private String cancelChequeNumber;
    private String supplierscode;
    private String accountGroupHead;
    private String natureOfBussiness;
    private String officePhoneNumber;
    private String ResidentPhoneNumber;
    private String vendorEmail;
    private String webSite;
    private String faxNumber;
    private LocalDate dateOfIncorporation;
    private Set<VendorAddressModel> vendorAddressModels;

    public List<VendorAddressModelDto> getVendorAddressModels() {
        return vendorAddressModels
                .stream()
                .map(this::getByLis).collect(Collectors.toList());
    }

    private VendorAddressModelDto getByLis(VendorAddressModel vendorAddressModel) {
        VendorAddressModelDto vendorAddressModelDto = new VendorAddressModelDto();
        vendorAddressModelDto.setId(vendorAddressModel.getId());
        vendorAddressModelDto.setState(vendorAddressModel.getState());
        vendorAddressModelDto.setPincode(vendorAddressModel.getPincode());
        vendorAddressModelDto.setCity(vendorAddressModel.getCity());
        vendorAddressModelDto.setVendorAddressType(vendorAddressModel.getVendorAddressType());
        vendorAddressModelDto.setAddress(vendorAddressModel.getAddress());

        return  vendorAddressModelDto;
    }

    public void setVendorAddressModels(Set<VendorAddressModel> vendorAddressModels) {
        this.vendorAddressModels = vendorAddressModels;
    }
}

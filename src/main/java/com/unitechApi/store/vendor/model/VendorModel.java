package com.unitechApi.store.vendor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.po.Model.PoPrice;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.vendor.vendorEnum.GstStatus;
import com.unitechApi.store.vendor.vendorEnum.MsmeType;
import com.unitechApi.store.vendor.vendorEnum.PaymentCondition;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendor_details", schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class VendorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long id;
    @Column(name = "vendor_name", nullable = false)
    private String vendorName;


    @Column(name = "gstno",nullable = false)
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "Please Enter valid Gst Number")
    private String gstno;

    /*
     * Sample of Gst Number  gst Number 06BZAHM6385P6Z2
     * */
    @Column(name = "panno",nullable = false)
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "please Enter your Valid Pan Number Details")
    private String panno;
    /*
     * Pan Number = BMPRV6763H
     * */
    @NotNull(message = "please Enter the Payment Conditions")
    @Enumerated(EnumType.ORDINAL)
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
    @Column(name = "suppliers_code",nullable = false)
    private String supplierscode;
    private String accountGroupHead;
    private String natureOfBussiness;
    private String officePhoneNumber;
    private String ResidentPhoneNumber;
    private String vendorEmail;
    private String webSite;
    private String faxNumber;
    private LocalDate dateOfIncorporation;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "store_management", name = "item_Vendor_details"
            ,joinColumns = @JoinColumn(name = "vendor_id")
            ,inverseJoinColumns = @JoinColumn(name = "item_id")
            )
    @JsonIgnoreProperties({"contractModels", "itemRequest", "issueItem","vendorWisePriceDAta", "vendorDate", "unit", "productCategory", "employe", "dataVendorAndIndent"})
    private Set<StoreItemModel> itemData = new HashSet<>();
//    @OneToMany(mappedBy = "vendorData", cascade = CascadeType.ALL)
//    @JsonIgnoreProperties(value = {"vendorData", "vendorWisePriceDAta"})
//    private Set<Indent> indentList;
    @OneToMany(mappedBy = "vendorModelData",cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"vendorModelData","vendorWisePriceDAta"})
    private Set<VendorWisePriceModel> vendorWisePriceDAta;
    @OneToMany(mappedBy = "vendorData",cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"vendorData","vendorData","vendorWisePriceDAta"})
    private Set<VendorAddressModel> vendorAddressModels;

    @OneToMany(mappedBy = "vendorModels",cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"vendorModels","vendorWisePriceDAta"})
    private Set<PoPrice> poPriceSetData;


    @PrePersist
    private void CreatedAt() {
        createdAt = new Date();
    }

    public void deleteItem(StoreItemModel vendorModel) {
        itemData.remove(vendorModel);
    }

    @Override
    public String toString() {
        return "VendorModel{" +
                "id=" + id +
                ", vendorName='" + vendorName + '\'' +
                '}';
    }
}

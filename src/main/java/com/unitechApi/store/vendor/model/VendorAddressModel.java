package com.unitechApi.store.vendor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.vendor.vendorEnum.VendorAddressType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "vendor_address",schema = "store_management")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VendorAddressModel {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String state;
    private String city;
    private String pincode;
    @Enumerated(EnumType.STRING)
    private VendorAddressType vendorAddressType;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "vendor_id"),name = "vendor_id",referencedColumnName = "vendor_id")
    @JsonIgnoreProperties(value = {"vendorAddressModels","itemData","indentList","vendorWisePriceDAta","vendorData"})
    private VendorModel vendorData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "VendorAddressModel{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", pincode='" + pincode + '\'' +
                ", vendorAddressType=" + vendorAddressType +
                '}';
    }
}

package com.unitechApi.purchase.RawMaterial.vendor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendor_details",schema = "purchaser")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class VendorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(name = "vendor_id")
    private  Long id;
    @Column(name = "vendor_name")
    private String vendorName;
    @Column(name = "vendor_address")
    private String vendorAddress;
    @Column(name = "vendor_code")
    private String vendorcode;
    @Column(name = "gstno")
    private String gstno;
    @Column(name = "panno")
    private String panno;
    private String paymentTermsConditions;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @OneToMany(mappedBy = "vendorModel",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("vendorModel")
    private Set<ContractModel> contractModels;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "store_management",name = "item_Vendor_details",
            joinColumns = @JoinColumn(name = "vendor_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    @JsonIgnoreProperties({"contractModels","dataVendorAndItem"})
    private Set<StoreItemModel> itemData=new HashSet<>();

    public Set<StoreItemModel> getDataVendor() {
        return itemData;
    }

    public void setDataVendor(Set<StoreItemModel> itemData) {
        this.itemData = itemData;
    }

    @OneToOne(mappedBy = "vendorDetails",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"vendorDetails"})
    private Indent chooseVendorDetailsInIndent;


    public Indent getChooseVendorDetailsInIndent() {
        return chooseVendorDetailsInIndent;
    }

    public void setChooseVendorDetailsInIndent(Indent chooseVendorDetailsInIndent) {
        this.chooseVendorDetailsInIndent = chooseVendorDetailsInIndent;
    }

    public Set<ContractModel> getContractModels() {
        return contractModels;
    }

    public void setContractModels(Set<ContractModel> contractModels) {
        this.contractModels = contractModels;
    }

    @PrePersist
    private void CreatedAt() {
        createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getVendorcode() {
        return vendorcode;
    }

    public void setVendorcode(String vendorcode) {
        this.vendorcode = vendorcode;
    }

    public String getGstno() {
        return gstno;
    }

    public void setGstno(String gstno) {
        this.gstno = gstno;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getPaymentTermsConditions() {
        return paymentTermsConditions;
    }

    public void setPaymentTermsConditions(String paymentTermsConditions) {
        this.paymentTermsConditions = paymentTermsConditions;
    }
}

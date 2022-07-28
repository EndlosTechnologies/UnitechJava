package com.unitechApi.store.vendor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendor_details", schema = "purchaser")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VendorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(name = "vendor_id")
    private Long id;
    @Column(name = "vendor_name", nullable = false)
    private String vendorName;
    @Column(name = "vendor_address", nullable = false)
    private String vendorAddress;
    @Column(name = "vendor_city", nullable = false)
    @NotNull(message = "You are not Allowed to enter city Null Vale")
    private String city;
    @Column(name = "vebndor_pincode",nullable = false)
    @NotNull(message = "You are not Allowed to enter Pin code Null Vale")
    private Integer pincode;
    @Column(name = "vendor_code",nullable = false)
    private String vendorcode;
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

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "store_management", name = "item_Vendor_details",
            joinColumns = @JoinColumn(name = "vendor_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    @JsonIgnoreProperties({"contractModels", "itemRequest", "issueItem", "vendorDate", "unit", "productCategory", "employe", "dataVendorAndIndent"})
    private Set<StoreItemModel> itemData = new HashSet<>();

    public Set<Indent> getIndentList() {
        return indentList;
    }

    public void setIndentList(Set<Indent> indentList) {
        this.indentList = indentList;
    }

    @OneToMany(mappedBy = "vendorData", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("vendorData")
    private Set<Indent> indentList;

    public Set<StoreItemModel> getItemData() {
        return itemData;
    }

    public void setItemData(Set<StoreItemModel> itemData) {
        this.itemData = itemData;
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
        this.panno = panno.toUpperCase();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public PaymentCondition getPaymentTermsConditions() {
        return paymentTermsConditions;
    }

    public void setPaymentTermsConditions(PaymentCondition paymentTermsConditions) {
        this.paymentTermsConditions = paymentTermsConditions;
    }



    public int getPincode() {
        
        return pincode;
    }

    public void setPincode(int pincode) {
        
        this.pincode = pincode;
    }



    public void deleteItem(StoreItemModel vendorModel) {
        itemData.remove(vendorModel);
    }

    @Override
    public String toString() {
        return "VendorModel{" +
                "id=" + id +
                ", vendorName='" + vendorName + '\'' +
                ", vendorAddress='" + vendorAddress + '\'' +
                ", city='" + city + '\'' +
                ", pincode=" + pincode +
                ", vendorcode='" + vendorcode + '\'' +
                ", gstno='" + gstno + '\'' +
                ", panno='" + panno + '\'' +
                ", paymentTermsConditions=" + paymentTermsConditions +
                ", paymentDays=" + paymentDays +
                ", createdAt=" + createdAt +
                ", itemData=" + itemData +
                ", indentList=" + indentList +
                '}';
    }
}

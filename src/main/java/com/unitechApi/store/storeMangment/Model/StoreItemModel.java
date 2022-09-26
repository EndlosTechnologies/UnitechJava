package com.unitechApi.store.storeMangment.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.AuditingAndResponse.Audit;
import com.unitechApi.store.indent.Model.IndentQuantity;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.po.Model.PoPrice;
import com.unitechApi.store.productCategory.model.ProductCategory;
import com.unitechApi.store.unit.model.Unit;
import com.unitechApi.store.vendor.model.VendorModel;
import com.unitechApi.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;


@Entity
@Getter
@Setter
@Table(name = "item", schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreItemModel extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    @Column(unique = true,nullable = false)
    @NotNull(message = "enter Item Name")
    private String itemName;
    private String itemDescription;
    @Column(nullable = false)
    @NotNull(message = "enter Drawing Number ")
    private String drawingNo;
    @Column(nullable = false)
    @NotNull(message = "enter catalog  Number ")
    private String catalogNo;
    @NotNull(message = "enter frequency")
    @Column(nullable = false)
    private long frequency;
    
    private int RemainingItem;
    @NotNull(message = "enter Tax ")
    @Column(nullable = false)
    private int paytax;
    private Date created;
    private double itemRate;
    public Date getCreated() {
        return created;
    }
    @PrePersist
    public void setCreated() {
        this.created = new Date();
    }

    private Boolean activation;

    private int expiryDays;
    private long quantity;

    @OneToMany(mappedBy = "storeItemModel",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"storeItemModel","indents","usageItems","emp","itemRequest","issueItemsData"})
    private Set<IssueItem> issueItem;
    @OneToMany(mappedBy = "itemPriceInPersonalOrder",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"itemPriceInPersonalOrder","indentDAta"})
    private Set<PoPrice> personalOrderPrice;

    @OneToMany(mappedBy = "storeItemIndentQuantityData" ,cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "storeItemIndentQuantityData")
    private List<IndentQuantity> storeIndentQuantity;
    @OneToMany(mappedBy = "itemModelPrice",cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"itemModelPrice","vendorWisePriceDAta","personalPrice"})
    private Set<VendorWisePriceModel> vendorWisePriceDataWithItem;

    public Set<VendorWisePriceModel> getVendorWisePriceDataWithItem() {
        return vendorWisePriceDataWithItem;
    }

    public void setVendorWisePriceDataWithItem(Set<VendorWisePriceModel> vendorWisePriceDataWithItem) {
        this.vendorWisePriceDataWithItem = vendorWisePriceDataWithItem;
    }

    public List<IndentQuantity> getStoreIndentQuantity() {
        return storeIndentQuantity;
    }

    public void setStoreIndentQuantity(List<IndentQuantity> storeIndentQuantity) {
        this.storeIndentQuantity = storeIndentQuantity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "p_id"), name = "p_id", referencedColumnName = "pid")
    @JsonIgnoreProperties({"item","unit"})
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(foreignKey = @ForeignKey(name = "u_id"), name = "u_id", referencedColumnName = "uid")
    @JsonIgnoreProperties({"itemunit"})
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "emp_id"), name = "emp_id", referencedColumnName = "user_profile_id")
    @JsonIgnoreProperties({"itemModelSet","issueItemsData","itemRequest","response","hrModel","familyDetails","userExperienceData"
    ,"passwordEntity","userQualificationData","roles","indentData"})
    private User employe;
    @ManyToMany(mappedBy = "itemData")
    @JsonIgnoreProperties({"itemData","indentList"})
    private Set<VendorModel> vendorDate=new HashSet<>();

    public Set<VendorModel> getVendorDate() {
        return vendorDate;
    }

    public void setVendorDate(Set<VendorModel> vendorDate) {
        this.vendorDate = vendorDate;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }


    public User getEmploye() {
        return employe;
    }

    public void setEmploye(User employe) {
        this.employe = employe;
    }



    public Set<IssueItem> getIssueItem() {
        return issueItem;
    }

    public void setIssueItem(Set<IssueItem> issueItem) {
        this.issueItem = issueItem;
    }

    public String getDrawingNo() {
        return drawingNo;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    public String getCatalogNo() {
        return catalogNo;
    }

    public void setCatalogNo(String catalogNo) {
        this.catalogNo = catalogNo;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }


    public Boolean getActivation() {
        return activation;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }


    public int getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(int expiryDays) {
        this.expiryDays = expiryDays;
    }



    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public int getRemainingItem() {
        return RemainingItem;
    }

    public void setRemainingItem(int remainingItem) {
        RemainingItem = remainingItem;
    }

    public double getItemRate() {
        return itemRate;
    }

    public void setItemRate(double itemRate) {
        this.itemRate = itemRate;
    }

    public int getPaytax() {
        return paytax;
    }

    public void setPaytax(int paytax) {
        this.paytax = paytax;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreItemModel that = (StoreItemModel) o;
        return frequency == that.frequency && RemainingItem == that.RemainingItem && paytax == that.paytax && expiryDays == that.expiryDays && quantity == that.quantity && itemId.equals(that.itemId) && itemName.equals(that.itemName) && itemDescription.equals(that.itemDescription) && drawingNo.equals(that.drawingNo) && catalogNo.equals(that.catalogNo) && created.equals(that.created) && activation.equals(that.activation) && issueItem.equals(that.issueItem) && productCategory.equals(that.productCategory) && unit.equals(that.unit) && employe.equals(that.employe) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, itemDescription, drawingNo, catalogNo, frequency, RemainingItem, paytax, created, activation, expiryDays, quantity, issueItem, productCategory, unit, employe);
    }



}

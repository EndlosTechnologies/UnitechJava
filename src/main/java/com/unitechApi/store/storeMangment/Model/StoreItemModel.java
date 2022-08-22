package com.unitechApi.store.storeMangment.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.AuditingAndResponse.Audit;
import com.unitechApi.store.indent.Model.IndentQuantity;
import com.unitechApi.store.vendor.model.VendorModel;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.productCategory.model.ProductCategory;
import com.unitechApi.store.unit.model.Unit;
import com.unitechApi.user.model.User;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
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
    @Column(unique = true,nullable = false)
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
    @OneToMany(mappedBy = "storeItemGetQuantity",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"storeItemModel","indents","usageItems","emp","itemRequest","issueItemsData"
            ,"storeItemGetQuantity"})
    private Set<IndentQuantity> indentQuantities;

    public Set<IndentQuantity> getIndentQuantities() {
        return indentQuantities;
    }

    public void setIndentQuantities(Set<IndentQuantity> indentQuantities) {
        this.indentQuantities = indentQuantities;
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
    @OneToMany(mappedBy = "storeItem", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"storeItem","employee","storeItemList"})
    private Set<Indent> itemRequest;
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

    public Set<Indent> getItemRequest() {
        return itemRequest;//.stream().sorted((o1, o2) -> o1.getItemId().compareTo(o2.getItemId())).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void setItemRequest(Set<Indent> itemRequest) {
        this.itemRequest = itemRequest;
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

//    public void deleteVendor(VendorModel vendorModel) {
//        dataVendor.remove(vendorModel);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreItemModel that = (StoreItemModel) o;
        return frequency == that.frequency && RemainingItem == that.RemainingItem && paytax == that.paytax && expiryDays == that.expiryDays && quantity == that.quantity && itemId.equals(that.itemId) && itemName.equals(that.itemName) && itemDescription.equals(that.itemDescription) && drawingNo.equals(that.drawingNo) && catalogNo.equals(that.catalogNo) && created.equals(that.created) && activation.equals(that.activation) && issueItem.equals(that.issueItem) && productCategory.equals(that.productCategory) && unit.equals(that.unit) && employe.equals(that.employe) && itemRequest.equals(that.itemRequest) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, itemDescription, drawingNo, catalogNo, frequency, RemainingItem, paytax, created, activation, expiryDays, quantity, issueItem, productCategory, unit, employe, itemRequest);
    }


    @Override
    public String toString() {
        return "StoreItemModel{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", drawingNo='" + drawingNo + '\'' +
                ", catalogNo='" + catalogNo + '\'' +
                ", frequency=" + frequency +
                ", RemainingItem=" + RemainingItem +
                ", paytax=" + paytax +
                ", created=" + created +
                ", activation=" + activation +
                ", expiryDays=" + expiryDays +
                ", quantity=" + quantity +
                ", issueItem=" + issueItem +
                ", productCategory=" + productCategory +
                ", unit=" + unit +
                ", employe=" + employe +
                ", itemRequest=" + itemRequest +
                '}';
    }
}

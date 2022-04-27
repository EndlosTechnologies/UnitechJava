package com.unitechApi.store.storeMangment.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.AuditingAndResponse.Audit;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.user.model.User;
import org.apache.poi.ss.usermodel.CellType;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "item", schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreItemModel extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String drawingNo;
    private String catalogNo;
    private String frequency;
    private int RemainingItem;
    private int paytax;
    private String name;
    private Date created;

    public Date getCreate() {
        return created;
    }
    @PrePersist
    public void setCreate() {
        this.created = new Date();
    }

    private Boolean activation;
    private Date inDate;
    private int expiryDays;
    private long quantity;
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @OneToMany(mappedBy = "storeItemModel",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"storeItemModel"})
    private Set<IssueItem> issueItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "p_id"), name = "p_id", referencedColumnName = "pid")
    @JsonIgnoreProperties({"item","unit"})
  //  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(foreignKey = @ForeignKey(name = "u_id"), name = "u_id", referencedColumnName = "uid")
    @JsonIgnoreProperties({"itemunit"})
  //  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "emp_id"), name = "emp_id", referencedColumnName = "user_profile_id")
    @JsonIgnoreProperties({"itemModelSet"})
    //  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User employe;

    public User getEmploye() {
        return employe;
    }

    public void setEmploye(User employe) {
        this.employe = employe;
    }

    @OneToMany(mappedBy = "storeItem", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("storeItem")
    private Set<Indent> itemRequest;

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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
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

    public StoreItemModel() {
        this.itemStatus = ItemStatus.PENDING;
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

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(int expiryDays) {
        this.expiryDays = expiryDays;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainingItem() {
        return RemainingItem;
    }

    public void setRemainingItem(int remainingItem) {
        RemainingItem = remainingItem;
    }

    public int getPaytax() {
        return paytax;
    }

    public void setPaytax(int paytax) {
        this.paytax = paytax;
    }
    @Override
    public String toString() {
        return "StoreItemModel{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", drawingNo='" + drawingNo + '\'' +
                ", catalogNo='" + catalogNo + '\'' +
                ", frequency='" + frequency + '\'' +
                ", RemainingItem=" + RemainingItem +
                ", paytax=" + paytax +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", activation=" + activation +
                ", inDate=" + inDate +
                ", expiryDate=" + expiryDays +
                ", quantity=" + quantity +
                ", itemStatus=" + itemStatus +
                ", productCategory=" + productCategory +
                ", unit=" + unit +
                ", itemRequest=" + itemRequest +
                '}';
    }
}

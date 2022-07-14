package com.unitechApi.store.indent.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.vendor.model.VendorModel;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "indent",schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Indent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indentId;
    @NotNull(message = "Enter the estimated Price")
    @Column(nullable = false)
    private float estimatedPrice;
    private float total;
    private Long responseId;
    @NotNull(message = "Enter the quantity ")
    @Column(nullable = false)
    private Long quantity;
    @Enumerated(EnumType.STRING)
    private IndentStatus indentStatus;
    private Long doid;
    private float includingTax;
    private Date created;

    public Date getCreated() {
        return created;
    }
    @PrePersist
    public void setCreated() {
        this.created = new Date();
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "item_id"),name = "item_id",referencedColumnName = "itemId")
    @JsonIgnoreProperties({"itemRequest","issueItem","employe"})
    private StoreItemModel storeItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "emp_id"),name = "emp_id",referencedColumnName = "user_profile_id")
    @JsonIgnoreProperties({"indentData","itemRequest","itemModelSet","issueItemsData"})
    private User employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "issue_id"),name = "issue_id",referencedColumnName = "issueId")
    @JsonIgnoreProperties({"indents","itemRequest","storeItemModel","usageItems","emp"})
    private IssueItem issue;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "store_management",name = "item_Add_In_indent",
            joinColumns = @JoinColumn(name = "indent_id"), inverseJoinColumns = @JoinColumn(name = "item_id") )
    private Set<StoreItemModel> storeItemList=new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "store_management",name = "indent_vendor_detailsID",
            joinColumns = @JoinColumn(name = "indent_id"), inverseJoinColumns = @JoinColumn(name = "vendor_id"))
    @JsonIgnoreProperties({"contractModels","itemData","indentList"})
    private Set<VendorModel> dataVendorAndIndent=new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "ven_id"),name = "ven_id", referencedColumnName = "vendor_id")
    @JsonIgnoreProperties({"indentList","contractModels"})
    private VendorModel vendorData;

    public Set<StoreItemModel> getStoreItemList() {
        return storeItemList;
    }

    public void setStoreItemList(Set<StoreItemModel> storeItemList) {
        this.storeItemList = storeItemList;
    }

    public VendorModel getVendorData() {
        return vendorData;
    }

    public void setVendorData(VendorModel vendorData) {
        this.vendorData = vendorData;
    }

    public void setEstimatedPrice(float estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Set<VendorModel> getDataVendorAndIndent() {
        return dataVendorAndIndent;
    }

    public void setDataVendorAndIndent(Set<VendorModel> dataVendorAndIndent) {
        this.dataVendorAndIndent = dataVendorAndIndent;
    }

    public Float getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(Long estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public Float getIncludingTax() {
        return includingTax;
    }

    public void setIncludingTax(float includingTax) {
        this.includingTax = includingTax;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public Long getDoid() {
        return doid;
    }

    public void setDoid(Long doid) {
        this.doid = doid;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public IssueItem getIssue() {
        return issue;
    }

    public void setIssue(IssueItem issue) {
        this.issue = issue;
    }

    public Indent ()
    {
        this.indentStatus=IndentStatus.GM;
    }

    public Long getIndentId() {
        return indentId;
    }

    public void setIndentId(Long indentId) {
        this.indentId = indentId;
    }



    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public IndentStatus getIndentStatus() {
        return indentStatus;
    }

    public void setIndentStatus(IndentStatus indentStatus) {
        this.indentStatus = indentStatus;
    }


    public StoreItemModel getStoreItem() {
        return storeItem;
    }

    public void setStoreItem(StoreItemModel storeItem) {
        this.storeItem = storeItem;
    }
}

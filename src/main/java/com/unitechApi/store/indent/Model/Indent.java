package com.unitechApi.store.indent.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.user.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "indent",schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Indent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indentId;
    private float estimatedPrice;
    private float total;
    private Long responseId;
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

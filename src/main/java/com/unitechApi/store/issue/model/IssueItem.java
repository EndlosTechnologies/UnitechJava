package com.unitechApi.store.issue.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.AuditingAndResponse.Audit;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.UsageItem;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "issueItem", schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class IssueItem extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long issueId;
    private int quantity;
    private String description;

    private Date issueDate;
    @Enumerated(EnumType.STRING)
    private IssueStatus status;
    private LocalDateTime closeDate;
    private int closeresid;
    private boolean isRaised;
    private int requiredDays;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "item_id"), name = "item_id", referencedColumnName = "itemId")
    @JsonIgnoreProperties("issueItem")
    private StoreItemModel storeItemModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "emp_id"), name = "emp_id", referencedColumnName = "user_profile_id")
    @JsonIgnoreProperties("issueItemsData")
    private User Emp;

    @OneToMany(mappedBy = "issue",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("issue")
    private Set<Indent> indents;
    @OneToMany(mappedBy = "issuedItem",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("issuedItem")
    private Set<UsageItem> usageItems;

    public Set<UsageItem> getUsageItems() {
        return usageItems;
    }

    public void setUsageItems(Set<UsageItem> usageItems) {
        this.usageItems = usageItems;
    }

    public Set<Indent> getIndents() {
        return indents;
    }

    public void setIndents(Set<Indent> indents) {
        this.indents = indents;
    }

    public IssueItem()
    {
        this.status=IssueStatus.PENDING;
    }

    public User getEmp() {
        return Emp;
    }

    public void setEmp(User emp) {
        Emp = emp;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public StoreItemModel getStoreItemModel() {
        return storeItemModel;
    }

    public void setStoreItemModel(StoreItemModel storeItemModel) {
        this.storeItemModel = storeItemModel;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Date getIssueDate() {
        return issueDate;
    }
    @PrePersist
    public void setIssueDate() {
        this.issueDate = new Date();
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }



    public int getCloseresid() {
        return closeresid;
    }

    public void setCloseresid(int closeresid) {
        this.closeresid = closeresid;
    }

    public boolean isRaised() {
        return isRaised;
    }

    public void setRaised(boolean raised) {
        isRaised = raised;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public int getRequiredDays() {
        return requiredDays;
    }

    public void setRequiredDays(int requiredDays) {
        this.requiredDays = requiredDays;
    }

    @Override
    public String toString() {
        return "IssueItem{" +
                "issueId=" + issueId +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", issueDate=" + issueDate +
                ", status=" + status +
                ", closeDate=" + closeDate +
                ", closeresid=" + closeresid +
                ", isRaised=" + isRaised +
                ", requiredDays=" + requiredDays +
                ", storeItemModel=" + storeItemModel +
                ", Emp=" + Emp +
                '}';
    }
}

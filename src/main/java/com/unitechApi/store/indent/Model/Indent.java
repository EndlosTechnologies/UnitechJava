package com.unitechApi.store.indent.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.AuditingAndResponse.Audit;
import com.unitechApi.store.po.Model.PoStore;
import com.unitechApi.store.vendor.model.VendorModel;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.user.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "indent",schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@ToString
public class Indent extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indentId;
    @NotNull(message = "Enter the estimated Price")
    @Column(nullable = false)
    private float estimatedPrice;
    private float total;
    private Long responseId;
    private String indentNumber= RandomStringUtils.randomNumeric(8).toString();

    public String getIndentNumber() {
        return indentNumber;
    }

    public void setIndentNumber(String indentNumber) {
        this.indentNumber = indentNumber;
    }

    @Enumerated(EnumType.STRING)
    private IndentStatus indentStatus;
    private Long doid;
    private float includingTax;
    private Date created;
    private String comment;
    @Transient
    private state state;
    public Date getCreated() {
        return created;
    }
    @PrePersist
    public void setCreated() {
        this.created = new Date();
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "item_id"),name = "item_id",referencedColumnName = "itemId")
    @JsonIgnoreProperties({"itemRequest","issueItem","employe","vendorDate"})
    private StoreItemModel storeItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "emp_id"),name = "emp_id",referencedColumnName = "user_profile_id")
    @JsonIgnoreProperties({"indentData","itemRequest","itemModelSet","issueItemsData","userQualificationData","passwordEntity"
                               ,"hrModel","familyDetails","userExperienceData" })
    private User employee;
    @OneToMany(mappedBy = "indentPrice",cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {})
    private Set<VendorWisePriceModel> vendorWisePriceSet;

    @OneToMany(mappedBy = "indentDAta",cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"indentDAta","itemPoSet"})
    private Set<PoStore> personalOrder;
    @OneToMany(mappedBy = "indentItemQuantity",cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"indentItemQuantity"})
    private List<IndentQuantity> indentQuantityList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "issue_id"),name = "issue_id",referencedColumnName = "issueId")
    @JsonIgnoreProperties({"indents","itemRequest","storeItemModel","usageItems","emp"})
    private IssueItem issue;@ManyToMany(fetch = FetchType.LAZY)

    @JoinTable(schema = "store_management",name = "indent_vendor_detailsID",
            joinColumns = @JoinColumn(name = "indent_id"), inverseJoinColumns = @JoinColumn(name = "vendor_id"))
    @JsonIgnoreProperties({"contractModels","itemData","indentList"})
    private Set<VendorModel> dataVendorAndIndent=new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "ven_id"),name = "ven_id", referencedColumnName = "vendor_id")
    @JsonIgnoreProperties({"indentList","contractModels"})
    private VendorModel vendorData;

    public Indent ()
    {
        this.indentStatus=IndentStatus.GM;
    }
    public boolean Approoved()
    {
        this.state= state.accept;
        return false;
    }
    public void Cancel()
    {
            this.state= state.cancel;
    }
    public void Rejected()
    {
        this.state=state.reject;
    }
    enum  state{
        accept,cancel,reject
    }



}

package com.unitechApi.store.indent.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.AuditingAndResponse.Audit;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.po.Model.PoPrice;
import com.unitechApi.store.po.Model.PoStore;
import com.unitechApi.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "indent", schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Indent extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indentId;
    @NotNull(message = "Enter the estimated Price")
    @Column(nullable = false)
    private float estimatedPrice;
    private float total;
    private Long responseId;
    private String indentNumber = RandomStringUtils.randomNumeric(8);
    public String getIndentNumber() {
        return indentNumber;
    }

    @Enumerated(EnumType.STRING)
    private IndentStatus indentStatus;
    private Long doid;
    private float includingTax;
    @CreatedDate
    private LocalDateTime created;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "emp_id"), name = "emp_id", referencedColumnName = "user_profile_id")
    @JsonIgnoreProperties({"indentData", "itemRequest", "itemModelSet", "issueItemsData", "userQualificationData", "passwordEntity"
            , "hrModel", "familyDetails", "userExperienceData"})
    private User employee;
    @OneToMany(mappedBy = "indentPrice", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"indentPrice", "personalPrice"})
    private Set<VendorWisePriceModel> vendorWisePriceSet;

    @OneToMany(mappedBy = "indentDAta", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"indentDAta", "itemPoSet"})
    private Set<PoStore> personalOrder;
    @OneToMany(mappedBy = "indentDAtaPo", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"indentDAtaPo"})
    private Set<PoPrice> poPriceswithIndent;
    @OneToMany(mappedBy = "indentItemQuantity", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"indentItemQuantity", ""})
    private List<IndentQuantity> indentQuantityList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "issue_id"), name = "issue_id", referencedColumnName = "issueId")
    @JsonIgnoreProperties({"indents", "itemRequest", "storeItemModel", "usageItems", "emp"})
    private IssueItem issue;


    public Indent() {
        this.indentStatus = IndentStatus.GM;
    }

    public void addPriceSet(VendorWisePriceModel v) {
        this.vendorWisePriceSet.add(v);
        v.setIndentPrice(this);
    }



    @Override
    public String toString() {
        return "Indent{" +
                "indentId=" + indentId +
                ", indentNumber='" + indentNumber + '\'' +
                '}';
    }
}

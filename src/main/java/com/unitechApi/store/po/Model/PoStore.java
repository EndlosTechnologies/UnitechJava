package com.unitechApi.store.po.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.AuditingAndResponse.Audit;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "personal_order", schema = "store_management")
@SQLDelete(sql = "update store_management.personal_order SET deleteview = true where poid=?")
@Where(clause = "deleteview=false")

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PoStore extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poId;
    private String poNumber = RandomStringUtils.randomNumeric(8);
    private String poName;
    private Float amount;
    private float itemQuantity;
    @JsonIgnore
    private boolean deleteView = Boolean.FALSE;
    private String utrNumber;
    private String itemNAme;
    private Long vendorId;

    public PoStore() {

    }
    // @ManyToMany

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "ind_id"), name = "ind_id", referencedColumnName = "indentId")
    @JsonIgnoreProperties(value = {"personalOrder", "indentQuantityList", "issue", "dataVendorAndIndent"
            , "vendorData", "vendorWisePriceSet", "poPriceswithIndent"})
    private Indent indentDAta;
    @OneToMany(mappedBy = "poStoreData", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"poStoreData", "vendorWisePriceModel"})
    private Set<PoPrice> personalOrderPrice;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "store_management", name = "po_with_price_data"
            , joinColumns = @JoinColumn(name = "price_id")
            , inverseJoinColumns = @JoinColumn(name = "po_id"))
    private Set<VendorWisePriceModel> listOfpO = new HashSet<>();


    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public boolean isDeleteView() {
        return deleteView;
    }

    public void setDeleteView(boolean deleteView) {
        this.deleteView = deleteView;
    }

    public String getPoName() {
        return poName;
    }

    public void setPoName(String poName) {
        this.poName = poName;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getUtrNumber() {
        return utrNumber;
    }

    public void setUtrNumber(String utrNumber) {
        this.utrNumber = utrNumber;
    }

    public Indent getIndentDAta() {
        return indentDAta;
    }

    public void setIndentDAta(Indent indentDAta) {
        this.indentDAta = indentDAta;
    }

    public float getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(float itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public String toString() {
        return "PoStore{" +
                "poId=" + poId +
                ", poName='" + poName + '\'' +
                ", deleteView=" + deleteView +
                ", utr Number =" + utrNumber +
                ", Account   =" + amount +
                '}';
    }
}

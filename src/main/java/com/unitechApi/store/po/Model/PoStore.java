package com.unitechApi.store.po.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "personal_order",schema = "store_management")
@SQLDelete(sql = "update store_management.personal_order SET deleteview = true where poid=?")
@Where(clause = "deleteview=false")
public class PoStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poId;
    private String poNumber= String.valueOf(UUID.class);
    private String poName;
    private Float amount;
    @JsonIgnore
    private boolean deleteView=Boolean.FALSE;
    private String utrNumber;
    public PoStore() {

    }
   // @ManyToMany
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "store_management",name = "po_item_data"
            , joinColumns = @JoinColumn(name = "po_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    @JsonIgnoreProperties(value = {"personalOrder","itemRequest","employe","issueItem"})
    private Set<StoreItemModel> itemPoSet=new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "ind_id"), name = "ind_id", referencedColumnName = "indentId")
    @JsonIgnoreProperties(value = {"personalOrder"})
    private Indent indentDAta;

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

    public Set<StoreItemModel> getItemPoSet() {
        return itemPoSet;
    }

    public void setItemPoSet(Set<StoreItemModel> itemPoSet) {
        this.itemPoSet = itemPoSet;
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

    @Override
    public String toString() {
        return "PoStore{" +
                "poId=" + poId +
                ", poName='" + poName + '\'' +
                ", deleteView=" + deleteView +
                ", utr Number ="+utrNumber+
                ", Account   ="+amount+
                '}';
    }
}

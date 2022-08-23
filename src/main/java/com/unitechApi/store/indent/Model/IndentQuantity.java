package com.unitechApi.store.indent.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Indent_quantity",schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class IndentQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quantityId;
    private float inculdingTax;
    private float withoutTax;
    private float estimatedPrice;
    private float total;
    private float quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "item_id"),name = "item_id",referencedColumnName = "itemId")
    @JsonIgnoreProperties({"itemRequest","issueItem","employe","indentQuantities","vendorDate"})
    private StoreItemModel storeItemGetQuantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "indent_Id"),name = "indent_Id",referencedColumnName = "indentId")
    @JsonIgnoreProperties({"storeItem","quantities"})
    private Indent indentqua;

    public StoreItemModel getStoreItemGetQuantity() {
        return storeItemGetQuantity;
    }

    public void setStoreItemGetQuantity(StoreItemModel storeItemGetQuantity) {
        this.storeItemGetQuantity = storeItemGetQuantity;
    }

    public Indent getIndentqua() {
        return indentqua;
    }

    public void setIndentqua(Indent indentqua) {
        this.indentqua = indentqua;
    }

    public Long getQuantityId() {
        return quantityId;
    }

    public void setQuantityId(Long quantityId) {
        this.quantityId = quantityId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getInculdingTax() {
        return inculdingTax;
    }

    public void setInculdingTax(float inculdingTax) {
        this.inculdingTax = inculdingTax;
    }

    public float getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(float estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public float getWithoutTax() {
        return withoutTax;
    }

    public void setWithoutTax(float withoutTax) {
        this.withoutTax = withoutTax;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "IndentQuantity{" +
                "quantityId=" + quantityId +
                ", inculdingTax=" + inculdingTax +
                ", withoutTax=" + withoutTax +
                ", total=" + total +
                ", quantity=" + quantity +
                ", storeItemGetQuantity=" + storeItemGetQuantity +
                ", indentqua=" + indentqua +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndentQuantity that = (IndentQuantity) o;
        return Float.compare(that.inculdingTax, inculdingTax) == 0 && Float.compare(that.withoutTax, withoutTax) == 0 && Float.compare(that.total, total) == 0 && Float.compare(that.quantity, quantity) == 0 && Objects.equals(quantityId, that.quantityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantityId, inculdingTax, withoutTax, total, quantity );
    }


    public void saveQuantityUpdate(Indent indent) {
        this.indentqua=indent;
    }
}


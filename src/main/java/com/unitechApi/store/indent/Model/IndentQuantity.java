package com.unitechApi.store.indent.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.apache.catalina.Store;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    @JoinColumn(foreignKey = @ForeignKey(name = "item_id"), name = "item_id", referencedColumnName = "itemId")
    @JsonIgnoreProperties(value = {"storeIndentQuantity"})
    private StoreItemModel storeItemIndentQuantityData;

    public StoreItemModel getStoreItemIndentQuantityData() {
        return storeItemIndentQuantityData;
    }

    public void setStoreItemIndentQuantityData(StoreItemModel storeItemIndentQuantityData) {
        this.storeItemIndentQuantityData = storeItemIndentQuantityData;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "indent_id"), name = "indent_id", referencedColumnName = "indentId")
    @JsonIgnoreProperties(value = {"indentQuantityList"})
    private Indent indentItemQuantity;

    public Indent getIndentItemQuantity() {
        return indentItemQuantity;
    }

    public void setIndentItemQuantity(Indent indentItemQuantity) {
        this.indentItemQuantity = indentItemQuantity;
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
        return "IndentQuantity{ ->  item  id " + storeItemIndentQuantityData.getItemId() + "  "+
                "quantityId=" + quantityId +
                ", inculdingTax=" + inculdingTax +
                ", withoutTax=" + withoutTax +
                ", estimatedPrice=" + estimatedPrice +
                ", total=" + total +
                ", quantity=" + quantity +
                '}';
    }

    public void saveQuantityUpdate(Indent indent) {
        this.indentItemQuantity=indent;
    }
}


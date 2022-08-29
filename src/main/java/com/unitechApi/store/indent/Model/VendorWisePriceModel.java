package com.unitechApi.store.indent.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.vendor.model.VendorModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "price_data",schema = "store_management")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class VendorWisePriceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id", nullable = false)
    private Long price_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "item_id"),name = "item_id",referencedColumnName = "itemId")
    @JsonIgnoreProperties(value = {"vendorDate","employe",
            "itemRequest","storeIndentQuantity","vendorWisePriceDataWithItem","issueItem"})
    private StoreItemModel itemModelPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "vendor_id"),name = "vendor_id")
    @JsonIgnoreProperties(value = {"itemData","vendorWisePriceDAta","vendorAddressModels","indentList"})
    private VendorModel vendorModelData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "indnet_id"),name = "indent_id")
    @JsonIgnoreProperties(value = {"vendorWisePriceSet","indentList","employee","vendorData",
            "issue","indentQuantityList","dataVendorAndIndent","storeItem"})
    private Indent indentPrice;
    private double priceItem;

    public Long getPrice_id() {
        return price_id;
    }

    public void setPrice_id(Long price_id) {
        this.price_id = price_id;
    }

    @Override
    public String toString() {
        return "VendorWisePriceModel{" +
                "price_id=" + price_id +
                ", itemModelPrice=" + itemModelPrice +
                ", vendorModelData=" + vendorModelData +
                ", priceItem=" + priceItem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendorWisePriceModel that = (VendorWisePriceModel) o;
        return Double.compare(that.priceItem, priceItem) == 0 && Objects.equals(price_id, that.price_id) && Objects.equals(itemModelPrice, that.itemModelPrice) && Objects.equals(vendorModelData, that.vendorModelData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price_id, itemModelPrice, vendorModelData, priceItem);
    }
}

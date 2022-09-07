package com.unitechApi.store.po.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.po.PoEnum.PaymentEnum;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.vendor.model.VendorModel;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;

@Entity
@Table(name = "personal_order_price",schema = "store_management")
@Getter
@Setter
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PoPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "po_price_id", nullable = false)
    private Long po_price_id;
    @Enumerated(EnumType.STRING)
    private PaymentEnum paymentTypes;
    private float poPrice;
    private String poNumber= RandomStringUtils.randomNumeric(8);
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "ind_id"), name = "ind_id", referencedColumnName = "indentId")
    @JsonIgnoreProperties(value = {"personalOrder","vendorWisePriceSet","poPriceswithIndent"
                            ,"issue","vendorData","dataVendorAndIndent","indentQuantityList"})
    private Indent indentDAtaPo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "item_id"), name = "item_id", referencedColumnName = "itemId")
    @JsonIgnoreProperties(value = {"personalOrder","issueItem","personalOrderPrice","storeIndentQuantity"
                        ,"vendorWisePriceDataWithItem","vendorDate","employe"})
    private StoreItemModel itemPriceInPersonalOrder;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "vendor_id"),name = "vendor_id",referencedColumnName = "vendor_id")
    @JsonIgnoreProperties(value = {"poPriceSetData","vendorWisePriceDAta"})
    private VendorModel vendorModels;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "poId"),name = "poId",referencedColumnName = "poId")
    @JsonIgnoreProperties(value = {"personalOrderPrice"})
    private PoStore poStoreData;

    private float includingTax;
    private float withoutTax;
    private float itemQuantity;



    public Long getPo_price_id() {
        return po_price_id;
    }

    public void setPo_price_id(Long po_price_id) {
        this.po_price_id = po_price_id;
    }
}

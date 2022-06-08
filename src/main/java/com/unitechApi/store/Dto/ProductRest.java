package com.unitechApi.store.Dto;

import com.unitechApi.store.productCategory.model.ProductCategory;
import com.unitechApi.store.unit.model.Unit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRest {
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String drawingNo;
    private String catalogNo;
    private long frequency;
    private int RemainingItem;
    private int paytax;
    private Boolean activation;
    private int expiryDays;
    private long quantity;
    private ProductCategory productCategory;
    private Unit unit;
}

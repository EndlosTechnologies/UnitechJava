package com.unitechApi.store.productCategory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.apache.poi.ss.usermodel.CellType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_category",schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    @Column(name = "p_name",unique = true)
    private String productName;
    private Date created;

    public ProductCategory(CellType cellType) {
        cellType.ordinal();
    }

    public ProductCategory() {

    }

    public ProductCategory(long id) {
        id=getPid();
    }

    public Date getCreated() {
        return created;
    }
    @PrePersist
    public void setCreated() {
        this.created = new Date();
    }

    @OneToMany(mappedBy = "productCategory",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"productCategory","unit"})
    private List<StoreItemModel> item;

    public void addProductCateGory(StoreItemModel  productCategory)
    {
        this.item.add(productCategory);
    }
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<StoreItemModel> getItem() {
        return item;
    }

    public void setItem(List<StoreItemModel> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "pid=" + pid +
                ", productName='" + productName + '\'' +
                ", created=" + created +
                ", item=" + item +
                '}';
    }
}

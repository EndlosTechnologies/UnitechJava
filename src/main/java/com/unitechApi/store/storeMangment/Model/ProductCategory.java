package com.unitechApi.store.storeMangment.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    public Date getCreate() {
        return created;
    }
    @PrePersist
    public void setCreate() {
        this.created = new Date();
    }

    @OneToMany(mappedBy = "productCategory",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"productCategory","unit"})
    private List<StoreItemModel> item;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<StoreItemModel> getItem() {
        return item;
    }

    public void setItem(List<StoreItemModel> item) {
        this.item = item;
    }
}

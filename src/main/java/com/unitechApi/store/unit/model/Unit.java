package com.unitechApi.store.unit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "unit",schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    @Column(name = "u_name",unique = true)
    private String unitName;
    private Date created;

    public Date getCreated() {
        return created;
    }
    @PrePersist
    public void setCreated() {
        this.created = new Date();
    }

    @OneToMany(mappedBy = "unit",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"unit","productCategory"})
    private List<StoreItemModel> itemunit;


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<StoreItemModel> getItemunit() {
        return itemunit;
    }

    public void setItemunit(List<StoreItemModel> itemunit) {
        this.itemunit = itemunit;
    }
}

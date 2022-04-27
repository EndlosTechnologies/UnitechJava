package com.unitechApi.purchase.RawMaterial.item.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "item",schema = "purchaser")
public class Itemmodel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(name = "Item_id")
    private Long id;
    @Column(name ="itemcode" ,unique = true)
    private  String itemcode;
    @Column(name ="itemdesciption" ,unique = true)
    private String itemDescription;
    @Column(name ="station" ,unique = true)
    private  String station;


    @OneToMany(mappedBy = "itemdata",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("itemdata")
    private Set<ContractModel> contData;

    public Set<ContractModel> getContData() {
        return contData;
    }

    public void setContData(Set<ContractModel> contData) {
        this.contData = contData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }



}

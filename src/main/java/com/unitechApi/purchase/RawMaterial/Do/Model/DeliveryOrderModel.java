package com.unitechApi.purchase.RawMaterial.Do.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "DeliveryOrder",schema = "purchaser")
public class DeliveryOrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(name = "d_id")
    private Long id;
    @Column(name = "deliveryto")
    private String To;
    @Column(name = "trsportname")
    private String transportName;
    @Column(name = "truckno")
    private String truckNo;
    @Column(name = "rate")
    private int rate;
    @Column(name = "bales")
    private int  bales;





    @OneToMany(mappedBy = "deliveryOrderModel", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("deliveryOrderModel")
    private Set<ContractModel> contdata;

    public Set<ContractModel> getContdata() {
        return contdata;
    }

    public void setContdata(Set<ContractModel> contdata) {
        this.contdata = contdata;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getBales() {
        return bales;
    }

    public void setBales(int bales) {
        this.bales = bales;
    }


}

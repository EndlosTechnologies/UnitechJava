package com.unitechApi.purchase.RawMaterial.sampling.samplingModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sampleData",schema = "purchaser")
public class SampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sampling_id")
    private long id;

    @Column(name = "totaltrash")
    private  float trash;

    @Column(name = "fiberness")
    private  float fiberness;

    @Column(name = "mi")
    private  float mi;

    @Column(name = "uhml")
    private  float uhml;

    @Column(name = "ui")
    private float ui;

    @Column(name = "sfi")
    private  float sfi;
    @Column(name = "fibrestrf")
    private  float fibrestrf;
    @Column(name = "rd")
    private  float rd;
    @Column(name = "bplus")
    private  float bplus;
    @Column(name = "cg")
    private  float cg;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "sample_id"),name = "sample_id",referencedColumnName = "contract_id")
    @JsonIgnoreProperties("sampleEntities")
    private ContractModel contractModel;

    public ContractModel getContractModel() {
        return contractModel;
    }

    public void setContractModel(ContractModel contractModel) {
        this.contractModel = contractModel;
    }

    @PostPersist
    private void CreatedAt() {
        createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getTrash() {
        return trash;
    }

    public void setTrash(float trash) {
        this.trash = trash;
    }

    public float getFiberness() {
        return fiberness;
    }

    public void setFiberness(float fiberness) {
        this.fiberness = fiberness;
    }

    public float getMi() {
        return mi;
    }

    public void setMi(float mi) {
        this.mi = mi;
    }

    public float getUhml() {
        return uhml;
    }

    public void setUhml(float uhml) {
        this.uhml = uhml;
    }

    public float getUi() {
        return ui;
    }

    public void setUi(float ui) {
        this.ui = ui;
    }

    public float getSfi() {
        return sfi;
    }

    public void setSfi(float sfi) {
        this.sfi = sfi;
    }

    public float getFibrestrf() {
        return fibrestrf;
    }

    public void setFibrestrf(float fibrestrf) {
        this.fibrestrf = fibrestrf;
    }

    public float getRd() {
        return rd;
    }

    public void setRd(float rd) {
        this.rd = rd;
    }

    public float getBplus() {
        return bplus;
    }

    public void setBplus(float bplus) {
        this.bplus = bplus;
    }

    public float getCg() {
        return cg;
    }

    public void setCg(float cg) {
        this.cg = cg;
    }

    public void updateId(ContractModel contractModel) {
        this.contractModel=contractModel;
    }
}

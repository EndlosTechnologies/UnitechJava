package com.unitechApi.purchase.RawMaterial.Po.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;

import javax.persistence.*;

@Entity
@Table(name = "po_model",schema = "purchaser")
public class PoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(name = "po_id")
    private Long id;
    @Column(name = "ponumber",unique = true)
    private String poNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "personalorder_id"), name = "personalorder_id", referencedColumnName = "contract_id")
    @JsonIgnoreProperties("PoData")
    private ContractModel personalorder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public void UpdateId(ContractModel contractModel) {
    this.personalorder=contractModel;
    }
}

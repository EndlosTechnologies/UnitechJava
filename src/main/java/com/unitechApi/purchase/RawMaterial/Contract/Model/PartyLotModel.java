package com.unitechApi.purchase.RawMaterial.Contract.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "partyLot",schema = "purchaser")
public class PartyLotModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private Long id;

    @Column(name = "lotNumber",unique = true)
    private String partyNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "c_id"),name = "p_id",referencedColumnName = "contract_id")
    @JsonIgnoreProperties("partyLotModels")
    private ContractModel contract;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartyNumber() {
        return partyNumber;
    }

    public void setPartyNumber(String partyNumber) {
        this.partyNumber = partyNumber;
    }

    public ContractModel getContract() {
        return contract;
    }

    public void setContract(ContractModel contract) {
        this.contract = contract;
    }

    public void idUpdate(ContractModel contractModel) {
        this.contract=contractModel;
    }
}

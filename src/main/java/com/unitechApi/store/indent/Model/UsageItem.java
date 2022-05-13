package com.unitechApi.store.indent.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.addmachine.model.*;
import com.unitechApi.store.issue.model.IssueItem;

import javax.persistence.*;

@Entity
@Table(name = "usageItem",schema = "store_management")
public class UsageItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uId;
    private String deptName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "issued_id"),name = "issued_id",referencedColumnName = "issueId")
    @JsonIgnoreProperties("usageItems")
    private IssueItem issuedItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "b_id"),name = "b_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties({"bdata","bloowroomReading"})
    private AddBloowroom bloowusage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "c_id"),name = "c_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddCardingMachine cardingusage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "d_id"),name = "d_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddComber comberusage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "dr_id"),name = "dr_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddDrawFramesMachine drawFramesMachine;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "f_id"),name = "f_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddFinisherMachine finisherMachinedata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "l_id"),name = "l_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddLapFormer lapFormerusage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "p_id"),name = "p_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddPackingMachine packingMachineusage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "r_id"),name = "r_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddRingFramesMachine ringframeMachineusage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "s_id"),name = "s_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddSimplexMAchine simplexMachineusage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "u_id"),name = "u_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddUtillityMachine utilliyMachineusage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "w_id"),name = "w_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddWasteRoomeMAchine wasteMachineusage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "winding_id"),name = "windind_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddWindingMachine  windingMachineusage;

    public AddFinisherMachine getFinisherMachinedata() {
        return finisherMachinedata;
    }

    public void setFinisherMachinedata(AddFinisherMachine finisherMachinedata) {
        this.finisherMachinedata = finisherMachinedata;
    }

    public AddLapFormer getLapFormerusage() {
        return lapFormerusage;
    }

    public void setLapFormerusage(AddLapFormer lapFormerusage) {
        this.lapFormerusage = lapFormerusage;
    }

    public AddPackingMachine getPackingMachineusage() {
        return packingMachineusage;
    }

    public void setPackingMachineusage(AddPackingMachine packingMachineusage) {
        this.packingMachineusage = packingMachineusage;
    }

    public AddRingFramesMachine getRingframeMachineusage() {
        return ringframeMachineusage;
    }

    public void setRingframeMachineusage(AddRingFramesMachine ringframeMachineusage) {
        this.ringframeMachineusage = ringframeMachineusage;
    }

    public AddSimplexMAchine getSimplexMachineusage() {
        return simplexMachineusage;
    }

    public void setSimplexMachineusage(AddSimplexMAchine simplexMachineusage) {
        this.simplexMachineusage = simplexMachineusage;
    }

    public AddUtillityMachine getUtilliyMachineusage() {
        return utilliyMachineusage;
    }

    public void setUtilliyMachineusage(AddUtillityMachine utilliyMachineusage) {
        this.utilliyMachineusage = utilliyMachineusage;
    }

    public AddWasteRoomeMAchine getWasteMachineusage() {
        return wasteMachineusage;
    }

    public void setWasteMachineusage(AddWasteRoomeMAchine wasteMachineusage) {
        this.wasteMachineusage = wasteMachineusage;
    }

    public AddWindingMachine getWindingMachineusage() {
        return windingMachineusage;
    }

    public void setWindingMachineusage(AddWindingMachine windingMachineusage) {
        this.windingMachineusage = windingMachineusage;
    }

    public AddDrawFramesMachine getDrawFramesMachine() {
        return drawFramesMachine;
    }

    public void setDrawFramesMachine(AddDrawFramesMachine drawFramesMachine) {
        this.drawFramesMachine = drawFramesMachine;
    }

    public AddComber getComberusage() {
        return comberusage;
    }

    public void setComberusage(AddComber comberusage) {
        this.comberusage = comberusage;
    }

    public AddCardingMachine getCardingusage() {
        return cardingusage;
    }

    public void setCardingusage(AddCardingMachine cardingusage) {
        this.cardingusage = cardingusage;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public IssueItem getIssuedItem() {
        return issuedItem;
    }

    public void setIssuedItem(IssueItem issuedItem) {
        this.issuedItem = issuedItem;
    }

    public AddBloowroom getBloowusage() {
        return bloowusage;
    }

    public void setBloowusage(AddBloowroom bloowusage) {
        this.bloowusage = bloowusage;
    }
}

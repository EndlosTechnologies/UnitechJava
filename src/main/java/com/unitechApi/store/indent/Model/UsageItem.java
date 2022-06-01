package com.unitechApi.store.indent.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.unitechApi.addmachine.model.*;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usageItem",schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsageItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uId;
    private String deptName;
    private Date created;


    public Date getCreated() {
        return created;
    }
    @PrePersist
    public void setCreated() {
        this.created = new Date();
    }
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
    @JsonIgnoreProperties({"usageItems","cardingsreading"})
    private AddCardingMachine cardingusage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "d_id"),name = "d_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties({"usageItems","comberreading"})
    private AddComber comberusage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "dr_id"),name = "dr_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties({"drawframesperkgReading","drawFramesPerHanks","usageItems"})
    private AddDrawFramesMachine drawFramesMachine;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "f_id"),name = "f_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties({"usageItems","finisherperKgReading","finisherperhankMachineReading"})
    private AddFinisherMachine finisherMachinedata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "l_id"),name = "l_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties({"usageItems"})
    private AddLapFormer lapFormerusage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "p_id"),name = "p_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties("usageItems")
    private AddPackingMachine packingMachineusage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "r_id"),name = "r_id",referencedColumnName = "m_id")
    @JsonIgnoreProperties({"usageItems","ringframesReading"})
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

    @Override
    public String toString() {
        return "UsageItem{" +
                "uId=" + uId +
                ", deptName='" + deptName + '\'' +
                ", created=" + created +
                ", issuedItem=" + issuedItem +
                ", bloowusage=" + bloowusage +
                ", cardingusage=" + cardingusage +
                ", comberusage=" + comberusage +
                ", drawFramesMachine=" + drawFramesMachine +
                ", finisherMachinedata=" + finisherMachinedata +
                ", lapFormerusage=" + lapFormerusage +
                ", packingMachineusage=" + packingMachineusage +
                ", ringframeMachineusage=" + ringframeMachineusage +
                ", simplexMachineusage=" + simplexMachineusage +
                ", utilliyMachineusage=" + utilliyMachineusage +
                ", wasteMachineusage=" + wasteMachineusage +
                ", windingMachineusage=" + windingMachineusage +
                '}';
    }
}

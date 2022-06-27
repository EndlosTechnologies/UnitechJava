package com.unitechApi.purchase.RawMaterial.Contract.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.purchase.RawMaterial.Do.Model.DeliveryOrderModel;
import com.unitechApi.purchase.RawMaterial.Po.Model.PoModel;
import com.unitechApi.purchase.RawMaterial.item.model.Itemmodel;
import com.unitechApi.purchase.RawMaterial.sampling.samplingModel.SampleEntity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "contract_detail", schema = "purchaser")
public class ContractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "contract_id")
    private Long id;
    @Column(name = "PartyplotNumber")
    private Long partyplotNumber;
    @Column(name = "contract_no")
    private Long contractNo;
    @Column(name = "invoiceNo")
    private String invoiceno;
    @Column(name = "originname")
    private String originname;
    @Column(name = "postOfDischarged")
    private String portOfDischarged;
    @Column(name = "blNo")
    private String blNo;
    @Column(name = "containerNo")
    private String containerNo;
    @Column(name = "station")
    private String station;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "uom")
    private Long vom;
    @Column(name = "press_running_no")
    private Long press_running_no;
    @Column(name = "gst_no")
    private String gstno;
    @Column(name = "pan_no")
    private String panno;
    @Column(name = "trash")
    private String trash;
    @Column(name = "mic")
    private String mic;
    @Column(name = "moisture")
    private String moisture;
    @Column(name = "broker")
    private String broker;
    @Column(name = "length")
    private String length;
    @Column(name = "status")
    private boolean status;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @OneToMany(mappedBy = "personalorder" ,cascade = CascadeType.ALL)
    @JsonIgnoreProperties("personalorder")
    private Set<PoModel> PoData;

    @OneToMany(mappedBy ="contract",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("contract")
    private Set<PartyLotModel> partyLotModels;

    public Set<PartyLotModel> getPartyLotModels() {
        return partyLotModels;
    }

    public void setPartyLotModels(Set<PartyLotModel> partyLotModels) {
        this.partyLotModels = partyLotModels;
    }

    @OneToMany(mappedBy = "contractModel",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("contractModel")
    private Set<SampleEntity> sampleEntities;

    public Set<SampleEntity> getSampleEntities() {
        return sampleEntities;
    }

    public void setSampleEntities(Set<SampleEntity> sampleEntities) {
        this.sampleEntities = sampleEntities;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "i_id"), name = "i_id", referencedColumnName = "item_id")
    @JsonIgnoreProperties("contData")
    private Itemmodel itemdata;






    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "delivery_id"), name = "delivery_id", referencedColumnName = "d_id")
    @JsonIgnoreProperties("contdata")
    private DeliveryOrderModel deliveryOrderModel;

    public Itemmodel getItemdata() {
        return itemdata;
    }

    public void setItemdata(Itemmodel itemdata) {
        this.itemdata = itemdata;
    }

    public DeliveryOrderModel getDeliveryOrderModel() {
        return deliveryOrderModel;
    }

    public void setDeliveryOrderModel(DeliveryOrderModel deliveryOrderModel) {
        this.deliveryOrderModel = deliveryOrderModel;
    }

    @PrePersist
    private void CreatedAt() {
        createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getOriginname() {
        return originname;
    }

    public void setOriginname(String originname) {
        this.originname = originname;
    }

    public String getPortOfDischarged() {
        return portOfDischarged;
    }

    public void setPortOfDischarged(String portOfDischarged) {
        this.portOfDischarged = portOfDischarged;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getTrash() {
        return trash;
    }

    public void setTrash(String trash) {
        this.trash = trash;
    }

    public String getMic() {
        return mic;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }





    public Set<PoModel> getPoData() {
        return PoData;
    }

    public void setPoData(Set<PoModel> poData) {
        PoData = poData;
    }



    public Long getPartyplotNumber() {
        return partyplotNumber;
    }

    public void setPartyplotNumber(Long partyplotNumber) {
        this.partyplotNumber = partyplotNumber;
    }

    public Long getContractNo() {
        return contractNo;
    }

    public void setContractNo(Long contractNo) {
        this.contractNo = contractNo;
    }


    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getVom() {
        return vom;
    }

    public void setVom(Long vom) {
        this.vom = vom;
    }

    public Long getPress_running_no() {
        return press_running_no;
    }

    public void setPress_running_no(Long press_running_no) {
        this.press_running_no = press_running_no;
    }

    public String getGstno() {
        return gstno;
    }

    public void setGstno(String gstno) {
        this.gstno = gstno;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }




    public void updateiddata(Itemmodel itemmodel) {
        this.itemdata=itemmodel;
    }

    public void updatedeliveryid(DeliveryOrderModel deliveryOrderModel) {
        this.deliveryOrderModel=deliveryOrderModel;
    }
}

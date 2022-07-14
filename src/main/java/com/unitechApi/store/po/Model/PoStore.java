package com.unitechApi.store.po.Model;

import javax.persistence.*;

@Entity
@Table(name = "personal_order",schema = "store_management")
public class PoStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poId;
    private String poName;
    private Integer amount;
    private boolean deleteView;
    private String utrNumber;
    public PoStore() {
        this.deleteView = false;
    }



    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public boolean isDeleteView() {
        return deleteView;
    }

    public void setDeleteView(boolean deleteView) {
        this.deleteView = deleteView;
    }

    public String getPoName() {
        return poName;
    }

    public void setPoName(String poName) {
        this.poName = poName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUtrNumber() {
        return utrNumber;
    }

    public void setUtrNumber(String utrNumber) {
        this.utrNumber = utrNumber;
    }

    @Override
    public String toString() {
        return "PoStore{" +
                "poId=" + poId +
                ", poName='" + poName + '\'' +
                ", deleteView=" + deleteView +
                ", utr Number ="+utrNumber+
                ", Account   ="+amount+
                '}';
    }
}

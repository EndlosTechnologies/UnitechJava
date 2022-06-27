package com.unitechApi.store.po.Model;

import javax.persistence.*;

@Entity
@Table(name = "personal_order",schema = "store_management")
public class PoStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poId;

    private boolean deleteView;

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
}

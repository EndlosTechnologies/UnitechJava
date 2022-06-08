package com.unitechApi.store.storeMangment.repository;

import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface StoreItemRepository extends JpaRepository<StoreItemModel,Long> {
    List<StoreItemModel> findByItemName(String name);
    List<StoreItemModel> findByCatalogNo(String name);
    List<StoreItemModel> findByDrawingNo(String name);
    List<StoreItemModel> findByCreated(Date date);

}

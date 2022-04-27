package com.unitechApi.purchase.RawMaterial.item.repository;


import com.unitechApi.purchase.RawMaterial.item.model.Itemmodel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Itemmodel , Long> {
}

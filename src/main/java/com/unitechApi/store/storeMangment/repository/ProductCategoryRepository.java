package com.unitechApi.store.storeMangment.repository;

import com.unitechApi.store.storeMangment.Model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
    List<ProductCategory>  findByProductName(String product);

    List<ProductCategory> findByCreated(Date data);
}

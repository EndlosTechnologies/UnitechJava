package com.unitechApi.store.productCategory.repository;

import com.unitechApi.store.productCategory.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>, JpaSpecificationExecutor<ProductCategory> {
    List<ProductCategory> findByProductName(String product);

    List<ProductCategory> findByCreated(Date data);

    Boolean existsByProductName(String productName);
}

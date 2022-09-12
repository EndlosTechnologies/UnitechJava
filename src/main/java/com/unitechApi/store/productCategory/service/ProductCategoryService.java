package com.unitechApi.store.productCategory.service;

import com.unitechApi.common.query.SearchRequest;
import com.unitechApi.common.query.SearchSpecification;
import com.unitechApi.exception.ExceptionService.ProductCategoryNotFound;
import com.unitechApi.store.productCategory.model.ProductCategory;
import com.unitechApi.store.productCategory.repository.ProductCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory saveData(ProductCategory productCategory) {

        return productCategoryRepository.save(productCategory);
    }

    public ProductCategory findById(Long id) {
        return productCategoryRepository
                .findById(id)
                .orElseThrow(() -> new ProductCategoryNotFound("Product Not Found :" + id));
    }

    public Object deleteCategory(Long id) {
        if (findById(id).equals(id)) {
            productCategoryRepository.deleteById(id);
        }
        return Optional.empty();
    }

    public List<ProductCategory> findAllProductCategory() {
        return productCategoryRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(ProductCategory::getPid))
                .collect(Collectors.toList());
    }

    public List<ProductCategory> FindCreateDate(Date data) {
        return productCategoryRepository.findByCreated(data);
    }

    public List<ProductCategory> findByProductName(String product) {
        return productCategoryRepository.findByProductName(product);
    }

    public Page<ProductCategory> searchingInProductCategory(SearchRequest searchRequest) {
        SearchSpecification<ProductCategory> data = new SearchSpecification<>(searchRequest);
        Pageable pageable = SearchSpecification.getPageable(searchRequest.getPage(), searchRequest.getSize());
        return productCategoryRepository.findAll(data, pageable);
    }


}

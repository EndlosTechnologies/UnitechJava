package com.unitechApi.store.productCategory.controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.productCategory.model.ProductCategory;
import com.unitechApi.store.productCategory.service.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/pCategory")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
    @PostMapping
    public ResponseEntity<?>  saveProductCategoryData(@RequestBody ProductCategory productCategory)
    {
        ProductCategory data=productCategoryService.saveData(productCategory);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.CREATED);
    }
    @GetMapping(value = "/{pId}")
    public ResponseEntity<?> getDataProductCategory(@PathVariable Long pId)
    {
        ProductCategory data=productCategoryService.findById(pId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<?> getAllProductCategory()
    {
        List<ProductCategory> data=productCategoryService.findAllProductCategory();
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @DeleteMapping(value = "/{pId}")
    public ResponseEntity<?> deleteProductCategory(@PathVariable Long pId)
    {
        Object data=productCategoryService.deleteCategory(pId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/product")
    public ResponseEntity<?> FindByProductName(@RequestParam String product)
    {
        Object data=productCategoryService.findByProductName(product);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping(value = "/date")
    public ResponseEntity<?> findByDate(@RequestParam Date date)
    {
        Object data=productCategoryService.FindCretaeDate(date);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }

}

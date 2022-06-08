package com.unitechApi.store.storeMangment.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.exception.ExceptionService.ItemNotFound;
import com.unitechApi.exception.ExceptionService.ProductCategoryNotFound;
import com.unitechApi.exception.ExceptionService.UnitNotFound;
import com.unitechApi.store.productCategory.model.ProductCategory;
import com.unitechApi.store.productCategory.repository.ProductCategoryRepository;
import com.unitechApi.store.storeMangment.ExcelService.ItemExcel;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import com.unitechApi.store.storeMangment.service.StoreItemService;
import com.unitechApi.store.unit.model.Unit;
import com.unitechApi.store.unit.repository.UnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/store")
public class StoreItemController {
    public static final Logger log = LoggerFactory.getLogger(StoreItemController.class);
    private final StoreItemService storeItemService;
    private final StoreItemRepository storeItemRepository;
    private final UnitRepository unitRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public StoreItemController(StoreItemService storeitemService, StoreItemRepository storeItemRepository, UnitRepository unitRepository, ProductCategoryRepository productCategoryRepository) {
        this.storeItemService = storeitemService;
        this.storeItemRepository = storeItemRepository;
        this.unitRepository = unitRepository;

        this.productCategoryRepository = productCategoryRepository;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> saveItemData(@RequestBody StoreItemModel storeItemModel) {
        StoreItemModel itemData = storeItemService.saveData(storeItemModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(itemData), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<StoreItemModel> data = storeItemService.findAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findByid(@PathVariable Long id) {
        StoreItemModel storeItemModel = storeItemService.findById(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(storeItemModel), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateData(@PathVariable long id, @RequestBody Map<Object, Object> storeItemModel) {
        Object s = storeItemService.updateItem(id, storeItemModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(s), HttpStatus.OK);
    }

    @GetMapping(value = "/itemName/")
    public ResponseEntity<?> findByItemName(@RequestParam String name) {
        List<StoreItemModel> data = storeItemService.findByItemName(name);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/cNo/")
    public ResponseEntity<?> findByCatalogNo(@RequestParam String name) {
        List<StoreItemModel> data = storeItemService.findByCno(name);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/dNo/")
    public ResponseEntity<?> findByDrawingNo(@RequestParam String name) {
        List<StoreItemModel> data = storeItemService.findByDno(name);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/sDate/")
    public ResponseEntity<?> findBysDate(@RequestParam Date date) {
        List<StoreItemModel> data = storeItemService.findByCreatedDate(date);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @PatchMapping("update/{uid}/{quantity}")
    public ResponseEntity<?> updateStock(@PathVariable long uid, @PathVariable Long quantity) {
        storeItemService.AddStock(uid, quantity);
        return new ResponseEntity<>(new MessageResponse("added Stock " + quantity), HttpStatus.OK);
    }

    @GetMapping(value = "checkRemainItem/{itemId}")
    public ResponseEntity<?> checkRemainingItem(@PathVariable Long itemId) {
        storeItemService.checkRemainingItem(itemId);
        return new ResponseEntity<>(new MessageResponse("checked done "), HttpStatus.OK);
    }
    @RequestMapping(
            value = "up/{id}",
            method = {
                    RequestMethod.PATCH
            })
    public ResponseEntity<?> updateStore(@PathVariable Long id, @RequestBody StoreItemModel storeItemModel) {
        StoreItemModel storeItem = storeItemRepository.findById(id).orElseThrow(()-> new ItemNotFound("item Not Found"));
        if (storeItem == null) {
            throw new ItemNotFound("Item Not Found ");
        }
        storeItem.setItemName(storeItemModel.getItemName());
        storeItem.setItemDescription(storeItemModel.getItemDescription());
        storeItem.setDrawingNo(storeItemModel.getDrawingNo());
        storeItem.setCatalogNo(storeItemModel.getCatalogNo());
        storeItem.setFrequency(storeItemModel.getFrequency());
        log.info("frequency ,{}", storeItem.getFrequency());
        storeItem.setPaytax(storeItemModel.getPaytax());
        storeItem.setActivation(storeItemModel.getActivation());
        storeItem.setExpiryDays(storeItemModel.getExpiryDays());
        storeItem.setQuantity(storeItemModel.getQuantity());
        if (storeItemModel.getProductCategory() != null) {
            ProductCategory productCategory = productCategoryRepository.findById(storeItemModel.getProductCategory().getPid()).orElseThrow(()->new ProductCategoryNotFound("product category Not found"));
            productCategory.getItem().add(storeItem);
            //  log.info("product added in {} ", productCategory);
            storeItem.setProductCategory(productCategory);
            productCategoryRepository.save(productCategory);
        }
        if (storeItemModel.getUnit() != null) {
            Unit unit = unitRepository.findById(storeItemModel.getUnit().getUid()).orElseThrow(()->new UnitNotFound("product unit Not found"));
            unit.getItemunit().add(storeItem);
            storeItem.setUnit(unit);
            unitRepository.save(unit);
        } else {
            storeItem.setProductCategory(storeItem.getProductCategory());
            storeItem.setUnit(storeItem.getUnit());
        }
        storeItem = storeItemRepository.save(storeItem);
        log.info("save in item data {}", storeItem);
        return new ResponseEntity<>(PageResponse.SuccessResponse(storeItem), HttpStatus.OK);
    }


    @DeleteMapping(value = "{item_id}/rmVendor/{vendor_id}")
    public ResponseEntity<?> deleteVendor(@PathVariable Long item_id, @PathVariable Long vendor_id) {
        storeItemService.deleteVendor(item_id, vendor_id);
        return new ResponseEntity<>(new MessageResponse("Deleted Successfully"), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/ex/d")
    public void downloadFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=itemList.xlsx";
        response.setHeader(headerKey, headerValue);
        List<StoreItemModel> data = storeItemService.findAll();
        data.forEach(System.out::println);
        ItemExcel itemExcel = new ItemExcel(data);
        itemExcel.export(response);
    }
}

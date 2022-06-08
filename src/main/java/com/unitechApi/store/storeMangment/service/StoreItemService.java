package com.unitechApi.store.storeMangment.service;

import com.unitechApi.exception.ExceptionService.AddItemException;
import com.unitechApi.exception.ExceptionService.ItemNotFound;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.purchase.RawMaterial.vendor.Repository.VendorRepository;
import com.unitechApi.purchase.RawMaterial.vendor.model.VendorModel;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StoreItemService {
    private final StoreItemRepository storeItemRepository;
    private final VendorRepository vendorRepository;
    private static final Logger log = LoggerFactory.getLogger(StoreItemService.class);

    public StoreItemService(StoreItemRepository storeItemRepository, VendorRepository vendorRepository) {
        this.storeItemRepository = storeItemRepository;
        this.vendorRepository = vendorRepository;
    }

    public StoreItemModel saveData(StoreItemModel storeItemModel) {
        return storeItemRepository.save(storeItemModel);
    }

    public void checkRemainingItem(Long id)
    {
        StoreItemModel storeItemModel = storeItemRepository.findById(id).orElseThrow(() -> new ItemNotFound("item Not Found"));
        if (storeItemModel.getQuantity()<storeItemModel.getRemainingItem())
        {
            throw new AddItemException("Sorry You Can't take item in Store ! Please add Item in store ");
        }
    }
    public StoreItemModel findById(Long id) {
        return storeItemRepository.findById(id).orElseThrow(() -> new ItemNotFound("item Not Found"));
    }

    public StoreItemModel updateItem(Long id, Map<Object, Object> itemData) {
        StoreItemModel itemId = storeItemRepository.findById(id).orElseThrow(() -> new ItemNotFound("item Not Found"));
        itemData.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(StoreItemModel.class, (String) key);
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, itemId, value);

        });

        return storeItemRepository.save(itemId);
    }

    public List<StoreItemModel> findAll() {
        return storeItemRepository.findAll();
    }

    public List<StoreItemModel> findByItemName(String name) {
        return storeItemRepository.findByItemName(name);
    }

    public List<StoreItemModel> findByDno(String name) {
        return storeItemRepository.findByDrawingNo(name);
    }

    public List<StoreItemModel> findByCno(String name) {
        return storeItemRepository.findByCatalogNo(name);
    }

    public List<StoreItemModel> findByCreatedDate(Date date) {
        return storeItemRepository.findByCreated(date);
    }

    public void AddStock(Long id, Long quantity) {

        StoreItemModel s = findById(id);
        long currentQuantity = s.getQuantity();
        long newQuantity = currentQuantity + quantity;
        log.info("new item {}", newQuantity);
        s.setQuantity(newQuantity);
        storeItemRepository.save(s);
    }

    public void deleteVendor(long item_id, long vendor_id)
    {
        StoreItemModel storeItemModel = storeItemRepository.findById(item_id).orElseThrow(() -> new ItemNotFound("item Not Found"));
        VendorModel vendorModel=vendorRepository.findById(vendor_id).orElseThrow(() -> new ResourceNotFound("Vendor Not Found"));

        storeItemModel.deleteVendor(vendorModel);
        storeItemRepository.save(storeItemModel);
    }

}


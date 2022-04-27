package com.unitechApi.purchase.RawMaterial.item.controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.purchase.RawMaterial.item.model.Itemmodel;
import com.unitechApi.purchase.RawMaterial.item.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/purchase/contract/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> SaveData(@RequestBody Itemmodel itemmodel) {
        Itemmodel item = itemService.SaveData(itemmodel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(item), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{ItemId}")
    public ResponseEntity<?> FindByIdData(@PathVariable Long ItemId) {
        Itemmodel itemmodel = itemService.FindById(ItemId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(itemmodel), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{itemId}")
    public ResponseEntity<?> DeleteData(@PathVariable Long itemId) {
        return new ResponseEntity<>(itemService.DeleteById(itemId), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> FindAll() {
        Object itemModel = itemService.FindAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(itemModel), HttpStatus.OK);
    }
//    @PutMapping("/{iId}/update/{cId}")
//    public ResponseEntity<?> UpdateId(@PathVariable Long iId, @PathVariable Long cId) {
//        Itemmodel itemmodel  = itemService.UpdateId(iId,cId);
//        return new ResponseEntity<>(PageResponse.SuccessResponse(itemmodel), HttpStatus.OK);
//    }
}

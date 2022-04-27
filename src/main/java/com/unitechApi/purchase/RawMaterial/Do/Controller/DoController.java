package com.unitechApi.purchase.RawMaterial.Do.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.purchase.RawMaterial.Do.Model.DeliveryOrderModel;
import com.unitechApi.purchase.RawMaterial.Do.Service.DoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/unitech/api/v1/purchase/do")
public class DoController {

    private final DoService doService;

    public DoController(DoService doService) {
        this.doService = doService;
    }


    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> SaveData(@RequestBody DeliveryOrderModel deliveryOrderModel) {
        DeliveryOrderModel dModel = doService.SaveData(deliveryOrderModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(dModel), HttpStatus.CREATED);

    }

    @GetMapping("/{doId}")
    public ResponseEntity<?> FindById(@PathVariable Long doId) {
        DeliveryOrderModel deliveryOrderModel = doService.GetOrderByid(doId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(deliveryOrderModel), HttpStatus.OK);
    }

    @DeleteMapping("/{DoId}")
    public ResponseEntity<?> DeleteData(@PathVariable Long DoId) {
        return new ResponseEntity<>(doService.DeleteData(DoId), HttpStatus.NO_CONTENT);
    }


    @PatchMapping("/{DoId}")
    public ResponseEntity<?> UpdateDoData(@PathVariable Long DoId, @RequestBody Map<String, Object> changes) {
        return new ResponseEntity<>(doService.updateData(DoId, changes), HttpStatus.OK);
    }


}
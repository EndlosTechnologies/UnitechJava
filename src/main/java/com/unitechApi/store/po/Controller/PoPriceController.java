package com.unitechApi.store.po.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.purchase.RawMaterial.Contract.Service.ContractService;
import com.unitechApi.store.po.Model.PoPrice;
import com.unitechApi.store.po.Service.PoPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/unitech/api/v1/store/po/price")
public class PoPriceController {
    private final PoPriceService priceService;


    public PoPriceController(PoPriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    public ResponseEntity<?> saveData(@RequestBody PoPrice poPrice)
    {
        PoPrice savePoPrice=priceService.saveData(poPrice);
        return new ResponseEntity<>(PageResponse.SuccessResponse(savePoPrice), HttpStatus.CREATED);
    }

}

package com.unitechApi.store.indent.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.indent.Service.ServiceImpl.PriceServiceImpl;
import org.apache.poi.util.LocaleID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/store/price")
public class PriceController {
    private final PriceServiceImpl priceServiceImpl;

    public PriceController(PriceServiceImpl priceServiceImpl) {
        this.priceServiceImpl = priceServiceImpl;
    }

    @GetMapping(value = "/{pId}")
    public ResponseEntity<?> getById(@PathVariable Long pId) {
        return new ResponseEntity<>(priceServiceImpl.findById(pId), HttpStatus.OK);
    }

    @GetMapping(value = "/indent/{indentId}")
    public ResponseEntity<?> getByIndentId(@PathVariable Long indentId) {
        List<?> data=priceServiceImpl.findByIndentId(indentId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> saveData(@RequestBody VendorWisePriceModel vendorWisePriceModel) {
        return new ResponseEntity<>(priceServiceImpl.saveData(vendorWisePriceModel), HttpStatus.OK);
    }
}

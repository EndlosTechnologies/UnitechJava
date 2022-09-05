package com.unitechApi.store.indent.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.indent.Service.ServiceImpl.PriceServiceImpl;
import com.unitechApi.store.indent.view.ViewByDistinct;
import org.apache.poi.util.LocaleID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/store/price")
public class PriceController {
    private final PriceServiceImpl priceServiceImpl;
    public static final Logger log= LoggerFactory.getLogger(PriceController.class);
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
    @GetMapping(value = "/price/{ven_id}")
    public ResponseEntity<?> getByCountWithVenId(@PathVariable Long ven_id) {
        List<?> data=priceServiceImpl.countDistinctByVendorModelDataId(ven_id);
        log.info("data {}",data.size());
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }
    @GetMapping(value = "/item/{ite_id}")
    public ResponseEntity<?> getByCountWithItemId(@PathVariable Long ite_id) {
        List<?> data=priceServiceImpl.getDistinctByItemId(ite_id);
        log.info("data {}",data.size());
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> saveData(@RequestBody VendorWisePriceModel vendorWisePriceModel) {
        VendorWisePriceModel data=priceServiceImpl.saveData(vendorWisePriceModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.CREATED);
    }
}

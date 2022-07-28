package com.unitechApi.store.indent.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.indent.Model.IndentQuantity;
import com.unitechApi.store.indent.Service.QuantityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequestMapping(value = "/unitech/api/v1/quantity")
public class QuantityController {
    private final QuantityService quantityService;

    public QuantityController(QuantityService quantityService) {
        this.quantityService = quantityService;
    }
    @PostMapping
    public ResponseEntity<?> saveData(@RequestBody IndentQuantity indentQuantity)
    {
        Object data=quantityService.saveData(indentQuantity);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.CREATED);
    }
    @PostMapping(value = "/saveAll")
    public ResponseEntity<?> saveAllData(@RequestBody List<IndentQuantity> indentQuantities)
    {
        quantityService.saveAllData(indentQuantities);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{indentId}")
    public ResponseEntity<?> findById(@PathVariable Long indentId)
    {
        Object data=quantityService.findById(indentId);
        return  new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping(value = "/AllIndent/{indentId}")
    public ResponseEntity<?> findAllByIndentId(@PathVariable Long indentId)
    {
        Object data=quantityService.dataGetByIndentId(indentId);
        return  new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
}

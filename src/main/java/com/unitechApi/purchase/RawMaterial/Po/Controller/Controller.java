package com.unitechApi.purchase.RawMaterial.Po.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.purchase.RawMaterial.Po.Model.PoModel;
import com.unitechApi.purchase.RawMaterial.Po.service.PoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unitech/api/v1/purchase/contract/po")
public class Controller {
    // Po Means Personal Order
    private final PoService poService;

    public Controller(PoService poService) {
        this.poService = poService;
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> SaveData(@RequestBody PoModel poModel) {
        PoModel poData = poService.SaveData(poModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(poData), HttpStatus.CREATED);
    }

    @GetMapping("/{PoId}")
    public ResponseEntity<?> FindById(@PathVariable Long PoId) {
        PoModel poData = poService.FindById(PoId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(poData), HttpStatus.OK);
    }

    @PutMapping("/{pId}/update/{cId}")
    public ResponseEntity<?> UpdateId(@PathVariable Long pId, @PathVariable Long cId) {
        PoModel poModel = poService.UpdateId(pId, cId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(poModel), HttpStatus.OK);
    }

    @DeleteMapping("/{PoId}")
    public ResponseEntity<?> DeleteById(@PathVariable Long PoId) {
        Object PoData = poService.DeleteById(PoId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(PoData), HttpStatus.NO_CONTENT);
    }
}

package com.unitechApi.purchase.RawMaterial.Contract.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.purchase.RawMaterial.Contract.Model.PartyLotModel;
import com.unitechApi.purchase.RawMaterial.Contract.Service.PartyLotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/purchase/party")
public class PartyLotController {
    private final PartyLotService partyLotService;

    public PartyLotController(PartyLotService partyLotService) {
        this.partyLotService = partyLotService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> SaveData(@RequestBody PartyLotModel partyLotModel) {
        PartyLotModel partyLot = partyLotService.saveData(partyLotModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(partyLot), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{partyId}")
    public ResponseEntity<?> FIndById(@PathVariable Long partyId) {
        PartyLotModel partyLot = partyLotService.FindById(partyId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(partyLot), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{partyId}")
    public ResponseEntity<?> FIndByDeleteData(@PathVariable Long partyId) {
        Optional partyLot = partyLotService.Delete(partyId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(partyLot), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> FIndByAll() {
        Object partyLot = partyLotService.FindAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(partyLot), HttpStatus.OK);
    }

    @PutMapping(value = "/{c_id}/update/{p_id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long c_id, @PathVariable Long p_id) {
        partyLotService.UpdateId(c_id, p_id);
        return new ResponseEntity<>("updtae Succeesfuully", HttpStatus.OK);
    }
}

package com.unitechApi.store.unit.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.unit.model.Unit;
import com.unitechApi.store.unit.repository.UnitRepository;
import com.unitechApi.store.unit.service.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/unit")
public class UnitController {
    private final UnitService unitService;
    private final UnitRepository unitRepository;


    public UnitController(UnitService unitService, UnitRepository unitRepository) {
        this.unitService = unitService;
        this.unitRepository = unitRepository;
    }

    @PostMapping
    public ResponseEntity<?> saveUnit(@RequestBody Unit unit) {
        if (unitRepository.existsByUnitName(unit.getUnitName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Already Exists " + unit.getUnitName()));
        }
        Unit data = unitService.saveData(unit);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable Long id) {
        Object data = unitService.findById(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllUnit() {
        List<Unit> data = unitService.findAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {
        Object data = unitService.deleteData(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/unit")
    public ResponseEntity<?> FindByProductName(@RequestParam String unit) {
        Object data = unitService.findByUnitName(unit);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/date")
    public ResponseEntity<?> findByDate(@RequestParam Date date) {
        Object data = unitService.findByDate(date);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }
}

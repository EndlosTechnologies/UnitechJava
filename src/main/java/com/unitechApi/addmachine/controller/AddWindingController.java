package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddWindingMachine;
import com.unitechApi.addmachine.repositroy.AddWindingRepository;
import com.unitechApi.addmachine.service.AddWindingService;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/addmachine/winding")
@Slf4j
public class AddWindingController {

    private final AddWindingService addWindingService;
    private final AddWindingRepository addWindingRepository;

    public AddWindingController(AddWindingService addWindingService, AddWindingRepository addWindingRepository) {
        this.addWindingService = addWindingService;
        this.addWindingRepository = addWindingRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<?> SaveBlowRoom(@RequestBody AddWindingMachine addWindingMachine) {
        if (addWindingRepository.existsByName(addWindingMachine.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Already Exists " + addWindingMachine.getName()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(addWindingService.SaveData(addWindingMachine));
    }

    @GetMapping("/{id}")
    public Optional<AddWindingMachine> FindByidBlowRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addWindingService.FindByData(id)).getBody();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addWindingService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        addWindingService.DeleteReading(id);
    }

    @GetMapping("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status) {
        List<AddWindingMachine> Data = addWindingService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddWindingMachine> addWindingMachine = Optional.ofNullable(addWindingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (addWindingMachine.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddWindingMachine.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addWindingMachine.get(), value);
            });
             addWindingRepository.save(addWindingMachine.get());
        } else {
            throw new ResourceNotFound("Resource Not Found " + id);
        }
        log.info(" Status Updated {} ", fields);
        return new ResponseEntity<>(new MessageResponse("Updated :->" + new ArrayList<>(fields.entrySet())), HttpStatus.OK);
    }
}

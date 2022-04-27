package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddCardingMachine;
import com.unitechApi.addmachine.repositroy.AddCardingRepository;
import com.unitechApi.addmachine.service.AddCardingService;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/addmachine/carding")
@Slf4j
public class AddCardingController {

    private final AddCardingService addCardingService;
    private final AddCardingRepository addCardingRepository;

    public AddCardingController(AddCardingService addCardingService, AddCardingRepository addCardingRepository) {
        this.addCardingService = addCardingService;
        this.addCardingRepository = addCardingRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<AddCardingMachine> SaveBlowRoom(@RequestBody AddCardingMachine addCardingMachine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addCardingService.Savecarding(addCardingMachine));
    }

    @GetMapping("/{id}")
    public Optional<AddCardingMachine> FindByidBlowRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addCardingService.FindByData(id)).getBody();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addCardingService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        addCardingService.DeleteReading(id);
    }

    @GetMapping("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status) {
        List<AddCardingMachine> Data = addCardingService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddCardingMachine> addCardingMachine = Optional.ofNullable(addCardingRepository.findById(id).
                orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (addCardingMachine.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddCardingMachine.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addCardingMachine.get(), value);
            });
            AddCardingMachine saveuser = addCardingRepository.save(addCardingMachine.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        log.info("{} Status Updated ", addCardingMachine);
        return new ResponseEntity<>(new MessageResponse("Updated "), HttpStatus.OK);
    }
}

package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddFinisherMachine;
import com.unitechApi.addmachine.repositroy.AddFinisherRepository;
import com.unitechApi.addmachine.service.AddFinisherService;
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
@RequestMapping("/unitech/api/v1/addmachine/finisher")
@Slf4j
public class AddFinisherController {

    private final AddFinisherService addFinisherService;
    private final AddFinisherRepository addFinisherRepository;

    public AddFinisherController(AddFinisherService addFinisherService, AddFinisherRepository addFinisherRepository) {
        this.addFinisherService = addFinisherService;
        this.addFinisherRepository = addFinisherRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<AddFinisherMachine> SaveBlowRoom(@RequestBody AddFinisherMachine addFinisherMachine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addFinisherService.savemachine(addFinisherMachine));
    }

    @GetMapping("/{id}")
    public Optional<AddFinisherMachine> FindByidBlowRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addFinisherService.FindByData(id)).getBody();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addFinisherService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        addFinisherService.DeleteReading(id);
    }

    @GetMapping("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status) {
        List<AddFinisherMachine> Data = addFinisherService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddFinisherMachine> addFinisherMachine = Optional.ofNullable(addFinisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (addFinisherMachine.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddFinisherMachine.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addFinisherMachine.get(), value);
            });
            AddFinisherMachine saveuser = addFinisherRepository.save(addFinisherMachine.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        log.info("{} Status Updated ", addFinisherMachine);
        return new ResponseEntity<>(new MessageResponse("Updated "), HttpStatus.OK);
    }
}

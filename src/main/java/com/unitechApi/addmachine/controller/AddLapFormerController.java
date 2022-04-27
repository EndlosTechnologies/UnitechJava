package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddLapFormer;
import com.unitechApi.addmachine.repositroy.AddLapFormerRepository;
import com.unitechApi.addmachine.service.AddLapFormerService;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/unitech/api/v1/addmachine/lapformer")
@Slf4j
public class AddLapFormerController {

    private final AddLapFormerService addLapFormerService;
    private final AddLapFormerRepository addLapFormerRepository;

    public AddLapFormerController(AddLapFormerService addLapFormerService, AddLapFormerRepository addLapFormerRepository) {
        this.addLapFormerService = addLapFormerService;
        this.addLapFormerRepository = addLapFormerRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<?> SaveData(@RequestBody AddLapFormer addLapFormer) {
        return new ResponseEntity<>(addLapFormerService.savemachine(addLapFormer), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetById(@PathVariable Long id) {
        return new ResponseEntity<>(addLapFormerService.FindByData(id), HttpStatus.OK);
    }

    @GetMapping("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status) {
        List<AddLapFormer> Data = addLapFormerService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> FindAll() {
        return new ResponseEntity<>(addLapFormerService.ViewData(), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddLapFormer> addlapformer = Optional.ofNullable(addLapFormerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (addlapformer.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddLapFormer.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addlapformer.get(), value);
            });
            AddLapFormer saveuser = addLapFormerRepository.save(addlapformer.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        log.info("{} Status Updated ", addlapformer);
        return new ResponseEntity<>(new MessageResponse("Updated "), HttpStatus.OK);
    }
}

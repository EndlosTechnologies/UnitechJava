package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddPackingMachine;
import com.unitechApi.addmachine.repositroy.AddPackingRepository;
import com.unitechApi.addmachine.service.AddPackingService;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/addmachine/packing")
@Slf4j
public class AddPackingController {

    private final AddPackingService addPackingService;
    private final AddPackingRepository addPackingRepository;

    public AddPackingController(AddPackingService addPackingService, AddPackingRepository addPackingRepository) {
        this.addPackingService = addPackingService;
        this.addPackingRepository = addPackingRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<?> SaveBlowRoom(@RequestBody AddPackingMachine addPackingMachine) {
        if (addPackingRepository.existsByName(addPackingMachine.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Already Exists " + addPackingMachine.getName()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(addPackingService.SaveData(addPackingMachine));
    }

    @GetMapping("/{id}")
    public Optional<AddPackingMachine> FindByidBlowRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addPackingService.FindByData(id)).getBody();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addPackingService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        addPackingService.DeleteReading(id);

    }

    @GetMapping("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status) {
        List<AddPackingMachine> Data = addPackingService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddPackingMachine> addPackingMachine = Optional.ofNullable(addPackingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (addPackingMachine.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddPackingMachine.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addPackingMachine.get(), value);
            });

           addPackingRepository.save(addPackingMachine.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        log.info(" Status Updated {} ", fields);
        return new ResponseEntity<>(new MessageResponse("Updated :->" + new ArrayList<>(fields.entrySet())), HttpStatus.OK);
    }
}

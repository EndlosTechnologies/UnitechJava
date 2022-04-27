package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddUtillityMachine;
import com.unitechApi.addmachine.repositroy.AddUtilityRepository;
import com.unitechApi.addmachine.service.AddUtilityService;
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
@RequestMapping("/unitech/api/v1/addmachine/utillity")
@Slf4j
public class AddUtillityController {

    private final AddUtilityService addUtilityService;
    private final AddUtilityRepository addUtilityRepository;

    public AddUtillityController(AddUtilityService addUtilityService, AddUtilityRepository addUtilityRepository) {
        this.addUtilityService = addUtilityService;
        this.addUtilityRepository = addUtilityRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<AddUtillityMachine> SaveBlowRoom(@RequestBody AddUtillityMachine addUtillityMachine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addUtilityService.SaveData(addUtillityMachine));
    }

    @GetMapping("/{id}")
    public Optional<AddUtillityMachine> FindByidBlowRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addUtilityService.FindByData(id)).getBody();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addUtilityService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        addUtilityService.DeleteReading(id);
    }

    @GetMapping("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status) {
        List<AddUtillityMachine> Data = addUtilityService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddUtillityMachine> addUtillityMachine = Optional.ofNullable(addUtilityRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resourse Not Found")));
        if (addUtillityMachine.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddUtillityMachine.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addUtillityMachine.get(), value);
            });
            AddUtillityMachine saveuser = addUtilityRepository.save(addUtillityMachine.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        log.info("{} Status Updated ", addUtillityMachine);
        return new ResponseEntity<>(new MessageResponse("Updated "), HttpStatus.OK);
    }
}

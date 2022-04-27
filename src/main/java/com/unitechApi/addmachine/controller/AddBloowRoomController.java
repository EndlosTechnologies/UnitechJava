package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddBloowroom;
import com.unitechApi.addmachine.repositroy.AddBloowRoomRepository;
import com.unitechApi.addmachine.service.Addbloowroomservice;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/addmachine/bloowroom")
@Slf4j
public class AddBloowRoomController {
    private final Addbloowroomservice addbloowroomservice;
    private final AddBloowRoomRepository addBloowRoomRepository;

    public AddBloowRoomController(Addbloowroomservice addbloowroomservice, AddBloowRoomRepository addBloowRoomRepository) {
        this.addbloowroomservice = addbloowroomservice;
        this.addBloowRoomRepository = addBloowRoomRepository;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR')")
    public ResponseEntity<?> SaveBlowRoom(@RequestBody AddBloowroom addBloowroom) {
        AddBloowroom addBloowroomData = addbloowroomservice.savemachine(addBloowroom);
        return new ResponseEntity<>(PageResponse.SuccessResponse(addBloowroomData), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Optional<AddBloowroom> FindByidBlowRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addbloowroomservice.FindByData(id)).getBody();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addbloowroomservice.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        addbloowroomservice.DeleteReading(id);
    }

    @GetMapping("/status")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status) {
        List<AddBloowroom> Data = addbloowroomservice.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddBloowroom> addBloowroom = Optional.ofNullable(addBloowRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound(
                "Resource Not Found"
        )));
        if (addBloowroom.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddBloowroom.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addBloowroom.get(), value);
            });
            AddBloowroom saveuser = addBloowRoomRepository.save(addBloowroom.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        log.info("{} Status Updated ", addBloowroom);
        return new ResponseEntity<>(new MessageResponse("Updated "), HttpStatus.OK);
    }
}



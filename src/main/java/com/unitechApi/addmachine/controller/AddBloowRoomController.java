package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddBloowroom;
import com.unitechApi.addmachine.repositroy.AddBloowRoomRepository;
import com.unitechApi.addmachine.service.Addbloowroomservice;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
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

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR')")
    public ResponseEntity<?> SaveBlowRoom(@RequestBody AddBloowroom addBloowRoom) {
        if (addBloowRoomRepository.existsByName(addBloowRoom.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Already Exists " + addBloowRoom.getName()));
        }
        AddBloowroom addBloowRoomData = addbloowroomservice.savemachine(addBloowRoom);
        return new ResponseEntity<>(PageResponse.SuccessResponse(addBloowRoomData), HttpStatus.CREATED);
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

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddBloowroom> addBloowRoom = Optional.ofNullable(addBloowRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound(
                "Resource Not Found"
        )));
        log.info("machine name {}",addBloowRoom.get().getName());
        if (addBloowRoom.isPresent()) {

            fields.forEach((key, value) -> {

                Field field = ReflectionUtils.findField(AddBloowroom.class, (String) key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, addBloowRoom.get(), value);
                log.info("field {}",field);
            });
            addBloowRoomRepository.save(addBloowRoom.get());

        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        log.info(" Status Updated {} ", fields);
        return new ResponseEntity<>(new MessageResponse("Updated :->" + new ArrayList<>(fields.entrySet())), HttpStatus.OK);
    }
}



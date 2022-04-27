package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddWasteRoomeMAchine;
import com.unitechApi.addmachine.repositroy.AddWasteRoomRepository;
import com.unitechApi.addmachine.service.AddWasteRoomService;
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
@RequestMapping("/unitech/api/v1/addmachine/wasteroom")
@Slf4j
public class AddWasteRoomController {

    private final AddWasteRoomService addWasteRoomService;
    private final AddWasteRoomRepository addWasteRoomRepository;
    public AddWasteRoomController(AddWasteRoomService addWasteRoomService, AddWasteRoomRepository addWasteRoomRepository) {
        this.addWasteRoomService = addWasteRoomService;
        this.addWasteRoomRepository = addWasteRoomRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<AddWasteRoomeMAchine> SaveBlowRoom(@RequestBody AddWasteRoomeMAchine addWasteRoomeMAchine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addWasteRoomService.SaveData(addWasteRoomeMAchine));
    }

    @GetMapping("/{id}")
    public Optional<AddWasteRoomeMAchine> FindByidBlowRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addWasteRoomService.FindByData(id)).getBody();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addWasteRoomService.ViewData());
    }
    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        addWasteRoomService.DeleteReading(id);
    }
    @GetMapping ("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status)
    {
        List<AddWasteRoomeMAchine> Data=addWasteRoomService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data),HttpStatus.OK);
    }
    @PatchMapping("/update")
    public ResponseEntity<?> UpdateData(@PathVariable Long id,@RequestBody Map<Object, Object> fields) {
        Optional<AddWasteRoomeMAchine> addWasteRoomeMAchine = Optional.ofNullable(addWasteRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (addWasteRoomeMAchine.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddWasteRoomeMAchine.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addWasteRoomeMAchine.get(), value);
            });
            AddWasteRoomeMAchine saveuser=addWasteRoomRepository.save(addWasteRoomeMAchine.get());
        }
        else
        {
            throw new UserNotFound("User Not Found "+id);
        }
        log.info("{} Status Updated ",addWasteRoomeMAchine);
        return new ResponseEntity<>(new MessageResponse("Updated "),HttpStatus.OK);    }
}

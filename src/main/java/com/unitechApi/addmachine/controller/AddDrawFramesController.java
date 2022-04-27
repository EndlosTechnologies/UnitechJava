package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddDrawFramesMachine;
import com.unitechApi.addmachine.repositroy.AddDrawFramesRepository;
import com.unitechApi.addmachine.service.AddDrawMachienService;
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
@RequestMapping("/unitech/api/v1/addmachine/drawframes")
@Slf4j
public class AddDrawFramesController {

    private final AddDrawMachienService addDrawMachienService;
    private final AddDrawFramesRepository addDrawFramesRepository;

    public AddDrawFramesController(AddDrawMachienService addDrawMachienService, AddDrawFramesRepository addDrawFramesRepository) {
        this.addDrawMachienService = addDrawMachienService;
        this.addDrawFramesRepository = addDrawFramesRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<AddDrawFramesMachine> SaveBlowRoom(@RequestBody AddDrawFramesMachine addDrawFramesMachine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addDrawMachienService.savemachineDraw(addDrawFramesMachine));
    }

    @GetMapping("/{id}")
    public Optional<AddDrawFramesMachine> FindByidBlowRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addDrawMachienService.FindByData(id)).getBody();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addDrawMachienService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        addDrawMachienService.DeleteReading(id);
    }

    @GetMapping("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status) {
        List<AddDrawFramesMachine> Data = addDrawMachienService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddDrawFramesMachine> addDrawFramesMachine = Optional.ofNullable(addDrawFramesRepository.findById(id).
                orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (addDrawFramesMachine.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddDrawFramesMachine.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addDrawFramesMachine.get(), value);
            });
            AddDrawFramesMachine saveuser = addDrawFramesRepository.save(addDrawFramesMachine.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        log.info("{} Status Updated ", addDrawFramesMachine);
        return new ResponseEntity<>(new MessageResponse("Updated "), HttpStatus.OK);
    }
}

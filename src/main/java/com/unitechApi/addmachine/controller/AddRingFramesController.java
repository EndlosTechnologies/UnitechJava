package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddRingFramesMachine;
import com.unitechApi.addmachine.repositroy.AddRingFrameRepossitory;
import com.unitechApi.addmachine.service.AddRingframesService;
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
@RequestMapping("/unitech/api/v1/addmachine/ringframes")
@Slf4j
public class AddRingFramesController {

    private final AddRingframesService addRingframesService;
    private final AddRingFrameRepossitory addRingFrameRepossitory;

    public AddRingFramesController(AddRingframesService addRingframesService, AddRingFrameRepossitory addRingFrameRepossitory) {
        this.addRingframesService = addRingframesService;
        this.addRingFrameRepossitory = addRingFrameRepossitory;
    }

    @PostMapping("/save")
    public ResponseEntity<?> SaveBlowRoom(@RequestBody AddRingFramesMachine addRingFramesMachine) {
      if (addRingFrameRepossitory.existsByName(addRingFramesMachine.getName())) {
          return ResponseEntity.badRequest().body(new MessageResponse("Already Exists " + addRingFramesMachine.getName()));
      }
        return ResponseEntity.status(HttpStatus.CREATED).body(addRingframesService.SaveData(addRingFramesMachine));
    }

    @GetMapping("/{id}")
    public Optional<AddRingFramesMachine> FindByidBlowRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addRingframesService.FindByData(id)).getBody();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addRingframesService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        addRingframesService.DeleteReading(id);
    }

    @GetMapping("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status) {
        List<AddRingFramesMachine> Data = addRingframesService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddRingFramesMachine> addRingFramesMachine = Optional.ofNullable(addRingFrameRepossitory.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource Not found")));
        if (addRingFramesMachine.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddRingFramesMachine.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addRingFramesMachine.get(), value);
            });

            addRingFrameRepossitory.save(addRingFramesMachine.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        log.info(" Status Updated {} ", fields);
        return new ResponseEntity<>(new MessageResponse("Updated :->" + new ArrayList<>(fields.entrySet())), HttpStatus.OK);
    }
}

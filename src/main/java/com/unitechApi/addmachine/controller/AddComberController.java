package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddComber;
import com.unitechApi.addmachine.repositroy.AddComberRepository;
import com.unitechApi.addmachine.service.AddComberService;
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
@RequestMapping("/unitech/api/v1/addmachine/comber")
@Slf4j
public class AddComberController {

    private final AddComberService addComberService;
    private final AddComberRepository addComberRepository;

    public AddComberController(AddComberService addComberService, AddComberRepository addComberRepository) {
        this.addComberService = addComberService;
        this.addComberRepository = addComberRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<?> SaveData(@RequestBody AddComber addComber) {
        return new ResponseEntity<>(addComberService.savemachine(addComber), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> GetById(@PathVariable Long id)
    {
        return new ResponseEntity<>(addComberService.FindByData(id),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addComberService.ViewData());
    }
    @GetMapping ("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status)
    {
        List<AddComber> Data=addComberService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data),HttpStatus.OK);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<AddComber> addcomber = Optional.ofNullable(addComberRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (addcomber.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddComber.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addcomber.get(), value);
            });
            AddComber saveuser=addComberRepository.save(addcomber.get());
        }
        else
        {
            throw new UserNotFound("User Not Found "+id);
        }
        log.info("{} Status Updated ",addcomber);
        return new ResponseEntity<>(new MessageResponse("Updated "), HttpStatus.OK);
    }
}

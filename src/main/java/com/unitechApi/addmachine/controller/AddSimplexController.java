package com.unitechApi.addmachine.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddSimplexMAchine;
import com.unitechApi.addmachine.repositroy.AddSimplexRepository;
import com.unitechApi.addmachine.service.AddSimplexService;
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
@RequestMapping("/unitech/api/v1/addmachine/simplex")
@Slf4j
public class AddSimplexController {

   private final AddSimplexService addSimplexService;
   private final AddSimplexRepository addSimplexRepository;

    public AddSimplexController(AddSimplexService addSimplexService, AddSimplexRepository addSimplexRepository) {
        this.addSimplexService = addSimplexService;
        this.addSimplexRepository = addSimplexRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<AddSimplexMAchine> SaveBlowRoom(@RequestBody AddSimplexMAchine addRingFramesMachine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addSimplexService.SaveData(addRingFramesMachine));
    }

    @GetMapping("/{id}")
    public Optional<AddSimplexMAchine> FindByidBlowRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addSimplexService.FindByData(id)).getBody();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addSimplexService.ViewData());
    }
    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        addSimplexService.DeleteReading(id);
    }
    @GetMapping ("/status/")
    public ResponseEntity<?> FindByStatus(@RequestParam boolean status)
    {
        List<AddSimplexMAchine> Data=addSimplexService.Status(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data),HttpStatus.OK);
    }
    @PatchMapping("/update")
    public ResponseEntity<?> UpdateData(@PathVariable Long id,@RequestBody Map<Object, Object> fields) {
        Optional<AddSimplexMAchine> addSimplexMAchine = Optional.ofNullable(addSimplexRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (addSimplexMAchine.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddSimplexMAchine.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, addSimplexMAchine.get(), value);
            });
            AddSimplexMAchine saveuser=addSimplexRepository.save(addSimplexMAchine.get());
        }
        else
        {
            throw new UserNotFound("User Not Found "+id);
        }
        log.info("{} Status Updated ",addSimplexMAchine);
        return new ResponseEntity<>(new MessageResponse("Updated "),HttpStatus.OK);    }
}

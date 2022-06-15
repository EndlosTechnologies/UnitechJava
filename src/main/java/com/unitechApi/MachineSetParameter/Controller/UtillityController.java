package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.model.Utiliity;
import com.unitechApi.MachineSetParameter.repository.UtilityRepository;
import com.unitechApi.MachineSetParameter.service.UtilityService;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddUtillityMachine;
import com.unitechApi.addmachine.repositroy.AddUtilityRepository;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/machine/utility")
public class UtillityController {

    private final AddUtilityRepository addUtilityRepository;
    private final UtilityRepository utilityRepository;
    private final UtilityService utilityService;

    public UtillityController(AddUtilityRepository addUtilityRepository, UtilityRepository utilityRepository, UtilityService utilityService) {
        this.addUtilityRepository = addUtilityRepository;
        this.utilityRepository = utilityRepository;
        this.utilityService = utilityService;
    }

    @PostMapping("/save")
    public ResponseEntity<Utiliity> SaveData(@RequestBody Utiliity utiliity) {
        return ResponseEntity.ok(utilityService.SaveData(utiliity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Utiliity>> findByid(@PathVariable Long id) {
        return ResponseEntity.ok(utilityService.FindByData(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(utilityService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        utilityService.DeleteReading(id);
    }

    @PatchMapping("/update")
    public ResponseEntity<Utiliity> UpdateData(@PathVariable Long id, Map<Object, Object> fields) {
        Optional<Utiliity> utiliity = utilityRepository.findById(id);
        if (utiliity.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Utiliity.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, utiliity.get(), value);
            });
            Utiliity saveuser = utilityRepository.save(utiliity.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getRecordByCreatedDate(@RequestParam Date start, @RequestParam Date end) {


        List<Utiliity> pagecontent = utilityService.FindData(start, end);
        return new ResponseEntity<>(PageResponse.SuccessResponse(pagecontent), HttpStatus.OK);
    }


    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start) {
        List<Utiliity> bloowRooms = utilityService.FindBySingleDate(start);
        return new ResponseEntity<>(PageResponse.SuccessResponse(bloowRooms), HttpStatus.OK);
    }

    @PutMapping("/u_a_id/update/u_id")
    public Utiliity updateid(@PathVariable Long u_a_id, @PathVariable Long u_id) {
        AddUtillityMachine addUtillityMachine = addUtilityRepository.findById(u_a_id).get();
        Utiliity utiliity = utilityRepository.findById(u_id).get();
        utiliity.idupdate(addUtillityMachine);
        return utilityRepository.save(utiliity);

    }
}

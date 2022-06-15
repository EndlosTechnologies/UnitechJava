package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.model.Winding;
import com.unitechApi.MachineSetParameter.repository.WindingRepository;
import com.unitechApi.MachineSetParameter.service.WindingService;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddWindingMachine;
import com.unitechApi.addmachine.repositroy.AddWindingRepository;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import org.springframework.data.domain.Page;
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
@RequestMapping("/unitech/api/v1/machine/winding")
public class WindingController {

    private final WindingService windinfService;
    private final AddWindingRepository addWindingRepository;
    private final WindingRepository windingRepository;

    public WindingController(WindingService windinfService, AddWindingRepository addWindingRepository, WindingRepository windingRepository) {
        this.windinfService = windinfService;
        this.addWindingRepository = addWindingRepository;
        this.windingRepository = windingRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<Winding> SaveData(@RequestBody Winding winding) {
        return ResponseEntity.ok(windinfService.SaveData(winding));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Winding>> findByid(@PathVariable Long id) {
        return ResponseEntity.ok(windinfService.FindByData(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(windinfService.ViewData());
        //System.out.println(bloowRoomService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        windinfService.DeleteReading(id);
    }

    @PatchMapping("/update")
    public ResponseEntity<Winding> UpdateData(@PathVariable Long id, @RequestBody  Map<Object, Object> fields) {
        Optional<Winding> winding = windingRepository.findById(id);
        if (winding.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Winding.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, winding.get(), value);
            });
            Winding saveuser = windingRepository.save(winding.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getRecordByCreatedDate(@RequestParam Date start, @RequestParam Date end) {


        List<Winding> pagecontent = windinfService.FindData(start, end);
        return new ResponseEntity<>(PageResponse.SuccessResponse(pagecontent), HttpStatus.OK);
    }


    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start) {
        List<Winding> bloowRooms=windinfService.FindBySingleDate(start);
        return new ResponseEntity<>(PageResponse.SuccessResponse(bloowRooms), HttpStatus.OK);
    }

    @PutMapping("/wi_a_id/update/wi_id")
    public Winding updateid(@PathVariable Long wi_a_id, @PathVariable Long wi_id) {
        AddWindingMachine addWindingMachine = addWindingRepository.findById(wi_a_id).get();
        Winding winding = windingRepository.findById(wi_id).get();
        winding.idupdate(addWindingMachine);
        return windingRepository.save(winding);

    }
}

package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.model.Wasteroom;
import com.unitechApi.MachineSetParameter.repository.WasteRoomRepository;
import com.unitechApi.MachineSetParameter.service.WasteRoomService;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddWasteRoomeMAchine;
import com.unitechApi.addmachine.repositroy.AddWasteRoomRepository;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/machine/wasteroom")
public class WasteRoomController {

    private final WasteRoomService wasteRoomService;
    private final AddWasteRoomRepository addWasteRoomRepository;
    private final WasteRoomRepository wasteRoomRepository;

    public WasteRoomController(WasteRoomService wasteRoomService, AddWasteRoomRepository addWasteRoomRepository, WasteRoomRepository wasteRoomRepository) {
        this.wasteRoomService = wasteRoomService;
        this.addWasteRoomRepository = addWasteRoomRepository;
        this.wasteRoomRepository = wasteRoomRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<Wasteroom> SaveData(@RequestBody Wasteroom wasteroom) {
        return ResponseEntity.ok(wasteRoomService.SaveData(wasteroom));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Wasteroom>> findByid(@PathVariable Long id) {
        return ResponseEntity.ok(wasteRoomService.FindByData(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(wasteRoomService.ViewData());
        //System.out.println(bloowRoomService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        wasteRoomService.DeleteReading(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getRecordByCreatedDate(@RequestParam(required = false) Date start, @RequestParam(required = false) Date end,
                                                                      @RequestParam(required = false, defaultValue = "0") int page,
                                                                      @RequestParam(required = false, defaultValue = "2") int size) {
        Pagination pagination = new Pagination(page, size);
        Page<Wasteroom> pagecontent = wasteRoomService.FindData(start, end, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(pagecontent, pagination), HttpStatus.OK);
    }


    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start, @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<Wasteroom> bloowRooms = wasteRoomService.FindBySingleDate(start, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(bloowRooms, pagination), HttpStatus.OK);
    }

    @PutMapping("/wa_a_id/update/w_id")
    public Wasteroom updateid(@PathVariable Long wa_a_id, @PathVariable Long wa_id) {
        AddWasteRoomeMAchine addWasteRoomeMAchine = addWasteRoomRepository.findById(wa_a_id).get();
        Wasteroom wasteroom = wasteRoomRepository.findById(wa_id).get();
        wasteroom.idupdate(addWasteRoomeMAchine);
        return wasteRoomRepository.save(wasteroom);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Wasteroom> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<Wasteroom> wasteroom = wasteRoomRepository.findById(id);
        if (wasteroom.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = org.springframework.data.util.ReflectionUtils.findRequiredField(Wasteroom.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, wasteroom.get(), value);
            });
            Wasteroom save = wasteRoomRepository.save(wasteroom.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        return null;
    }
}

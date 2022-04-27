package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.model.Packing;
import com.unitechApi.MachineSetParameter.repository.PackingRepository;
import com.unitechApi.MachineSetParameter.service.PackingService;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddPackingMachine;
import com.unitechApi.addmachine.repositroy.AddPackingRepository;
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
@RequestMapping("/unitech/api/v1/machine/packing")
public class PackingController {

    @Autowired
    private final AddPackingRepository addPackingRepository;
    private final PackingRepository packingRepository;
    private final PackingService packingService;

    public PackingController(AddPackingRepository addPackingRepository, PackingRepository packingRepository, PackingService packingService) {
        this.addPackingRepository = addPackingRepository;
        this.packingRepository = packingRepository;
        this.packingService = packingService;
    }

    @PostMapping("/save")
    public ResponseEntity<Packing> SaveData(@RequestBody Packing packing) {
        return ResponseEntity.ok(packingService.SaveData(packing));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Packing>> findByid(@PathVariable Long id) {
        return ResponseEntity.ok(packingService.FindByData(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(packingService.ViewData());

    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        packingService.DeleteReading(id);
    }

    @PatchMapping("/update")
    public ResponseEntity<Packing> UpdateData(@PathVariable Long id, Map<Object, Object> fields) {
        Optional<Packing> packing = packingRepository.findById(id);
        if (packing.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Packing.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, packing.get(), value);
            });
            Packing saveuser = packingRepository.save(packing.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getRecordByCreatedDate(@RequestParam Date start, @RequestParam Date end,
                                                                      @RequestParam(required = false, defaultValue = "0") int page,
                                                                      @RequestParam(required = false, defaultValue = "2") int size) {
        Pagination pagination = new Pagination(page, size);
        Page<Packing> pagecontent = packingService.FindData(start, end, pagination);

        return new ResponseEntity<>(PageResponse.pagebleResponse(pagecontent, pagination), HttpStatus.OK);
    }


    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start, @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<Packing> bloowRooms = packingService.FindBySingleDate(start, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(bloowRooms, pagination), HttpStatus.OK);
    }

    @PutMapping("{a_p_id}/update/{p_id}")
    public Packing updateid(@PathVariable Long a_p_id, @PathVariable Long p_id) {
        AddPackingMachine addPackingMachine = addPackingRepository.findById(a_p_id).get();
        Packing packing = packingRepository.findById(p_id).get();
        packing.idupdate(addPackingMachine);
        return packingRepository.save(packing);
    }
}

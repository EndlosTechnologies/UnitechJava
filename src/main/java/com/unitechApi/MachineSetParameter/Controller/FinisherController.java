package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.DtoRest.FinisherperKgRest;
import com.unitechApi.MachineSetParameter.ExcelService.FinisherPerKgExcelService;
import com.unitechApi.MachineSetParameter.model.FinisherperKg;
import com.unitechApi.MachineSetParameter.repository.FinisherRepository;
import com.unitechApi.MachineSetParameter.service.FinisherperKgservice;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddFinisherMachine;
import com.unitechApi.addmachine.repositroy.AddFinisherRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.TimeExtendException;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/machine/finisher")
@ToString
public class FinisherController {
    private final AddFinisherRepository addFinisherRepository;
    private final FinisherRepository finisherRepository;
    private final FinisherperKgservice finisherperKgservice;

    public FinisherController(AddFinisherRepository addFinisherRepository, FinisherRepository finisherRepository, FinisherperKgservice finisherperKgservice) {
        this.addFinisherRepository = addFinisherRepository;
        this.finisherRepository = finisherRepository;
        this.finisherperKgservice = finisherperKgservice;
    }

    @PostMapping("/save")
    public ResponseEntity<FinisherperKg> SaveData(@RequestBody FinisherperKg finisherperKg) {
        return ResponseEntity.ok(finisherperKgservice.SaveData(finisherperKg));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<FinisherperKg>> findByid(@PathVariable Long id) {
        return ResponseEntity.ok(finisherperKgservice.FindByData(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(finisherperKgservice.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        finisherperKgservice.DeleteReading(id);
    }

    @PatchMapping("/update")
    public ResponseEntity<FinisherperKg> UpdateData(@PathVariable Long id, Map<Object, Object> fields) {
        Optional<FinisherperKg> finisher = finisherRepository.findById(id);
        if (finisher.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(FinisherperKg.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, finisher.get(), value);
            });
            FinisherperKg saveuser = finisherRepository.save(finisher.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getRecordByCreatedDate(@RequestParam Date start, @RequestParam Date end
                                                                      ) {


        List<FinisherperKg> pagecontent = finisherperKgservice.FindData(start, end);
        return new ResponseEntity<>(PageResponse.SuccessResponse(pagecontent), HttpStatus.OK);
    }


    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start) {

        List<FinisherperKg> bloowRooms = finisherperKgservice.FindBySingleDate(start);
        return new ResponseEntity<>(PageResponse.SuccessResponse(bloowRooms), HttpStatus.OK);
    }

    @PutMapping("/{f_a_id}/update/{f_id}")
    public FinisherperKg updateid(@PathVariable Long f_a_id, @PathVariable Long f_id) {
        AddFinisherMachine addFinisherMachine = addFinisherRepository.findById(f_a_id).get();
        FinisherperKg finisherperKg = finisherRepository.findById(f_id).get();
        finisherperKg.idupdate(addFinisherMachine);
        return finisherRepository.save(finisherperKg);
    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportToExcel(@RequestParam Date start,
                              @RequestParam Date end, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=FInisherData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<FinisherperKg> ListData = finisherperKgservice.ExcelDateToDateReport(start, end);
        ListData.forEach(carding -> System.out.println(carding));
        FinisherPerKgExcelService c = new FinisherPerKgExcelService(ListData);
        c.export(response);


    }

    @GetMapping("/downloadSingle")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void perDayExportToExcel(@RequestParam Date start,
                                    HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=FInisherData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<FinisherperKg> ListData = finisherperKgservice.ExcelDateToPerDateReport(start);
        ListData.forEach(carding -> System.out.println(carding));

        FinisherPerKgExcelService c = new FinisherPerKgExcelService(ListData);
        c.export(response);

    }

    @PatchMapping("/updateShiftAOne/{id}")
    public ResponseEntity<?> UpdateShiftAOne(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("08:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("02:20:00 PM")))
        ) {
            FinisherperKg finisherperKg = finisherRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            FinisherperKgRest finisherperKgRest = mapPersitenceToRestmodel(finisherperKg);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursOne":
                        finisherperKgRest.setShift_a_sixHoursOne(values);
                }
            });
            mapRestModelToPersistenceShiftAOne(finisherperKg, finisherperKgRest);
            finisherRepository.save(finisherperKg);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftAOne(FinisherperKg finisherperKg, FinisherperKgRest finisherperKgRest) {
        finisherperKg.setMachineId(finisherperKgRest.getId());
        finisherperKg.setShift_a_sixHoursOne(finisherperKgRest.getShift_a_sixHoursOne());
        finisherperKg.setAvervg_difference_a_sixHoursOne((finisherperKg.getShift_a_sixHoursOne() - finisherperKg.getMachineefficencykgdfper6hours()
        ) / finisherperKg.getMachineefficencykgdfper6hours() * 100);
        finisherperKg.setTotal_shift_prod_a(finisherperKg.getShift_a_sixHoursOne() + finisherperKg.getShift_a_sixHoursTwo());
        finisherperKg.setTotal_shift_prod_b(finisherperKg.getShift_b_sixHoursOne() + finisherperKg.getShift_b_sixHoursTwo());

        finisherperKg.setActual_producation(finisherperKg.getTotal_shift_prod_b() + finisherperKg.getTotal_shift_prod_a());

        finisherperKg.setEfficiency((finisherperKg.getActual_producation() / finisherperKg.getProductiononratekgdfper8hour() * 3) * 100);
        finisherperKg.setTarget_prod_variance_kg(finisherperKg.getMachineefficencykgdfperday() - finisherperKg.getActual_producation());
        finisherperKg.setTarget_prod_variance(((finisherperKg.getActual_producation() - finisherperKg.getMachineefficencykgdfperday())
                / finisherperKg.getMachineefficencykgdfperday()) * 100);
    }

    @PatchMapping("/updateShiftATwo/{id}")
    public ResponseEntity<?> UpdateShiftATwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("02:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("08:20:00 PM")))
        ) {
            FinisherperKg finisherperKg = finisherRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            FinisherperKgRest finisherperKgRest = mapPersitenceToRestmodel(finisherperKg);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursTwo":
                        finisherperKgRest.setShift_a_sixHoursTwo(values);
                }
            });
            mapRestModelToPersistenceShiftATwo(finisherperKg, finisherperKgRest);
            finisherRepository.save(finisherperKg);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftATwo(FinisherperKg finisherperKg, FinisherperKgRest finisherperKgRest) {
        finisherperKg.setMachineId(finisherperKgRest.getId());
        finisherperKg.setShift_a_sixHoursTwo(finisherperKgRest.getShift_a_sixHoursTwo());
        finisherperKg.setAvervg_difference_a_sixHoursTwo((finisherperKg.getShift_a_sixHoursTwo() - finisherperKg.getMachineefficencykgdfper6hours()) /
                finisherperKg.getMachineefficencykgdfper6hours() * 100);
        finisherperKg.setTotal_shift_prod_a(finisherperKg.getShift_a_sixHoursOne() + finisherperKg.getShift_a_sixHoursTwo());
        finisherperKg.setTotal_shift_prod_b(finisherperKg.getShift_b_sixHoursOne() + finisherperKg.getShift_b_sixHoursTwo());

        finisherperKg.setActual_producation(finisherperKg.getTotal_shift_prod_b() + finisherperKg.getTotal_shift_prod_a());

        finisherperKg.setEfficiency((finisherperKg.getActual_producation() / finisherperKg.getProductiononratekgdfper8hour() * 3) * 100);
        finisherperKg.setTarget_prod_variance_kg(finisherperKg.getMachineefficencykgdfperday() - finisherperKg.getActual_producation());
        finisherperKg.setTarget_prod_variance(((finisherperKg.getActual_producation() - finisherperKg.getMachineefficencykgdfperday())
                / finisherperKg.getMachineefficencykgdfperday()) * 100);
    }

    @PatchMapping("/updateShiftBOne/{id}")
    public ResponseEntity<?> UpdateShiftBOne(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if ((LocalTime.now().isAfter(LocalTime.from(timeformat.parse("08:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("11:59:59 PM"))))
                || (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("00:00:00 AM")))
                && LocalTime.now().isBefore(LocalTime.from(timeformat.parse("02:20:00 AM"))))
        ) {
            FinisherperKg finisherperKg = finisherRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            FinisherperKgRest finisherperKgRest = mapPersitenceToRestmodel(finisherperKg);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursOne":
                        finisherperKgRest.setShift_b_sixHoursOne(values);
                }
            });
            mapRestModelToPersistenceShiftBOne(finisherperKg, finisherperKgRest);
            finisherRepository.save(finisherperKg);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftBOne(FinisherperKg finisherperKg, FinisherperKgRest finisherperKgRest) {
        finisherperKg.setMachineId(finisherperKgRest.getId());
        finisherperKg.setShift_b_sixHoursOne(finisherperKgRest.getShift_b_sixHoursOne());
        finisherperKg.setAvervg_difference_b_sixHoursOne((finisherperKg.getShift_b_sixHoursOne() - finisherperKg.getMachineefficencykgdfper6hours()
        ) / finisherperKg.getMachineefficencykgdfper6hours() * 100);
        finisherperKg.setTotal_shift_prod_a(finisherperKg.getShift_a_sixHoursOne() + finisherperKg.getShift_a_sixHoursTwo());
        finisherperKg.setTotal_shift_prod_b(finisherperKg.getShift_b_sixHoursOne() + finisherperKg.getShift_b_sixHoursTwo());

        finisherperKg.setActual_producation(finisherperKg.getTotal_shift_prod_b() + finisherperKg.getTotal_shift_prod_a());

        finisherperKg.setEfficiency((finisherperKg.getActual_producation() / finisherperKg.getProductiononratekgdfper8hour() * 3) * 100);
        finisherperKg.setTarget_prod_variance_kg(finisherperKg.getMachineefficencykgdfperday() - finisherperKg.getActual_producation());
        finisherperKg.setTarget_prod_variance(((finisherperKg.getActual_producation() - finisherperKg.getMachineefficencykgdfperday())
                / finisherperKg.getMachineefficencykgdfperday()) * 100);
    }

    @PatchMapping("/updateShiftBTwo/{id}")
    public ResponseEntity<?> UpdateShiftBTwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("02:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("08:20:00 AM")))
        ) {
            FinisherperKg finisherperKg = finisherRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            FinisherperKgRest finisherperKgRest = mapPersitenceToRestmodel(finisherperKg);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursTwo":
                        finisherperKgRest.setShift_b_sixHoursTwo(values);
                }
            });
            mapRestModelToPersistenceShiftBTwo(finisherperKg, finisherperKgRest);
            finisherRepository.save(finisherperKg);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftBTwo(FinisherperKg finisherperKg, FinisherperKgRest finisherperKgRest) {
        finisherperKg.setMachineId(finisherperKgRest.getId());
        finisherperKg.setShift_b_sixHoursTwo(finisherperKgRest.getShift_b_sixHoursTwo());
        finisherperKg.setAvervg_difference_b_sixHoursTwo((finisherperKg.getShift_b_sixHoursTwo() - finisherperKg.getMachineefficencykgdfper6hours()
        ) / finisherperKg.getMachineefficencykgdfper6hours() * 100);
        finisherperKg.setTotal_shift_prod_a(finisherperKg.getShift_a_sixHoursOne() + finisherperKg.getShift_a_sixHoursTwo());
        finisherperKg.setTotal_shift_prod_b(finisherperKg.getShift_b_sixHoursOne() + finisherperKg.getShift_b_sixHoursTwo());

        finisherperKg.setActual_producation(finisherperKg.getTotal_shift_prod_b() + finisherperKg.getTotal_shift_prod_a());

        finisherperKg.setEfficiency((finisherperKg.getActual_producation() / finisherperKg.getProductiononratekgdfper8hour() * 3) * 100);
        finisherperKg.setTarget_prod_variance_kg(finisherperKg.getMachineefficencykgdfperday() - finisherperKg.getActual_producation());
        finisherperKg.setTarget_prod_variance(((finisherperKg.getActual_producation() - finisherperKg.getMachineefficencykgdfperday())
                / finisherperKg.getMachineefficencykgdfperday()) * 100);
    }

    private FinisherperKgRest mapPersitenceToRestmodel(FinisherperKg finisherperKg) {
        FinisherperKgRest finisherperKgRest = new FinisherperKgRest();
        finisherperKgRest.setId(finisherperKg.getMachineId());

        // data shift A  part one Reading

        finisherperKgRest.setShift_a_sixHoursOne(finisherperKg.getShift_a_sixHoursOne());
        finisherperKgRest.setAvervg_difference_a_sixHoursOne(finisherperKg.getAvervg_difference_a_sixHoursOne());

        // data shift A  part second Reading

        finisherperKgRest.setShift_a_sixHoursTwo(finisherperKg.getShift_a_sixHoursTwo());
        finisherperKgRest.setAvervg_difference_a_sixHoursTwo(finisherperKg.getAvervg_difference_a_sixHoursTwo());
        finisherperKgRest.setTotal_shift_prod_a(finisherperKg.getTotal_shift_prod_a());

        // data shift B  part one Reading

        finisherperKgRest.setShift_b_sixHoursOne(finisherperKg.getShift_b_sixHoursOne());
        finisherperKgRest.setAvervg_difference_b_sixHoursOne(finisherperKg.getAvervg_difference_b_sixHoursOne());

        // data shift B  part second Reading

        finisherperKgRest.setShift_b_sixHoursTwo(finisherperKg.getShift_b_sixHoursTwo());
        finisherperKgRest.setAvervg_difference_b_sixHoursTwo(finisherperKg.getAvervg_difference_b_sixHoursTwo());

        // total production Reading

        finisherperKgRest.setActual_producation(finisherperKg.getActual_producation());
        finisherperKgRest.setEfficiency(finisherperKg.getEfficiency());
        finisherperKgRest.setTarget_prod_variance_kg(finisherperKg.getTarget_prod_variance_kg());
        finisherperKgRest.setTarget_prod_variance(finisherperKg.getTarget_prod_variance());

        return finisherperKgRest;
    }
}

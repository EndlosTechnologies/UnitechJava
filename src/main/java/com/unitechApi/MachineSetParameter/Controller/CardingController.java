package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.DtoRest.CardingRest;
import com.unitechApi.MachineSetParameter.ExcelService.CardingExcelService;
import com.unitechApi.MachineSetParameter.model.Carding;
import com.unitechApi.MachineSetParameter.repository.CardingRepository;
import com.unitechApi.MachineSetParameter.service.CardingService;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddCardingMachine;
import com.unitechApi.addmachine.repositroy.AddCardingRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.TimeExtendException;
import com.unitechApi.exception.ExceptionService.UserNotFound;
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
@RequestMapping("unitech/api/v1/machine/carding")
public class CardingController {
    private final CardingService cardingService;
    private final AddCardingRepository addCardingRepository;
    private final CardingRepository cardingRepository;

    public CardingController(CardingService cardingService, AddCardingRepository addCardingRepository, CardingRepository cardingRepository) {
        this.cardingService = cardingService;
        this.addCardingRepository = addCardingRepository;
        this.cardingRepository = cardingRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<Carding> SaveData(@RequestBody Carding carding) {
        return ResponseEntity.ok(cardingService.SaveData(carding));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Carding>> findByid(@PathVariable Long id) {
        return ResponseEntity.ok(cardingService.FindByData(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(cardingService.ViewData());
        //System.out.println(bloowRoomService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        cardingService.DeleteReading(id);
    }

    @PatchMapping("/update")
    public ResponseEntity<Carding> UpdateData(@PathVariable Long id, Map<Object, Object> fields) {
        Optional<Carding> carding = cardingRepository.findById(id);
        if (carding.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Carding.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, carding.get(), value);
            });
            Carding saveuser = cardingRepository.save(carding.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<?> getRecordByCreatedDate(@RequestParam Date start, @RequestParam Date end) {

        List<Carding> pagecontent = cardingService.FindByDate(start, end);
        return new ResponseEntity<>(PageResponse.SuccessResponse(pagecontent), HttpStatus.OK);
    }

    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start, @RequestParam int pagesize) {

        List<Carding> carding= cardingService.FindBySingleDate(start);
        return new ResponseEntity<>(PageResponse.SuccessResponse(carding), HttpStatus.OK);
    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportToExcel(@RequestParam Date start,
                              @RequestParam Date end, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cardingData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Carding> ListData = cardingService.ExcelDateToDateReport(start, end);
        ListData.forEach(carding -> System.out.println(carding));

        CardingExcelService c = new CardingExcelService(ListData);
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
        String headerValue = "attachment; filename=cardingData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Carding> ListData = cardingService.ExcelDateToPerDateReport(start);
        ListData.forEach(carding -> System.out.println(carding));

        CardingExcelService c = new CardingExcelService(ListData);
        c.export(response);
    }

    @PutMapping("/{c_a_id}/update/{c_id}")
    public Carding cardingIdUpdate(@PathVariable Long c_a_id, @PathVariable Long c_id) {
        AddCardingMachine addCardingMachine = addCardingRepository.findById(c_a_id).get();
        Carding carding = cardingRepository.findById(c_id).get();
        carding.idupdateincarding(addCardingMachine);
        return cardingRepository.save(carding);
    }

    @PatchMapping("/updateShiftAOne/{id}")
    public ResponseEntity<?> UpdateShiftAOne(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("08:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("02:20:00 PM")))
        ) {
            Carding carding = cardingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            CardingRest cardingRest = mapPersitenceToRestmodel(carding);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursOne":
                        cardingRest.setShift_a_sixHoursOne(values);
                }
            });
            mapRestModelToPersistenceShiftAOne(carding, cardingRest);
            cardingRepository.save(carding);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }


    private void mapRestModelToPersistenceShiftAOne(Carding carding, CardingRest cardingRest) {
        carding.setMachineId(cardingRest.getId());
        carding.setShift_a_sixHoursOne(cardingRest.getShift_a_sixHoursOne());
        carding.setAvervg_difference_a_sixHoursOne((carding.getShift_a_sixHoursOne() - carding.getMachineefficencykgcardpersixhours()
        ) / carding.getMachineefficencykgcardpersixhours() * 100);
        carding.setTotal_shift_prod_a(carding.getShift_a_sixHoursOne() + carding.getShift_a_sixHoursTwo());
        carding.setTotal_shift_prod_b(carding.getShift_b_sixHoursOne() + carding.getShift_b_sixHoursTwo());

        carding.setActual_producation(carding.getTotal_shift_prod_b() + carding.getTotal_shift_prod_a());

        carding.setEfficiency((carding.getActual_producation() / carding.getMachineefficencykgcardperday()) * 100);
        carding.setTarget_prod_variance_kg(carding.getMachineefficencykgcardperday() - carding.getActual_producation());
        carding.setTarget_prod_variance(((carding.getActual_producation() - carding.getMachineefficencykgcardperday())
                / carding.getMachineefficencykgcardperday()) * 100);
    }

    @PatchMapping("/updateShiftATwo/{id}")
    public ResponseEntity<?> UpdateShiftATwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("02:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("08:20:00 PM")))
        ) {
            Carding carding = cardingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            CardingRest cardingRest = mapPersitenceToRestmodel(carding);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursTwo":
                        cardingRest.setShift_a_sixHoursTwo(values);
                }
            });
            mapRestModelToPersistenceShiftATwo(carding, cardingRest);
            cardingRepository.save(carding);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftATwo(Carding carding, CardingRest cardingRest) {
        carding.setMachineId(cardingRest.getId());
        carding.setShift_a_sixHoursTwo(cardingRest.getShift_a_sixHoursTwo());
        carding.setAvervg_difference_a_sixHoursTwo((carding.getShift_a_sixHoursTwo() - carding.getMachineefficencykgcardpersixhours()) /
                carding.getMachineefficencykgcardpersixhours() * 100);

        carding.setTotal_shift_prod_a(carding.getShift_a_sixHoursOne() + carding.getShift_a_sixHoursTwo());
        carding.setTotal_shift_prod_b(carding.getShift_b_sixHoursOne() + carding.getShift_b_sixHoursTwo());

        carding.setActual_producation(carding.getTotal_shift_prod_b() + carding.getTotal_shift_prod_a());

        carding.setEfficiency((carding.getActual_producation() / carding.getMachineefficencykgcardperday()) * 100);
        carding.setTarget_prod_variance_kg(carding.getMachineefficencykgcardperday() - carding.getActual_producation());
        carding.setTarget_prod_variance(((carding.getActual_producation() - carding.getMachineefficencykgcardperday())
                / carding.getMachineefficencykgcardperday()) * 100);
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
            Carding carding = cardingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            CardingRest cardingRest = mapPersitenceToRestmodel(carding);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursOne":
                        cardingRest.setShift_b_sixHoursOne(values);
                }
            });
            mapRestModelToPersistenceShiftBOne(carding, cardingRest);
            cardingRepository.save(carding);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftBOne(Carding carding, CardingRest cardingRest) {
        carding.setMachineId(cardingRest.getId());
        carding.setShift_b_sixHoursOne(cardingRest.getShift_b_sixHoursOne());
        carding.setAvervg_difference_b_sixHoursOne((carding.getShift_b_sixHoursOne() - carding.getMachineefficencykgcardpersixhours()
        ) / carding.getMachineefficencykgcardpersixhours() * 100);
        carding.setTotal_shift_prod_a(carding.getShift_a_sixHoursOne() + carding.getShift_a_sixHoursTwo());
        carding.setTotal_shift_prod_b(carding.getShift_b_sixHoursOne() + carding.getShift_b_sixHoursTwo());

        carding.setActual_producation(carding.getTotal_shift_prod_b() + carding.getTotal_shift_prod_a());

        carding.setEfficiency((carding.getActual_producation() / carding.getMachineefficencykgcardperday()) * 100);
        carding.setTarget_prod_variance_kg(carding.getMachineefficencykgcardperday() - carding.getActual_producation());
        carding.setTarget_prod_variance(((carding.getActual_producation() - carding.getMachineefficencykgcardperday())
                / carding.getMachineefficencykgcardperday()) * 100);
    }

    @PatchMapping("/updateShiftBTwo/{id}")
    public ResponseEntity<?> UpdateShiftBTwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("02:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("08:20:00 AM")))
        ) {
            Carding carding = cardingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            CardingRest cardingRest = mapPersitenceToRestmodel(carding);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursTwo":
                        cardingRest.setShift_b_sixHoursTwo(values);
                }
            });
            mapRestModelToPersistenceShiftBTwo(carding, cardingRest);
            cardingRepository.save(carding);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftBTwo(Carding carding, CardingRest cardingRest) {
        carding.setMachineId(cardingRest.getId());
        carding.setShift_b_sixHoursTwo(cardingRest.getShift_b_sixHoursTwo());
        carding.setAvervg_difference_b_sixHoursTwo((carding.getShift_b_sixHoursTwo() - carding.getMachineefficencykgcardpersixhours()
        ) / carding.getMachineefficencykgcardpersixhours() * 100);
        carding.setTotal_shift_prod_a(carding.getShift_a_sixHoursOne() + carding.getShift_a_sixHoursTwo());
        carding.setTotal_shift_prod_b(carding.getShift_b_sixHoursOne() + carding.getShift_b_sixHoursTwo());

        carding.setActual_producation(carding.getTotal_shift_prod_b() + carding.getTotal_shift_prod_a());

        carding.setEfficiency((carding.getActual_producation() / carding.getMachineefficencykgcardperday()) * 100);
        carding.setTarget_prod_variance_kg(carding.getMachineefficencykgcardperday() - carding.getActual_producation());
        carding.setTarget_prod_variance(((carding.getActual_producation() - carding.getMachineefficencykgcardperday())
                / carding.getMachineefficencykgcardperday()) * 100);

    }

    private CardingRest mapPersitenceToRestmodel(Carding carding) {
        CardingRest cardingRest = new CardingRest();
        cardingRest.setId(carding.getMachineId());

        // data shift A  part one Reading

        cardingRest.setShift_a_sixHoursOne(carding.getShift_a_sixHoursOne());
        cardingRest.setAvervg_difference_a_sixHoursOne(carding.getAvervg_difference_a_sixHoursOne());

        // data shift A  part second Reading

        cardingRest.setShift_a_sixHoursTwo(carding.getShift_a_sixHoursTwo());
        cardingRest.setAvervg_difference_a_sixHoursTwo(carding.getAvervg_difference_a_sixHoursTwo());
        cardingRest.setTotal_shift_prod_a(carding.getTotal_shift_prod_a());

        // data shift B  part one Reading

        cardingRest.setShift_b_sixHoursOne(carding.getShift_b_sixHoursOne());
        cardingRest.setAvervg_difference_b_sixHoursOne(carding.getAvervg_difference_b_sixHoursOne());

        // data shift B  part second Reading

        cardingRest.setShift_b_sixHoursTwo(carding.getShift_b_sixHoursTwo());
        cardingRest.setAvervg_difference_b_sixHoursTwo(carding.getAvervg_difference_b_sixHoursTwo());

        // total production Reading

        cardingRest.setActual_producation(carding.getActual_producation());
        cardingRest.setEfficiency(carding.getEfficiency());
        cardingRest.setTarget_prod_variance_kg(carding.getTarget_prod_variance_kg());
        cardingRest.setTarget_prod_variance(carding.getTarget_prod_variance());


        return cardingRest;
    }
}

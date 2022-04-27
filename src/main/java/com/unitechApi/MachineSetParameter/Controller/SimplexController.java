package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.DtoRest.SimplexRest;
import com.unitechApi.MachineSetParameter.ExcelService.SimplexExcelService;
import com.unitechApi.MachineSetParameter.model.Simplex;
import com.unitechApi.MachineSetParameter.repository.SimplexRepository;
import com.unitechApi.MachineSetParameter.service.SimplexService;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddSimplexMAchine;
import com.unitechApi.addmachine.repositroy.AddSimplexRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.TimeExtendException;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/unitech/api/v1/machine/simplex")
public class SimplexController {

    private final SimplexService simplexService;
    private final AddSimplexRepository addsimplexrepository;
    private final SimplexRepository simplexRepository;

    public SimplexController(SimplexService simplexService, AddSimplexRepository addsimplexrepository, SimplexRepository simplexRepository) {
        this.simplexService = simplexService;
        this.addsimplexrepository = addsimplexrepository;
        this.simplexRepository = simplexRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<Simplex> SaveData(@RequestBody Simplex simplex, HttpServletRequest request) {
        Simplex simpex = new Simplex();
        String msb = ServletUriComponentsBuilder.fromCurrentRequest().path(request.getContextPath()).build().toUriString();
        return ResponseEntity.status(HttpStatus.CREATED).body(simplexService.SaveData(simplex));//HttpStatus.CREATED.value(simplexService.SaveData(simplex));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Simplex>> findByid(@PathVariable Long id) {
        return ResponseEntity.ok(simplexService.FindByData(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(simplexService.ViewData());

    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        simplexService.DeleteReading(id);
    }

    @PatchMapping("/update")
    public ResponseEntity<Simplex> UpdateData(@PathVariable Long id, Map<Object, Object> fields) {
        Optional<Simplex> simplex = simplexRepository.findById(id);
        if (simplex.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Simplex.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, simplex.get(), value);
            });
            Simplex saveuser = simplexRepository.save(simplex.get());
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
        Page<Simplex> pagecontent = simplexService.FindData(start, end, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(pagecontent, pagination), HttpStatus.OK);
    }


    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start, @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<Simplex> bloowRooms = simplexService.FindBySingleDate(start, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(bloowRooms, pagination), HttpStatus.OK);
    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportToExcel(@RequestParam Date start,
                              @RequestParam Date end, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=SimplexData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Simplex> ListData = simplexService.ExcelDateToDateReport(start, end);
        ListData.forEach(carding -> System.out.println(carding));

        SimplexExcelService c = new SimplexExcelService(ListData);
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
        String headerValue = "attachment; filename=SimplexData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Simplex> ListData = simplexService.ExcelDateToPerDateReport(start);
        ListData.forEach(carding -> System.out.println(carding));

        SimplexExcelService c = new SimplexExcelService(ListData);
        c.export(response);

    }

    @PutMapping("/{s_a_id}/update/{s_id}")
    public Simplex updateid(@PathVariable Long s_a_id, @PathVariable Long s_id) {

        AddSimplexMAchine addSimplexMAchine = addsimplexrepository.findById(s_a_id).get();
        Simplex simplex = simplexRepository.findById(s_id).get();
        simplex.idupdate(addSimplexMAchine);
        return simplexRepository.save(simplex);

    }

    @PatchMapping("/updateshiftAone/{id}")
    public ResponseEntity<?> UpdateReadingShiftAone(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timePattern = "hh:mm:ss a";
        DateTimeFormatter TimeDataFormatter = DateTimeFormatter.ofPattern(timePattern);
        if (LocalTime.now().isAfter(LocalTime.from(TimeDataFormatter.parse("08:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(TimeDataFormatter.parse("03:20:00 PM")))) {
            Simplex simplex = simplexRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            SimplexRest simplexRest = mapPersistenceRestModel(simplex);
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_sixHoursOne":
                        simplexRest.setShift_a_sixHoursOne(value);
                }
            });
            mapRestModelToPersistenceShiftAOne(simplexRest, simplex);
            simplexRepository.save(simplex);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern))), HttpStatus.OK);
    }


    @PatchMapping("/updateshiftATwo/{id}")
    public ResponseEntity<?> UpdateReadingShiftATwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timePattern = "hh:mm:ss a";
        DateTimeFormatter TimeDataFormatter = DateTimeFormatter.ofPattern(timePattern);
        if (LocalTime.now().isAfter(LocalTime.from(TimeDataFormatter.parse("02:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(TimeDataFormatter.parse("08:20:00 PM")))) {
            Simplex simplex = simplexRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            SimplexRest simplexRest = mapPersistenceRestModel(simplex);
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_sixHoursTwo":
                        simplexRest.setShift_a_sixHoursTwo(value);
                }
            });
            mapRestModelToPersistenceShiftATwo(simplexRest, simplex);
            simplexRepository.save(simplex);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern))), HttpStatus.OK);
    }


    @PatchMapping("/updateshiftBOne/{id}")
    public ResponseEntity<?> UpdateReadingShiftBOne(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timePattern = "hh:mm:ss a";
        DateTimeFormatter TimeDataFormatter = DateTimeFormatter.ofPattern(timePattern);
        if ((LocalTime.now().isAfter(LocalTime.from(TimeDataFormatter.parse("08:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(TimeDataFormatter.parse("11:59:59 PM"))))
                || (LocalTime.now().isAfter(LocalTime.from(TimeDataFormatter.parse("00:00:00 AM")))
                && LocalTime.now().isBefore(LocalTime.from(TimeDataFormatter.parse("02:20:00 AM"))))
        ) {
            Simplex simplex = simplexRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            SimplexRest simplexRest = mapPersistenceRestModel(simplex);
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_sixHoursOne":
                        simplexRest.setShift_b_sixHoursOne(value);
                }
            });
            mapRestModelToPersistenceShiftBOne(simplexRest, simplex);
            simplexRepository.save(simplex);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern))), HttpStatus.OK);
    }


    @PatchMapping("/updateshiftBTwo/{id}")
    public ResponseEntity<?> UpdateReadingShiftBTwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timePattern = "hh:mm:ss a";
        DateTimeFormatter TimeDataFormatter = DateTimeFormatter.ofPattern(timePattern);
        if (LocalTime.now().isAfter(LocalTime.from(TimeDataFormatter.parse("02:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(TimeDataFormatter.parse("08:20:00 AM")))) {
            Simplex simplex = simplexRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            SimplexRest simplexRest = mapPersistenceRestModel(simplex);
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_sixHoursTwo":
                        simplexRest.setShift_b_sixHoursTwo(value);
                }
            });
            mapRestModelToPersistenceShiftBtwo(simplex, simplexRest);
            simplexRepository.save(simplex);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftAOne(SimplexRest simplexRest, Simplex simplex) {
        simplex.setMachineId(simplexRest.getId());
        simplex.setShift_a_sixHoursOne(simplexRest.getShift_a_sixHoursOne());
        simplex.setAvervg_difference_a_sixHoursOne((simplex.getShift_a_sixHoursOne() - simplex.getConversionTo6HoursMachineShiftKgs()
        ) / simplex.getConversionTo6HoursMachineShiftKgs() * 100);
        simplex.setTotal_shift_prod_a(simplex.getShift_a_sixHoursOne() + simplex.getShift_a_sixHoursTwo());
        simplex.setTotal_shift_prod_b(simplex.getShift_b_sixHoursOne() + simplex.getShift_b_sixHoursTwo());

        simplex.setActual_producation(simplex.getTotal_shift_prod_b() + simplex.getTotal_shift_prod_a());

        simplex.setEfficiency((simplex.getActual_producation() / simplex.getConversionTo24HoursMachineShiftKgs()) * 100);
        simplex.setTarget_prod_variance_kg(simplex.getConversionTo24HoursMachineShiftKgs() - simplex.getActual_producation());
        simplex.setTarget_prod_variance(((simplex.getActual_producation() -
                simplex.getConversionTo24HoursMachineShiftKgs())
                / simplex.getConversionTo24HoursMachineShiftKgs()) * 100);
    }

    private void mapRestModelToPersistenceShiftATwo(SimplexRest simplexRest, Simplex simplex) {
        simplex.setMachineId(simplexRest.getId());
        simplex.setShift_a_sixHoursTwo(simplexRest.getShift_a_sixHoursTwo());
        simplex.setAvervg_difference_a_sixHoursTwo((simplex.getShift_a_sixHoursTwo() - simplex.getConversionTo6HoursMachineShiftKgs()
        ) / simplex.getConversionTo6HoursMachineShiftKgs() * 100);
        simplex.setTotal_shift_prod_a(simplex.getShift_a_sixHoursOne() + simplex.getShift_a_sixHoursTwo());
        simplex.setTotal_shift_prod_b(simplex.getShift_b_sixHoursOne() + simplex.getShift_b_sixHoursTwo());

        simplex.setActual_producation(simplex.getTotal_shift_prod_b() + simplex.getTotal_shift_prod_a());

        simplex.setEfficiency((simplex.getActual_producation() / simplex.getConversionTo24HoursMachineShiftKgs()) * 100);
        simplex.setTarget_prod_variance_kg(simplex.getConversionTo24HoursMachineShiftKgs() - simplex.getActual_producation());
        simplex.setTarget_prod_variance(((simplex.getActual_producation() -
                simplex.getConversionTo24HoursMachineShiftKgs())
                / simplex.getConversionTo24HoursMachineShiftKgs()) * 100);
    }

    private void mapRestModelToPersistenceShiftBOne(SimplexRest simplexRest, Simplex simplex) {
        simplex.setMachineId(simplexRest.getId());
        simplex.setShift_b_sixHoursOne(simplexRest.getShift_b_sixHoursOne());
        simplex.setAvervg_difference_b_sixHoursOne((simplex.getShift_b_sixHoursOne() - simplex.getConversionTo6HoursMachineShiftKgs()) /
                simplex.getConversionTo6HoursMachineShiftKgs() * 100);
        simplex.setTotal_shift_prod_a(simplex.getShift_a_sixHoursOne() + simplex.getShift_a_sixHoursTwo());
        simplex.setTotal_shift_prod_b(simplex.getShift_b_sixHoursOne() + simplex.getShift_b_sixHoursTwo());

        simplex.setActual_producation(simplex.getTotal_shift_prod_b() + simplex.getTotal_shift_prod_a());

        simplex.setEfficiency((simplex.getActual_producation() / simplex.getConversionTo24HoursMachineShiftKgs()) * 100);
        simplex.setTarget_prod_variance_kg(simplex.getConversionTo24HoursMachineShiftKgs() - simplex.getActual_producation());
        simplex.setTarget_prod_variance(((simplex.getActual_producation() -
                simplex.getConversionTo24HoursMachineShiftKgs())
                / simplex.getConversionTo24HoursMachineShiftKgs()) * 100);
    }

    private void mapRestModelToPersistenceShiftBtwo(Simplex simplex, SimplexRest simplexRest) {
        simplex.setMachineId(simplexRest.getId());
        simplex.setShift_b_sixHoursTwo(simplexRest.getShift_b_sixHoursTwo());
        simplex.setAvervg_difference_b_sixHoursTwo((simplex.getShift_b_sixHoursTwo() - simplex.getConversionTo6HoursMachineShiftKgs()) /
                simplex.getConversionTo6HoursMachineShiftKgs() * 100);
        simplex.setTotal_shift_prod_a(simplex.getShift_a_sixHoursOne() + simplex.getShift_a_sixHoursTwo());
        simplex.setTotal_shift_prod_b(simplex.getShift_b_sixHoursOne() + simplex.getShift_b_sixHoursTwo());

        simplex.setActual_producation(simplex.getTotal_shift_prod_b() + simplex.getTotal_shift_prod_a());

        simplex.setEfficiency((simplex.getActual_producation() / simplex.getConversionTo24HoursMachineShiftKgs()) * 100);
        simplex.setTarget_prod_variance_kg(simplex.getConversionTo24HoursMachineShiftKgs() - simplex.getActual_producation());
        simplex.setTarget_prod_variance(((simplex.getActual_producation() -
                simplex.getConversionTo24HoursMachineShiftKgs())
                / simplex.getConversionTo24HoursMachineShiftKgs()) * 100);
    }

    private SimplexRest mapPersistenceRestModel(Simplex simplex) {
        SimplexRest simplexRest = new SimplexRest();
        simplexRest.setId(simplex.getMachineId());

        //Shift A ANd Time  8:00 Am to 2:00 pm

        simplexRest.setShift_a_sixHoursOne(simplex.getShift_a_sixHoursOne());
        simplexRest.setAvervg_difference_a_sixHoursOne(simplex.getAvervg_difference_a_sixHoursOne());

        //Shift A ANd Time  02:00 pm to 8:00 pm

        simplexRest.setShift_a_sixHoursTwo(simplex.getShift_a_sixHoursTwo());
        simplexRest.setAvervg_difference_a_sixHoursTwo(simplex.getAvervg_difference_a_sixHoursTwo());

        // total production Shift A

        simplexRest.setTotal_shift_prod_a(simplex.getTotal_shift_prod_a());

        //Shift B ANd Time  08:00 pm to 02:00 am

        simplexRest.setShift_b_sixHoursOne(simplex.getShift_b_sixHoursOne());
        simplexRest.setAvervg_difference_b_sixHoursOne(simplex.getAvervg_difference_b_sixHoursOne());

        //Shift B ANd Time  08:00 pm to 08:00 am

        simplexRest.setShift_b_sixHoursTwo(simplex.getShift_b_sixHoursTwo());
        simplexRest.setAvervg_difference_b_sixHoursTwo(simplex.getAvervg_difference_b_sixHoursTwo());

        //  total production Shift A

        simplexRest.setTotal_shift_prod_b(simplex.getTotal_shift_prod_b());

        // calculation total production and efficiency and total production variance kg and simple

        simplexRest.setActual_producation(simplex.getActual_producation());
        simplexRest.setEfficiency(simplex.getEfficiency());
        simplexRest.setTarget_prod_variance_kg(simplex.getTarget_prod_variance_kg());
        simplexRest.setTarget_prod_variance(simplex.getTarget_prod_variance());


        return simplexRest;
    }

}

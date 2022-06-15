package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.DtoRest.DrawFramesPerKgRest;
import com.unitechApi.MachineSetParameter.ExcelService.DrawFramesKgExcelService;
import com.unitechApi.MachineSetParameter.model.Drawframesperkg;
import com.unitechApi.MachineSetParameter.repository.DrawFramesRepository;
import com.unitechApi.MachineSetParameter.service.DrawframesService;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddDrawFramesMachine;
import com.unitechApi.addmachine.repositroy.AddDrawFramesRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.TimeExtendException;
import com.unitechApi.exception.ExceptionService.UserNotFound;
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
@RequestMapping("/unitech/api/v1/machine/drawframes")
public class DrawFramesController {

    private final DrawframesService drawframesService;
    private final DrawFramesRepository drawFramesRepository;
    private final AddDrawFramesRepository addDrawFramesRepository;

    public DrawFramesController(DrawframesService drawframesService, DrawFramesRepository drawFramesRepository, AddDrawFramesRepository addDrawFramesRepository) {
        this.drawframesService = drawframesService;
        this.drawFramesRepository = drawFramesRepository;
        this.addDrawFramesRepository = addDrawFramesRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<Drawframesperkg> SaveData(@RequestBody Drawframesperkg drawframesperkg) {
        return ResponseEntity.ok(drawframesService.SaveData(drawframesperkg));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Drawframesperkg>> findByid(@PathVariable Long id) {

        return ResponseEntity.ok(drawframesService.FindByData(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(drawframesService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        drawframesService.DeleteReading(id);
    }

    @PatchMapping("/update")
    public ResponseEntity<Drawframesperkg> UpdateData(@PathVariable Long id, Map<Object, Object> fields) {
        Optional<Drawframesperkg> drawframes = drawFramesRepository.findById(id);
        if (drawframes.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Drawframesperkg.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, drawframes.get(), value);
            });
            Drawframesperkg saveuser = drawFramesRepository.save(drawframes.get());
        } else {
            throw new UserNotFound("User Not Found " + id);
        }
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<?> getRecordByCreatedDate(@RequestParam Date start, @RequestParam Date end) {


        List<Drawframesperkg> pagecontent = drawframesService.FindByDate(start, end);
        return new ResponseEntity<>(PageResponse.SuccessResponse(pagecontent), HttpStatus.OK);
    }


    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start) {

        List<Drawframesperkg> bloowRooms = drawframesService.FindBySingleDate(start);
        return new ResponseEntity<>(PageResponse.SuccessResponse(bloowRooms), HttpStatus.OK);
    }

    @PutMapping("/{d_a_id}/update/{d_id}")
    public Drawframesperkg idUpdateDrawframes(@PathVariable Long d_a_id, @PathVariable Long d_id) {
        AddDrawFramesMachine addDrawFramesMachine = addDrawFramesRepository.findById(d_a_id).get();
        Drawframesperkg drawframesperkg = drawFramesRepository.findById(d_id).get();
        drawframesperkg.idupdatedrawframes(addDrawFramesMachine);
        return drawFramesRepository.save(drawframesperkg);

    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportToExcel(@RequestParam Date start,
                              @RequestParam Date end, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=drawFramesData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Drawframesperkg> ListData = drawframesService.ExcelDateToDateReport(start, end);
        ListData.forEach(carding -> System.out.println(carding));

        DrawFramesKgExcelService c = new DrawFramesKgExcelService(ListData);
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
        String headerValue = "attachment; filename=drawFramesData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Drawframesperkg> ListData = drawframesService.ExcelDateToPerDateReport(start);
        ListData.forEach(carding -> System.out.println(carding));

        DrawFramesKgExcelService c = new DrawFramesKgExcelService(ListData);
        c.export(response);
    }

    @PatchMapping("/updateShiftAOne/{id}")
    public ResponseEntity<?> UpdateShiftAOne(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("08:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("02:20:00 PM")))
        ) {
            Drawframesperkg drawframesPerkg = drawFramesRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            DrawFramesPerKgRest drawframePerkgRest = mapPersitenceToRestmodel(drawframesPerkg);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursOne":
                        drawframePerkgRest.setShift_a_sixHoursOne(values);
                }
            });
            mapRestModelToPersistenceShiftAOne(drawframesPerkg, drawframePerkgRest);
            drawFramesRepository.save(drawframesPerkg);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateShiftATwo/{id}")
    public ResponseEntity<?> UpdateShiftATwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("02:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("08:20:00 PM")))
        ) {
            Drawframesperkg drawframesperkg = drawFramesRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            DrawFramesPerKgRest drawframePerkgRest = mapPersitenceToRestmodel(drawframesperkg);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursTwo":
                        drawframePerkgRest.setShift_a_sixHoursTwo(values);
                }
            });
            mapRestModelToPersistenceShiftATwo(drawframesperkg, drawframePerkgRest);
            drawFramesRepository.save(drawframesperkg);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
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
            Drawframesperkg drawframesperkg = drawFramesRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            DrawFramesPerKgRest drawframePerkgRest = mapPersitenceToRestmodel(drawframesperkg);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursOne":
                        drawframePerkgRest.setShift_b_sixHoursOne(values);
                }
            });
            mapRestModelToPersistenceShiftBOne(drawframesperkg, drawframePerkgRest);
            drawFramesRepository.save(drawframesperkg);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }


    @PatchMapping("/updateShiftBTwo/{id}")
    public ResponseEntity<?> UpdateShiftBTwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("02:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("08:20:00 AM")))
        ) {
            Drawframesperkg drawframesperkg = drawFramesRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            DrawFramesPerKgRest drawframePerkgRest = mapPersitenceToRestmodel(drawframesperkg);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursTwo":
                        drawframePerkgRest.setShift_b_sixHoursTwo(values);
                }
            });
            mapRestModelToPersistenceShiftBTwo(drawframesperkg, drawframePerkgRest);
            drawFramesRepository.save(drawframesperkg);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftBOne(Drawframesperkg drawframesperkg, DrawFramesPerKgRest drawframePerkgRest) {
        drawframesperkg.setMachineId(drawframePerkgRest.getId());
        drawframesperkg.setShift_b_sixHoursOne(drawframePerkgRest.getShift_b_sixHoursOne());
        drawframesperkg.setAvervg_difference_b_sixHoursOne((drawframesperkg.getShift_b_sixHoursOne() - drawframesperkg.getMachineefficencykgdfper6hours()
        ) / drawframesperkg.getMachineefficencykgdfper6hours() * 100);
        drawframesperkg.setTotal_shift_prod_a(drawframesperkg.getShift_a_sixHoursOne() + drawframesperkg.getShift_a_sixHoursTwo());
        drawframesperkg.setTotal_shift_prod_b(drawframesperkg.getShift_b_sixHoursOne() + drawframesperkg.getShift_b_sixHoursTwo());

        drawframesperkg.setActual_producation(drawframesperkg.getTotal_shift_prod_b() + drawframesperkg.getTotal_shift_prod_a());

        drawframesperkg.setEfficiency((drawframesperkg.getActual_producation() /
                drawframesperkg.getProductiononratekgdfper8hour() * 3) * 100);
        drawframesperkg.setTarget_prod_variance_kg(drawframesperkg.getMachineefficencykgdfperday() - drawframesperkg.getActual_producation());
        drawframesperkg.setTarget_prod_variance(((drawframesperkg.getActual_producation() - drawframesperkg.getMachineefficencykgdfperday())
                / drawframesperkg.getMachineefficencykgdfperday()) * 100);

    }

    private void mapRestModelToPersistenceShiftBTwo(Drawframesperkg drawframesPerkg, DrawFramesPerKgRest drawframePerkgRest) {
        drawframesPerkg.setMachineId(drawframePerkgRest.getId());
        drawframesPerkg.setShift_b_sixHoursTwo(drawframePerkgRest.getShift_b_sixHoursTwo());
        drawframesPerkg.setAvervg_difference_b_sixHoursTwo((drawframesPerkg.getShift_b_sixHoursTwo() - drawframesPerkg.getMachineefficencykgdfper6hours()
        ) / drawframesPerkg.getMachineefficencykgdfper6hours() * 100);
        drawframesPerkg.setTotal_shift_prod_a(drawframesPerkg.getShift_a_sixHoursOne() + drawframesPerkg.getShift_a_sixHoursTwo());
        drawframesPerkg.setTotal_shift_prod_b(drawframesPerkg.getShift_b_sixHoursOne() + drawframesPerkg.getShift_b_sixHoursTwo());

        drawframesPerkg.setActual_producation(drawframesPerkg.getTotal_shift_prod_b() + drawframesPerkg.getTotal_shift_prod_a());

        drawframesPerkg.setEfficiency((drawframesPerkg.getActual_producation() /
                drawframesPerkg.getProductiononratekgdfper8hour() * 3) * 100);
        drawframesPerkg.setTarget_prod_variance_kg(drawframesPerkg.getMachineefficencykgdfperday() - drawframesPerkg.getActual_producation());
        drawframesPerkg.setTarget_prod_variance(((drawframesPerkg.getActual_producation() - drawframesPerkg.getMachineefficencykgdfperday())
                / drawframesPerkg.getMachineefficencykgdfperday()) * 100);
    }

    private void mapRestModelToPersistenceShiftAOne(Drawframesperkg drawframesPerkg, DrawFramesPerKgRest drawframePerkgRest) {
        drawframesPerkg.setMachineId(drawframePerkgRest.getId());
        drawframesPerkg.setShift_a_sixHoursOne(drawframePerkgRest.getShift_a_sixHoursOne());
        drawframesPerkg.setAvervg_difference_a_sixHoursOne((drawframesPerkg.getShift_a_sixHoursOne() - drawframesPerkg.getMachineefficencykgdfper6hours()
        ) / drawframesPerkg.getMachineefficencykgdfper6hours() * 100);
        drawframesPerkg.setTotal_shift_prod_a(drawframesPerkg.getShift_a_sixHoursOne() + drawframesPerkg.getShift_a_sixHoursTwo());
        drawframesPerkg.setTotal_shift_prod_b(drawframesPerkg.getShift_b_sixHoursOne() + drawframesPerkg.getShift_b_sixHoursTwo());

        drawframesPerkg.setActual_producation(drawframesPerkg.getTotal_shift_prod_b() + drawframesPerkg.getTotal_shift_prod_a());

        drawframesPerkg.setEfficiency((drawframesPerkg.getActual_producation() /
                drawframesPerkg.getProductiononratekgdfper8hour() * 3) * 100);
        drawframesPerkg.setTarget_prod_variance_kg(drawframesPerkg.getMachineefficencykgdfperday() - drawframesPerkg.getActual_producation());
        drawframesPerkg.setTarget_prod_variance(((drawframesPerkg.getActual_producation() - drawframesPerkg.getMachineefficencykgdfperday())
                / drawframesPerkg.getMachineefficencykgdfperday()) * 100);
    }

    private void mapRestModelToPersistenceShiftATwo(Drawframesperkg drawframesperkg, DrawFramesPerKgRest drawframePerkgRest) {
        drawframesperkg.setMachineId(drawframePerkgRest.getId());
        drawframesperkg.setShift_a_sixHoursTwo(drawframePerkgRest.getShift_a_sixHoursTwo());
        drawframesperkg.setAvervg_difference_a_sixHoursTwo((drawframesperkg.getShift_a_sixHoursTwo() - drawframesperkg.getMachineefficencykgdfper6hours()) /
                drawframesperkg.getMachineefficencykgdfper6hours() * 100);
        drawframesperkg.setTotal_shift_prod_a(drawframesperkg.getShift_a_sixHoursOne() + drawframesperkg.getShift_a_sixHoursTwo());
        drawframesperkg.setTotal_shift_prod_b(drawframesperkg.getShift_b_sixHoursOne() + drawframesperkg.getShift_b_sixHoursTwo());

        drawframesperkg.setActual_producation(drawframesperkg.getTotal_shift_prod_b() + drawframesperkg.getTotal_shift_prod_a());

        drawframesperkg.setEfficiency((drawframesperkg.getActual_producation() /
                drawframesperkg.getProductiononratekgdfper8hour() * 3) * 100);
        drawframesperkg.setTarget_prod_variance_kg(drawframesperkg.getMachineefficencykgdfperday() - drawframesperkg.getActual_producation());
        drawframesperkg.setTarget_prod_variance(((drawframesperkg.getActual_producation() - drawframesperkg.getMachineefficencykgdfperday())
                / drawframesperkg.getMachineefficencykgdfperday()) * 100);
    }

    private DrawFramesPerKgRest mapPersitenceToRestmodel(Drawframesperkg drawframesPerkg) {
        DrawFramesPerKgRest drawframePerkgRest = new DrawFramesPerKgRest();
        drawframePerkgRest.setId(drawframesPerkg.getMachineId());

        // data shift A  part one Reading

        drawframePerkgRest.setShift_a_sixHoursOne(drawframesPerkg.getShift_a_sixHoursOne());
        drawframePerkgRest.setAvervg_difference_a_sixHoursOne(drawframesPerkg.getAvervg_difference_a_sixHoursOne());

        // data shift A  part second Reading

        drawframePerkgRest.setShift_a_sixHoursTwo(drawframesPerkg.getShift_a_sixHoursTwo());
        drawframePerkgRest.setAvervg_difference_a_sixHoursTwo(drawframesPerkg.getAvervg_difference_a_sixHoursTwo());
        drawframePerkgRest.setTotal_shift_prod_a(drawframesPerkg.getTotal_shift_prod_a());

        // data shift B  part one Reading

        drawframePerkgRest.setShift_b_sixHoursOne(drawframesPerkg.getShift_b_sixHoursOne());
        drawframePerkgRest.setAvervg_difference_b_sixHoursOne(drawframesPerkg.getAvervg_difference_b_sixHoursOne());

        // data shift B  part second Reading

        drawframePerkgRest.setShift_b_sixHoursTwo(drawframesPerkg.getShift_b_sixHoursTwo());
        drawframePerkgRest.setAvervg_difference_b_sixHoursTwo(drawframesPerkg.getAvervg_difference_b_sixHoursTwo());

        // total production Reading

        drawframePerkgRest.setActual_producation(drawframesPerkg.getActual_producation());
        drawframePerkgRest.setEfficiency(drawframesPerkg.getEfficiency());
        drawframePerkgRest.setTarget_prod_variance_kg(drawframesPerkg.getTarget_prod_variance_kg());
        drawframePerkgRest.setTarget_prod_variance(drawframesPerkg.getTarget_prod_variance());

        return drawframePerkgRest;
    }
}

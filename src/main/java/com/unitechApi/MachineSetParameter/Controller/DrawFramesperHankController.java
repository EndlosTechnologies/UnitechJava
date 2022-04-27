package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.DtoRest.DrawFramesPerHankRest;
import com.unitechApi.MachineSetParameter.ExcelService.DrawFramesPerHankExcelService;
import com.unitechApi.MachineSetParameter.model.DrawFramesPerHank;
import com.unitechApi.MachineSetParameter.repository.DrawFramesPerHankRepository;
import com.unitechApi.MachineSetParameter.service.DrawFramesHankService;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddDrawFramesMachine;
import com.unitechApi.addmachine.repositroy.AddDrawFramesRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.TimeExtendException;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@RequestMapping("/unitech/api/v1/machine/drawframesperhank")
@ToString
public class DrawFramesperHankController {

    private final DrawFramesPerHankRepository drawFramesPerHankRepository;
    private final AddDrawFramesRepository addDrawFramesRepository;
    private final DrawFramesHankService drawFramesHankService;

    public DrawFramesperHankController(DrawFramesPerHankRepository drawFramesPerHankRepository, AddDrawFramesRepository addDrawFramesRepository, DrawFramesHankService drawFramesHankService) {
        this.drawFramesPerHankRepository = drawFramesPerHankRepository;
        this.addDrawFramesRepository = addDrawFramesRepository;
        this.drawFramesHankService = drawFramesHankService;
    }

    @PostMapping("/save")
    public ResponseEntity<DrawFramesPerHank> SaveData(@RequestBody DrawFramesPerHank drawFramesPerHank) {
        return new ResponseEntity<>(drawFramesHankService.save(drawFramesPerHank), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DrawFramesPerHank>> findByid(@PathVariable Long id) {
        return ResponseEntity.ok(drawFramesPerHankRepository.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> FIndyDate(@RequestParam Date start, @RequestParam Date end,
                                       @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<?> drawframes = drawFramesHankService.FindByDate(start, end, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(drawframes, pagination), HttpStatus.OK);
    }

    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start, @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<DrawFramesPerHank> bloowRooms = drawFramesHankService.FindBySingleDate(start, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(bloowRooms, pagination), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(drawFramesPerHankRepository.findAll());
    }

    @PutMapping("/{dh_a_id}/update/{d_id}")
    public DrawFramesPerHank idUpdateDrawframes(@PathVariable Long dh_a_id, @PathVariable Long d_id) {
        AddDrawFramesMachine addDrawFramesMachine = addDrawFramesRepository.findById(dh_a_id).get();
        DrawFramesPerHank drawFramesPerHank = drawFramesPerHankRepository.findById(d_id).get();
        drawFramesPerHank.idupdatedrawframes(addDrawFramesMachine);
        return drawFramesPerHankRepository.save(drawFramesPerHank);

    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportToExcel(@RequestParam Date start,
                              @RequestParam Date end, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=drawFramesHankData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<DrawFramesPerHank> ListData = drawFramesHankService.ExcelDateToDateReport(start, end);
        ListData.forEach(carding -> System.out.println(carding));

        DrawFramesPerHankExcelService c = new DrawFramesPerHankExcelService(ListData);
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
        String headerValue = "attachment; filename=drawFramesHankData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<DrawFramesPerHank> ListData = drawFramesHankService.ExcelDateToPerDateReport(start);
        ListData.forEach(carding -> System.out.println(carding));

        DrawFramesPerHankExcelService c = new DrawFramesPerHankExcelService(ListData);
        c.export(response);
    }

    @PatchMapping("/updateShiftAOne/{id}")
    public ResponseEntity<?> UpdateShiftAOne(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("08:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("02:20:00 PM")))
        ) {
            DrawFramesPerHank drawFramesPerHank = drawFramesPerHankRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            DrawFramesPerHankRest drawFramesPerHankRest = mapPersitenceToRestmodel(drawFramesPerHank);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursOne":
                        drawFramesPerHankRest.setShift_a_sixHoursOne(values);
                }
            });
            mapRestModelToPersistenceShiftAOne(drawFramesPerHank, drawFramesPerHankRest);
            drawFramesPerHankRepository.save(drawFramesPerHank);
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
            DrawFramesPerHank drawFramesPerHank = drawFramesPerHankRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            DrawFramesPerHankRest drawFramesPerHankRest = mapPersitenceToRestmodel(drawFramesPerHank);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursTwo":
                        drawFramesPerHankRest.setShift_a_sixHoursTwo(values);
                }
            });
            mapRestModelToPersistenceShiftATwo(drawFramesPerHank, drawFramesPerHankRest);
            drawFramesPerHankRepository.save(drawFramesPerHank);
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
            DrawFramesPerHank drawFramesPerHank = drawFramesPerHankRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            DrawFramesPerHankRest drawFramesPerHankRest = mapPersitenceToRestmodel(drawFramesPerHank);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursOne":
                        drawFramesPerHankRest.setShift_b_sixHoursOne(values);
                }
            });
            mapRestModelToPersistenceShiftBOne(drawFramesPerHank, drawFramesPerHankRest);
            drawFramesPerHankRepository.save(drawFramesPerHank);
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
            DrawFramesPerHank drawFramesPerHank = drawFramesPerHankRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            DrawFramesPerHankRest drawFramesPerHankRest = mapPersitenceToRestmodel(drawFramesPerHank);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursTwo":
                        drawFramesPerHankRest.setShift_b_sixHoursTwo(values);
                }
            });
            mapRestModelToPersistenceShiftBTwo(drawFramesPerHank, drawFramesPerHankRest);
            drawFramesPerHankRepository.save(drawFramesPerHank);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftBTwo(DrawFramesPerHank drawFramesPerHank, DrawFramesPerHankRest drawFramesPerHankRest) {
        drawFramesPerHank.setMachineId(drawFramesPerHankRest.getId());
        drawFramesPerHank.setShift_b_sixHoursTwo(drawFramesPerHankRest.getShift_b_sixHoursTwo());
        drawFramesPerHank.setAvervg_difference_b_sixHoursTwo((drawFramesPerHank.getShift_b_sixHoursTwo() - drawFramesPerHank.getMachineefficencykgdfper6hours()
        ) / drawFramesPerHank.getMachineefficencykgdfper6hours() * 100);
        drawFramesPerHank.setTotal_shift_prod_a(drawFramesPerHank.getShift_a_sixHoursOne() + drawFramesPerHank.getShift_a_sixHoursTwo());
        drawFramesPerHank.setTotal_shift_prod_b(drawFramesPerHank.getShift_b_sixHoursOne() + drawFramesPerHank.getShift_b_sixHoursTwo());

        drawFramesPerHank.setActual_producation(drawFramesPerHank.getTotal_shift_prod_b() + drawFramesPerHank.getTotal_shift_prod_a());

        drawFramesPerHank.setEfficiency((drawFramesPerHank.getActual_producation() / drawFramesPerHank.getMachineefficencykgdfperday()) * 100);
        drawFramesPerHank.setTarget_prod_variance_kg(drawFramesPerHank.getMachineefficencykgdfperday() - drawFramesPerHank.getActual_producation());
        drawFramesPerHank.setTarget_prod_variance(((drawFramesPerHank.getActual_producation() - drawFramesPerHank.getMachineefficencykgdfperday())
                / drawFramesPerHank.getMachineefficencykgdfperday()) * 100);

    }

    private void mapRestModelToPersistenceShiftBOne(DrawFramesPerHank drawFramesPerHank, DrawFramesPerHankRest drawFramesPerHankRest) {
        drawFramesPerHank.setMachineId(drawFramesPerHankRest.getId());
        drawFramesPerHank.setShift_b_sixHoursOne(drawFramesPerHankRest.getShift_b_sixHoursOne());
        drawFramesPerHank.setAvervg_difference_b_sixHoursOne((drawFramesPerHank.getShift_b_sixHoursOne() - drawFramesPerHank.getMachineefficencykgdfper6hours()
        ) / drawFramesPerHank.getMachineefficencykgdfper6hours() * 100);
        drawFramesPerHank.setTotal_shift_prod_a(drawFramesPerHank.getShift_a_sixHoursOne() + drawFramesPerHank.getShift_a_sixHoursTwo());
        drawFramesPerHank.setTotal_shift_prod_b(drawFramesPerHank.getShift_b_sixHoursOne() + drawFramesPerHank.getShift_b_sixHoursTwo());

        drawFramesPerHank.setActual_producation(drawFramesPerHank.getTotal_shift_prod_b() + drawFramesPerHank.getTotal_shift_prod_a());

        drawFramesPerHank.setEfficiency((drawFramesPerHank.getActual_producation() / drawFramesPerHank.getMachineefficencykgdfperday()) * 100);
        drawFramesPerHank.setTarget_prod_variance_kg(drawFramesPerHank.getMachineefficencykgdfperday() - drawFramesPerHank.getActual_producation());
        drawFramesPerHank.setTarget_prod_variance(((drawFramesPerHank.getActual_producation() - drawFramesPerHank.getMachineefficencykgdfperday())
                / drawFramesPerHank.getMachineefficencykgdfperday()) * 100);
    }

    private void mapRestModelToPersistenceShiftATwo(DrawFramesPerHank drawFramesPerHank, DrawFramesPerHankRest drawFramesPerHankRest) {
        drawFramesPerHank.setMachineId(drawFramesPerHankRest.getId());
        drawFramesPerHank.setShift_a_sixHoursTwo(drawFramesPerHankRest.getShift_a_sixHoursTwo());
        drawFramesPerHank.setAvervg_difference_a_sixHoursTwo((drawFramesPerHank.getShift_a_sixHoursTwo() - drawFramesPerHank.getMachineefficencykgdfper6hours()) /
                drawFramesPerHank.getMachineefficencykgdfper6hours() * 100);
        drawFramesPerHank.setTotal_shift_prod_a(drawFramesPerHank.getShift_a_sixHoursOne() + drawFramesPerHank.getShift_a_sixHoursTwo());
        drawFramesPerHank.setTotal_shift_prod_a(drawFramesPerHank.getShift_a_sixHoursOne() + drawFramesPerHank.getShift_a_sixHoursTwo());
        drawFramesPerHank.setTotal_shift_prod_b(drawFramesPerHank.getShift_b_sixHoursOne() + drawFramesPerHank.getShift_b_sixHoursTwo());

        drawFramesPerHank.setActual_producation(drawFramesPerHank.getTotal_shift_prod_b() + drawFramesPerHank.getTotal_shift_prod_a());

        drawFramesPerHank.setEfficiency((drawFramesPerHank.getActual_producation() / drawFramesPerHank.getMachineefficencykgdfperday()) * 100);
        drawFramesPerHank.setTarget_prod_variance_kg(drawFramesPerHank.getMachineefficencykgdfperday() - drawFramesPerHank.getActual_producation());
        drawFramesPerHank.setTarget_prod_variance(((drawFramesPerHank.getActual_producation() - drawFramesPerHank.getMachineefficencykgdfperday())
                / drawFramesPerHank.getMachineefficencykgdfperday()) * 100);
    }


    private void mapRestModelToPersistenceShiftAOne(DrawFramesPerHank drawFramesPerHank, DrawFramesPerHankRest drawFramesPerHankRest) {
        drawFramesPerHank.setMachineId(drawFramesPerHankRest.getId());
        drawFramesPerHank.setShift_a_sixHoursOne(drawFramesPerHankRest.getShift_a_sixHoursOne());
        drawFramesPerHank.setAvervg_difference_a_sixHoursOne((drawFramesPerHank.getShift_a_sixHoursOne() - drawFramesPerHank.getMachineefficencykgdfper6hours()
        ) / drawFramesPerHank.getMachineefficencykgdfper6hours() * 100);
        drawFramesPerHank.setTotal_shift_prod_a(drawFramesPerHank.getShift_a_sixHoursOne() + drawFramesPerHank.getShift_a_sixHoursTwo());
        drawFramesPerHank.setTotal_shift_prod_b(drawFramesPerHank.getShift_b_sixHoursOne() + drawFramesPerHank.getShift_b_sixHoursTwo());

        drawFramesPerHank.setActual_producation(drawFramesPerHank.getTotal_shift_prod_b() + drawFramesPerHank.getTotal_shift_prod_a());

        drawFramesPerHank.setEfficiency((drawFramesPerHank.getActual_producation() / drawFramesPerHank.getMachineefficencykgdfperday()) * 100);
        drawFramesPerHank.setTarget_prod_variance_kg(drawFramesPerHank.getMachineefficencykgdfperday() - drawFramesPerHank.getActual_producation());
        drawFramesPerHank.setTarget_prod_variance(((drawFramesPerHank.getActual_producation() - drawFramesPerHank.getMachineefficencykgdfperday())
                / drawFramesPerHank.getMachineefficencykgdfperday()) * 100);


    }

    private DrawFramesPerHankRest mapPersitenceToRestmodel(DrawFramesPerHank drawFramesPerHank) {
        DrawFramesPerHankRest drawFramesPerHankRest = new DrawFramesPerHankRest();
        drawFramesPerHankRest.setId(drawFramesPerHank.getMachineId());

        // data shift A  part one Reading

        drawFramesPerHankRest.setShift_a_sixHoursOne(drawFramesPerHank.getShift_a_sixHoursOne());
        drawFramesPerHankRest.setAvervg_difference_a_sixHoursOne(drawFramesPerHankRest.getAvervg_difference_a_sixHoursOne());

        // data shift A  part second Reading

        drawFramesPerHankRest.setShift_a_sixHoursTwo(drawFramesPerHank.getShift_a_sixHoursTwo());
        drawFramesPerHankRest.setAvervg_difference_a_sixHoursTwo(drawFramesPerHank.getAvervg_difference_a_sixHoursTwo());
        drawFramesPerHankRest.setTotal_shift_prod_a(drawFramesPerHank.getTotal_shift_prod_a());

        // data shift B  part one Reading

        drawFramesPerHankRest.setShift_b_sixHoursOne(drawFramesPerHank.getShift_b_sixHoursOne());
        drawFramesPerHankRest.setAvervg_difference_b_sixHoursOne(drawFramesPerHank.getAvervg_difference_b_sixHoursOne());

        // data shift B  part second Reading

        drawFramesPerHankRest.setShift_b_sixHoursTwo(drawFramesPerHank.getShift_b_sixHoursTwo());
        drawFramesPerHankRest.setAvervg_difference_b_sixHoursTwo(drawFramesPerHank.getAvervg_difference_b_sixHoursTwo());

        // total production Reading

        drawFramesPerHankRest.setActual_producation(drawFramesPerHank.getActual_producation());
        drawFramesPerHankRest.setEfficiency(drawFramesPerHank.getEfficiency());
        drawFramesPerHankRest.setTarget_prod_variance_kg(drawFramesPerHank.getTarget_prod_variance_kg());
        drawFramesPerHankRest.setTarget_prod_variance(drawFramesPerHank.getTarget_prod_variance());

        return drawFramesPerHankRest;

    }
}


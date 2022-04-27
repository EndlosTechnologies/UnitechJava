package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.DtoRest.LapFormerRest;
import com.unitechApi.MachineSetParameter.ExcelService.LapFormerExCelService;
import com.unitechApi.MachineSetParameter.model.LapFormer;
import com.unitechApi.MachineSetParameter.repository.LapFormerRepository;
import com.unitechApi.MachineSetParameter.service.LapFormerService;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.TimeExtendException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/machine/lapformer")
public class LapFormerController {


    private final LapFormerService lapFormerService;
    private final LapFormerRepository lapFormerRepository;
    private static final Logger logger = LoggerFactory.getLogger(LapFormerController.class);

    public LapFormerController(LapFormerService lapFormerService, LapFormerRepository lapFormerRepository) {
        this.lapFormerService = lapFormerService;
        this.lapFormerRepository = lapFormerRepository;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<LapFormer> SaveData(@RequestBody LapFormer lapFormer) {
        return new ResponseEntity<>(lapFormerService.SaveData(lapFormer), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LapFormer> FindById(@PathVariable Long id) {
        return new ResponseEntity<>(lapFormerService.FindById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> FindByDate(@RequestParam Date start, @RequestParam Date end,
                                                          @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination1 = new Pagination(page, pagesize);
        Page<LapFormer> lapFormers = lapFormerService.DateToDateSearch(start, end, pagination1);
        return ResponseEntity.status(HttpStatus.OK).body(PageResponse.pagebleResponse(lapFormers, pagination1));

    }

    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start, @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<LapFormer> bloowRooms = lapFormerService.FindBySingleDate(start, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(bloowRooms, pagination), HttpStatus.OK);
    }

    @PutMapping("{l_a_id}/update/{l_r_id}")
    public ResponseEntity<?> UpdateId(@PathVariable Long l_a_id, @PathVariable Long l_r_id) {
        LapFormer lapFormer = lapFormerService.UpdateId(l_a_id, l_r_id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(lapFormer), HttpStatus.OK);
    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportToExcel(@RequestParam Date start,
                              @RequestParam Date end, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=LapFormerData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<LapFormer> ListData = lapFormerService.ExcelDateToDateReport(start, end);
        ListData.forEach(carding -> System.out.println(carding));

        LapFormerExCelService c = new LapFormerExCelService(ListData);
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
        String headerValue = "attachment; filename=LapFormerData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<LapFormer> ListData = lapFormerService.ExcelDateToPerDateReport(start);
        ListData.forEach(carding -> System.out.println(carding));

        LapFormerExCelService c = new LapFormerExCelService(ListData);
        c.export(response);
    }

    @PatchMapping("/updateshiftAone/{id}")
    public ResponseEntity<?> UpdateReadingShiftAone(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timePattern = "hh:mm:ss a";
        DateTimeFormatter TimeDataFormatter = DateTimeFormatter.ofPattern(timePattern);
        if (LocalTime.now().isAfter(LocalTime.from(TimeDataFormatter.parse("08:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(TimeDataFormatter.parse("03:20:00 PM")))) {
            LapFormer lapformer = lapFormerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            LapFormerRest lapFormerRest = mapPersistenceToRestModel(lapformer);
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_sixHoursOne":
                        lapFormerRest.setShift_a_sixHoursOne(value);
                }
            });
            mapRestModelToPersistenceShiftAOne(lapFormerRest, lapformer);
            lapFormerRepository.save(lapformer);
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
            LapFormer lapformer = lapFormerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            LapFormerRest lapFormerRest = mapPersistenceToRestModel(lapformer);
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_sixHoursTwo":
                        lapFormerRest.setShift_a_sixHoursTwo(value);
                }
            });
            mapRestModelToPersistenceShiftATwo(lapFormerRest, lapformer);
            lapFormerRepository.save(lapformer);
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
            LapFormer lapformer = lapFormerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            LapFormerRest lapFormerRest = mapPersistenceToRestModel(lapformer);
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_sixHoursOne":
                        lapFormerRest.setShift_b_sixHoursOne(value);
                }
            });
            mapRestModelToPersistenceShiftBOne(lapFormerRest, lapformer);
            lapFormerRepository.save(lapformer);
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
            LapFormer lapformer = lapFormerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            LapFormerRest lapFormerRest = mapPersistenceToRestModel(lapformer);
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_sixHoursTwo":
                        lapFormerRest.setShift_b_sixHoursTwo(value);
                }
            });
            mapRestModelToPersistenceShiftBtwo(lapFormerRest, lapformer);
            lapFormerRepository.save(lapformer);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftBtwo(LapFormerRest lapFormerRest, LapFormer lapformer) {
        lapformer.setMachineId(lapFormerRest.getId());
        lapformer.setShift_b_sixHoursTwo(lapFormerRest.getShift_b_sixHoursTwo());
        lapformer.setAvervg_difference_b_sixHoursTwo((lapformer.getShift_b_sixHoursTwo() - lapformer.getMachineefficencykgcardpersixhours()) /
                lapformer.getMachineefficencykgcardpersixhours() * 100);
        lapformer.setTotal_shift_prod_a(lapformer.getShift_a_sixHoursOne() + lapformer.getShift_a_sixHoursTwo());
        lapformer.setTotal_shift_prod_b(lapformer.getShift_b_sixHoursOne() + lapformer.getShift_b_sixHoursTwo());

        lapformer.setActual_producation(lapformer.getTotal_shift_prod_b() + lapformer.getTotal_shift_prod_a());

        lapformer.setEfficiency((lapformer.getActual_producation() / lapformer.getMachineefficencykgcardperday()) * 100);
        lapformer.setTarget_prod_variance_kg(lapformer.getMachineefficencykgcardperday() - lapformer.getActual_producation());
        lapformer.setTarget_prod_variance(((lapformer.getActual_producation() - lapformer.getMachineefficencykgcardperday())
                / lapformer.getMachineefficencykgcardperday()) * 100);

    }

    private void mapRestModelToPersistenceShiftBOne(LapFormerRest lapFormerRest, LapFormer lapformer) {
        lapformer.setMachineId(lapFormerRest.getId());
        lapformer.setShift_b_sixHoursOne(lapFormerRest.getShift_b_sixHoursOne());
        lapformer.setAvervg_difference_b_sixHoursOne((lapformer.getShift_b_sixHoursOne() - lapformer.getMachineefficencykgcardpersixhours()) /
                lapformer.getMachineefficencykgcardpersixhours() * 100);
        lapformer.setTotal_shift_prod_a(lapformer.getShift_a_sixHoursOne() + lapformer.getShift_a_sixHoursTwo());
        lapformer.setTotal_shift_prod_b(lapformer.getShift_b_sixHoursOne() + lapformer.getShift_b_sixHoursTwo());

        lapformer.setActual_producation(lapformer.getTotal_shift_prod_b() + lapformer.getTotal_shift_prod_a());

        lapformer.setEfficiency((lapformer.getActual_producation() / lapformer.getMachineefficencykgcardperday()) * 100);
        lapformer.setTarget_prod_variance_kg(lapformer.getMachineefficencykgcardperday() - lapformer.getActual_producation());
        lapformer.setTarget_prod_variance(((lapformer.getActual_producation() - lapformer.getMachineefficencykgcardperday())
                / lapformer.getMachineefficencykgcardperday()) * 100);
    }


    private void mapRestModelToPersistenceShiftATwo(LapFormerRest lapFormerRest, LapFormer lapformer) {
        lapformer.setMachineId(lapFormerRest.getId());
        lapformer.setShift_a_sixHoursTwo(lapFormerRest.getShift_a_sixHoursTwo());
        lapformer.setAvervg_difference_a_sixHoursTwo((lapformer.getShift_a_sixHoursTwo() - lapformer.getMachineefficencykgcardpersixhours()
        ) / lapformer.getMachineefficencykgcardpersixhours() * 100);

        lapformer.setTotal_shift_prod_a(lapformer.getShift_a_sixHoursOne() + lapformer.getShift_a_sixHoursTwo());
        lapformer.setTotal_shift_prod_b(lapformer.getShift_b_sixHoursOne() + lapformer.getShift_b_sixHoursTwo());

        lapformer.setActual_producation(lapformer.getTotal_shift_prod_b() + lapformer.getTotal_shift_prod_a());

        lapformer.setEfficiency((lapformer.getActual_producation() / lapformer.getMachineefficencykgcardperday()) * 100);
        lapformer.setTarget_prod_variance_kg(lapformer.getMachineefficencykgcardperday() - lapformer.getActual_producation());
        lapformer.setTarget_prod_variance(((lapformer.getActual_producation() - lapformer.getMachineefficencykgcardperday())
                / lapformer.getMachineefficencykgcardperday()) * 100);
    }

    private void mapRestModelToPersistenceShiftAOne(LapFormerRest lapFormerRest, LapFormer lapformer) {
        lapformer.setMachineId(lapFormerRest.getId());
        lapformer.setShift_a_sixHoursOne(lapFormerRest.getShift_a_sixHoursOne());
        lapformer.setAvervg_difference_a_sixHoursOne((lapformer.getShift_a_sixHoursOne() - lapformer.getMachineefficencykgcardpersixhours()
        ) / lapformer.getMachineefficencykgcardpersixhours() * 100);
        lapformer.setTotal_shift_prod_a(lapformer.getShift_a_sixHoursOne() + lapformer.getShift_a_sixHoursTwo());
        lapformer.setTotal_shift_prod_b(lapformer.getShift_b_sixHoursOne() + lapformer.getShift_b_sixHoursTwo());

        lapformer.setActual_producation(lapformer.getTotal_shift_prod_b() + lapformer.getTotal_shift_prod_a());

        lapformer.setEfficiency((lapformer.getActual_producation() / lapformer.getMachineefficencykgcardperday()) * 100);
        lapformer.setTarget_prod_variance_kg(lapformer.getMachineefficencykgcardperday() - lapformer.getActual_producation());
        lapformer.setTarget_prod_variance(((lapformer.getActual_producation() - lapformer.getMachineefficencykgcardperday())
                / lapformer.getMachineefficencykgcardperday()) * 100);
    }

    private LapFormerRest mapPersistenceToRestModel(LapFormer lapformer) {
        LapFormerRest lapFormerRestDto = new LapFormerRest();
        lapFormerRestDto.setId(lapformer.getMachineId());

        //Shift A ANd Time  8:00 Am to 2:00 pm

        lapFormerRestDto.setShift_a_sixHoursOne(lapformer.getShift_a_sixHoursOne());
        lapFormerRestDto.setAvervg_difference_a_sixHoursOne(lapformer.getAvervg_difference_a_sixHoursOne());

        //Shift A ANd Time  02:00 pm to 8:00 pm

        lapFormerRestDto.setShift_a_sixHoursTwo(lapformer.getShift_a_sixHoursTwo());
        lapFormerRestDto.setAvervg_difference_a_sixHoursTwo(lapformer.getAvervg_difference_a_sixHoursTwo());

        // total production Shift A

        lapFormerRestDto.setTotal_shift_prod_a(lapformer.getTotal_shift_prod_a());

        //Shift B ANd Time  08:00 pm to 02:00 am

        lapFormerRestDto.setShift_b_sixHoursOne(lapformer.getShift_b_sixHoursOne());
        lapFormerRestDto.setAvervg_difference_b_sixHoursOne(lapformer.getAvervg_difference_b_sixHoursOne());

        //Shift B ANd Time  08:00 pm to 08:00 am

        lapFormerRestDto.setShift_b_sixHoursTwo(lapformer.getShift_b_sixHoursTwo());
        lapFormerRestDto.setAvervg_difference_b_sixHoursTwo(lapformer.getAvervg_difference_b_sixHoursTwo());

        //  total production Shift A

        lapFormerRestDto.setTotal_shift_prod_b(lapformer.getTotal_shift_prod_b());

        // calculation total production and efficiency and total production variance kg and simple

        lapFormerRestDto.setActual_producation(lapformer.getActual_producation());
        lapFormerRestDto.setEfficiency(lapformer.getEfficiency());
        lapFormerRestDto.setTarget_prod_variance_kg(lapformer.getTarget_prod_variance_kg());
        lapFormerRestDto.setTarget_prod_variance(lapformer.getTarget_prod_variance());

        return lapFormerRestDto;
    }


}

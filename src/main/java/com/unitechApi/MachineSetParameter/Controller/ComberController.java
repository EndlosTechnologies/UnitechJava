package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.DtoRest.ComberRest;
import com.unitechApi.MachineSetParameter.ExcelService.ComberExcelService;
import com.unitechApi.MachineSetParameter.model.Comber;
import com.unitechApi.MachineSetParameter.repository.ComberRepository;
import com.unitechApi.MachineSetParameter.service.ComberService;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.TimeExtendException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/unitech/api/v1/machine/comber")
public class ComberController {
    private final ComberRepository comberRepository;
    private final ComberService comberService;
    private static final Logger logger = LoggerFactory.getLogger(ComberController.class);

    public ComberController(ComberRepository comberRepository, ComberService comberService) {
        this.comberRepository = comberRepository;
        this.comberService = comberService;
    }

    @PostMapping("/save")
    public ResponseEntity<Comber> SaveData(@RequestBody Comber comber) {
        return new ResponseEntity<>(comberService.SaveData(comber), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comber> GetById(@PathVariable Long id) {
        return new ResponseEntity<>(comberService.FindById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> SerachData(@RequestParam Date start, @RequestParam Date end) {

        List<Comber> comber = comberService.FindByDate(start, end);
        logger.info("data  {}=> ",comber);
        return new ResponseEntity<>(PageResponse.SuccessResponse(comber), HttpStatus.OK);
    }

    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start) {

        List<Comber> bloowRooms = comberService.FindBySingleDate(start);
        return new ResponseEntity<>(PageResponse.SuccessResponse(bloowRooms), HttpStatus.OK);
    }

    @PutMapping("{co_a_id}/update/{co_r_id}")
    public ResponseEntity<?> UpdateId(@PathVariable Long co_a_id, @PathVariable Long co_r_id) {
        return new ResponseEntity<>(comberService.updateid(co_r_id, co_a_id), HttpStatus.OK);
    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportToExcel(@RequestParam Date start,
                              @RequestParam Date end, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=comberData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Comber> ListData = comberService.ExcelDateToDateReport(start, end);
        ListData.forEach(System.out::println);

        ComberExcelService c = new ComberExcelService(ListData);
        c.export(response);
    }

    @GetMapping("/downloadSingle")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void perDayExportToExcel(@RequestParam Date start,
                                    HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=comberData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Comber> ListData = comberService.ExcelDateToPerDateReport(start);
        ListData.forEach(System.out::println);

        ComberExcelService c = new ComberExcelService(ListData);
        c.export(response);
    }

    @PatchMapping("/updateShiftAOne/{id}")
    public ResponseEntity<?> UpdateReadingParameterShiftAOne(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String TimeFormat = "hh:mm:ss a";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TimeFormat);
        if (LocalTime.now().isAfter(LocalTime.from(dateTimeFormatter.parse("08:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(dateTimeFormatter.parse("02:20:00 PM")))) {
            Comber comber = comberRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            ComberRest comberRest = mapPersistanceTOModel(comber);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursOne":
                        comberRest.setShift_a_sixHoursOne(values);
                }
            });
            mapRestModelTOPersistanceShiftAone(comber, comberRest);
            comberRepository.save(comber);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(TimeFormat)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(TimeFormat))), HttpStatus.OK);
    }

    @PatchMapping("/updateShiftATwo/{id}")
    public ResponseEntity<?> UpdateReadingParameterShiftATwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String TimeFormat = "hh:mm:ss a";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TimeFormat);
        if (LocalTime.now().isAfter(LocalTime.from(dateTimeFormatter.parse("02:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(dateTimeFormatter.parse("08:20:00 PM")))) {
            Comber comber = comberRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            ComberRest comberRest = mapPersistanceTOModel(comber);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursTwo":
                        comberRest.setShift_a_sixHoursTwo(values);
                }
            });
            mapRestModelTOPersistanceShiftATwo(comber, comberRest);
            comberRepository.save(comber);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(TimeFormat)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(TimeFormat))), HttpStatus.OK);
    }

    @PatchMapping("/updateShiftBOne/{id}")
    public ResponseEntity<?> UpdateReadingParameterShiftBOno(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String TimeFormat = "hh:mm:ss a";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TimeFormat);
        if ((LocalTime.now().isAfter(LocalTime.from(dateTimeFormatter.parse("08:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(dateTimeFormatter.parse("11:59:59 PM"))))
                || (LocalTime.now().isAfter(LocalTime.from(dateTimeFormatter.parse("00:00:00 AM")))
                && LocalTime.now().isBefore(LocalTime.from(dateTimeFormatter.parse("02:20:00 AM"))))) {
            Comber comber = comberRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            ComberRest comberRest = mapPersistanceTOModel(comber);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursOne":
                        comberRest.setShift_b_sixHoursOne(values);
                }
            });
            mapRestModelTOPersistanceShiftBOne(comber, comberRest);
            comberRepository.save(comber);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(TimeFormat)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(TimeFormat))), HttpStatus.OK);
    }

    @PatchMapping("/updateShiftBTwo/{id}")
    public ResponseEntity<?> UpdateReadingParameterShiftBTwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String TimeFormat = "hh:mm:ss a";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TimeFormat);
        if (LocalTime.now().isAfter(LocalTime.from(dateTimeFormatter.parse("02:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(dateTimeFormatter.parse("08:20:00 AM")))) {
            Comber comber = comberRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            ComberRest comberRest = mapPersistanceTOModel(comber);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursTwo":
                        comberRest.setShift_b_sixHoursTwo(values);
                }
            });
            mapRestModelTOPersistanceShiftBTwo(comber, comberRest);
            comberRepository.save(comber);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(TimeFormat)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(TimeFormat))), HttpStatus.OK);
    }

    private void mapRestModelTOPersistanceShiftBTwo(Comber comber, ComberRest comberRest) {
        comber.setMachineId(comberRest.getId());
        comber.setShift_b_sixHoursTwo(comberRest.getShift_b_sixHoursTwo());
        comber.setAvervg_difference_b_sixHoursTwo((comber.getShift_b_sixHoursTwo() - comber.getProductioMc6Hour()) /
                comber.getProductioMc6Hour() * 100);
        comber.setTotal_shift_prod_a(comber.getShift_a_sixHoursOne() + comber.getShift_a_sixHoursTwo());
        comber.setTotal_shift_prod_b(comber.getShift_b_sixHoursOne() + comber.getShift_b_sixHoursTwo());

        comber.setActual_producation(comber.getTotal_shift_prod_b() + comber.getTotal_shift_prod_a());

        comber.setEfficiency((comber.getActual_producation() / comber.getProductioMc24Hour()) * 100);
        comber.setTarget_prod_variance_kg(comber.getProductioMc24Hour() - comber.getActual_producation());
        comber.setTarget_prod_variance(((comber.getActual_producation() - comber.getProductioMc24Hour())
                / comber.getProductioMc24Hour()) * 100);
    }

    private void mapRestModelTOPersistanceShiftBOne(Comber comber, ComberRest comberRest) {
        comber.setMachineId(comberRest.getId());
        comber.setShift_b_sixHoursOne(comberRest.getShift_b_sixHoursOne());
        comber.setAvervg_difference_b_sixHoursOne((comber.getShift_b_sixHoursOne() - comber.getProductioMc6Hour()) /
                comber.getProductioMc6Hour() * 100);
        comber.setTotal_shift_prod_a(comber.getShift_a_sixHoursOne() + comber.getShift_a_sixHoursTwo());
        comber.setTotal_shift_prod_b(comber.getShift_b_sixHoursOne() + comber.getShift_b_sixHoursTwo());

        comber.setActual_producation(comber.getTotal_shift_prod_b() + comber.getTotal_shift_prod_a());

        comber.setEfficiency((comber.getActual_producation() / comber.getProductioMc24Hour()) * 100);
        comber.setTarget_prod_variance_kg(comber.getProductioMc24Hour() - comber.getActual_producation());
        comber.setTarget_prod_variance(((comber.getActual_producation() - comber.getProductioMc24Hour())
                / comber.getProductioMc24Hour()) * 100);
    }

    private void mapRestModelTOPersistanceShiftATwo(Comber comber, ComberRest comberRest) {
        comber.setMachineId(comberRest.getId());
        comber.setShift_a_sixHoursTwo(comberRest.getShift_a_sixHoursTwo());
        comber.setAvervg_difference_a_sixHoursTwo((comber.getShift_a_sixHoursTwo() - comber.getProductioMc6Hour()
        ) / comber.getProductioMc6Hour() * 100);
        comber.setTotal_shift_prod_a(comber.getShift_a_sixHoursOne() + comber.getShift_a_sixHoursTwo());
        comber.setTotal_shift_prod_a(comber.getShift_a_sixHoursOne() + comber.getShift_a_sixHoursTwo());
        comber.setTotal_shift_prod_b(comber.getShift_b_sixHoursOne() + comber.getShift_b_sixHoursTwo());

        comber.setActual_producation(comber.getTotal_shift_prod_b() + comber.getTotal_shift_prod_a());

        comber.setEfficiency((comber.getActual_producation() / comber.getProductioMc24Hour()) * 100);
        comber.setTarget_prod_variance_kg(comber.getProductioMc24Hour() - comber.getActual_producation());
        comber.setTarget_prod_variance(((comber.getActual_producation() - comber.getProductioMc24Hour())
                / comber.getProductioMc24Hour()) * 100);
    }


    private void mapRestModelTOPersistanceShiftAone(Comber comber, ComberRest comberRest) {
        comber.setMachineId(comberRest.getId());
        comber.setShift_a_sixHoursOne(comberRest.getShift_a_sixHoursOne());
        comber.setAvervg_difference_a_sixHoursOne((comber.getShift_a_sixHoursOne() - comber.getProductioMc6Hour()
        ) / comber.getProductioMc6Hour() * 100);
        comber.setTotal_shift_prod_a(comber.getShift_a_sixHoursOne() + comber.getShift_a_sixHoursTwo());
        comber.setTotal_shift_prod_b(comber.getShift_b_sixHoursOne() + comber.getShift_b_sixHoursTwo());

        comber.setActual_producation(comber.getTotal_shift_prod_b() + comber.getTotal_shift_prod_a());

        comber.setEfficiency((comber.getActual_producation() / comber.getProductioMc24Hour()) * 100);
        comber.setTarget_prod_variance_kg(comber.getProductioMc24Hour() - comber.getActual_producation());
        comber.setTarget_prod_variance(((comber.getActual_producation() - comber.getProductioMc24Hour())
                / comber.getProductioMc24Hour()) * 100);
    }

    private ComberRest mapPersistanceTOModel(Comber comber) {
        ComberRest comberRest = new ComberRest();
        comberRest.setId(comber.getMachineId());

        //Shift A ANd Time  8:00 Am to 2:00 pm

        comberRest.setShift_a_sixHoursOne(comber.getShift_a_sixHoursOne());
        comberRest.setAvervg_difference_a_sixHoursOne(comber.getAvervg_difference_a_sixHoursOne());

        //Shift A ANd Time  02:00 pm to 8:00 pm

        comberRest.setShift_a_sixHoursTwo(comber.getShift_a_sixHoursTwo());
        comberRest.setAvervg_difference_a_sixHoursTwo(comber.getAvervg_difference_a_sixHoursTwo());

        // total production Shift A

        comberRest.setTotal_shift_prod_a(comber.getTotal_shift_prod_a());

        //Shift B ANd Time  08:00 pm to 02:00 am

        comberRest.setShift_b_sixHoursOne(comber.getShift_b_sixHoursOne());
        comberRest.setAvervg_difference_b_sixHoursOne(comber.getAvervg_difference_b_sixHoursOne());

        //Shift B ANd Time  08:00 pm to 08:00 am

        comberRest.setShift_b_sixHoursTwo(comber.getShift_b_sixHoursTwo());
        comberRest.setAvervg_difference_b_sixHoursTwo(comber.getAvervg_difference_b_sixHoursTwo());

        //  total production Shift A

        comberRest.setTotal_shift_prod_b(comber.getTotal_shift_prod_b());

        // calculation total production and efficiency and total production variance kg and simple

        comberRest.setActual_producation(comber.getActual_producation());
        comberRest.setEfficiency(comber.getEfficiency());
        comberRest.setTarget_prod_variance_kg(comber.getTarget_prod_variance_kg());
        comberRest.setTarget_prod_variance(comber.getTarget_prod_variance());

        return comberRest;
    }

}

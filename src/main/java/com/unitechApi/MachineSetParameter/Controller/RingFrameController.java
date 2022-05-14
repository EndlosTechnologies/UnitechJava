package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.DtoRest.RingFrameRest;
import com.unitechApi.MachineSetParameter.ExcelService.RingframeExcelService;
import com.unitechApi.MachineSetParameter.model.RingFrame;
import com.unitechApi.MachineSetParameter.repository.RingFrameRepossitory;
import com.unitechApi.MachineSetParameter.service.RingframesService;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddRingFramesMachine;
import com.unitechApi.addmachine.repositroy.AddRingFrameRepossitory;
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
@RequestMapping("/unitech/api/v1/machine/ringframe")
public class RingFrameController {
    private final RingFrameRepossitory ringFrameRepossitory;
    private final RingframesService ringframesService;
    private final AddRingFrameRepossitory addRingFrameRepossitory;

    public RingFrameController(RingFrameRepossitory ringFrameRepossitory, RingframesService ringframesService, AddRingFrameRepossitory addRingFrameRepossitory) {
        this.ringFrameRepossitory = ringFrameRepossitory;
        this.ringframesService = ringframesService;
        this.addRingFrameRepossitory = addRingFrameRepossitory;
    }

    @PostMapping("/save")
    public ResponseEntity<RingFrame> SaveData(@RequestBody RingFrame ringFrame) {
        return ResponseEntity.ok(ringframesService.SaveData(ringFrame));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<RingFrame>> findByid(@PathVariable Long id) {
        return ResponseEntity.ok(ringframesService.FindByData(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(ringframesService.ViewData());
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        ringframesService.DeleteReading(id);
    }

    @PatchMapping("/update")
    public ResponseEntity<RingFrame> UpdateData(@PathVariable Long id,@RequestBody Map<Object, Object> fields) {
        Optional<RingFrame> ringFrame = ringFrameRepossitory.findById(id);
        if (ringFrame.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(RingFrame.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, ringFrame.get(), value);
            });
            ringFrameRepossitory.save(ringFrame.get());
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
        Page<RingFrame> pagecontent = ringframesService.FindData(start, end, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(pagecontent, pagination), HttpStatus.OK);
    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportToExcel(@RequestParam Date start,
                              @RequestParam Date end, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=RingFrameData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<RingFrame> ListData = ringframesService.ExcelDateToDateReport(start, end);


        RingframeExcelService c = new RingframeExcelService(ListData);
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
        String headerValue = "attachment; filename=RingFrameData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<RingFrame> ListData = ringframesService.ExcelDateToPerDateReport(start);
        ListData.forEach(carding -> System.out.println(carding));

        RingframeExcelService c = new RingframeExcelService(ListData);
        c.export(response);

    }

    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start, @RequestParam int page, @RequestParam int pageSize) {
        Pagination pagination = new Pagination(page, pageSize);
        Page<RingFrame> bloowRooms = ringframesService.FindBySingleDate(start, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(bloowRooms, pagination), HttpStatus.OK);
    }

    @PutMapping("/{r_a_id}/update/{r_id}")
    public RingFrame updateid(@PathVariable Long r_a_id, @PathVariable Long r_id) {
        AddRingFramesMachine addRingFramesMachine = addRingFrameRepossitory.findById(r_a_id).get();
        RingFrame ringFrame = ringFrameRepossitory.findById(r_id).get();
        ringFrame.idupdate(addRingFramesMachine);
        return ringFrameRepossitory.save(ringFrame);
    }

    /*
     * calculation of Shift A And
     * time 08:00 Am To time 08:00 Pm
     * */
    @PatchMapping("/updateshiftAOne/{id}")
    public ResponseEntity<?> UpdateShiftAOneReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("08:00:00 AM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("10:20:00 AM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_twoHoursOne":
//                        ringFrameRest.setShift_a_twoHoursOne(value);
                        ringFrameRest.setShift_a_twoHoursOne(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftATwoOne(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateshiftATwo/{id}")
    public ResponseEntity<?> UpdateShiftATwoReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("10:00:00 AM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("12:20:00 PM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_twoHoursTwo":
                        ringFrameRest.setShift_a_twoHoursTwo(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftATwoTwo(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateshiftAThree/{id}")
    public ResponseEntity<?> UpdateShiftAThreeReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("00:00:00 PM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("02:20:00 PM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_twoHoursThree":
                        ringFrameRest.setShift_a_twoHoursThree(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftATwoThree(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateshiftAFour/{id}")
    public ResponseEntity<?> UpdateShiftAFourReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("02:00:00 PM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("04:20:00 PM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_twoHoursFour":
                        ringFrameRest.setShift_a_twoHoursFour(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftATwoFour(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateshiftAFive/{id}")
    public ResponseEntity<?> UpdateShiftAFiveReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("04:00:00 PM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("06:20:00 PM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_twoHoursFive":
                        ringFrameRest.setShift_a_twoHoursFive(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftATwoFive(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateshiftASix/{id}")
    public ResponseEntity<?> UpdateShiftASixReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("06:00:00 PM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("08:20:00 PM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_twoHoursSix":
                        ringFrameRest.setShift_a_twoHoursSix(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftATwoSix(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    /*
     * calculation of Shift A And
     * time 08:00 Pm To time 08:00 Am
     * */
    @PatchMapping("/updateshiftBOne/{id}")
    public ResponseEntity<?> UpdateShiftBOneReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("08:00:00 PM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("10:20:00 PM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_twoHoursOne":
                        ringFrameRest.setShift_b_twoHoursOne(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftBTwoOne(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateshiftBTwo/{id}")
    public ResponseEntity<?> UpdateShiftBTwoReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("10:00:00 PM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("11:59:59 PM")

                        )) || (LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("00:00:00 AM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("00:20:00 AM")))
                )) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_twoHoursTwo":
                        ringFrameRest.setShift_b_twoHoursTwo(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftBTwoTwo(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateshiftBThree/{id}")
    public ResponseEntity<?> UpdateShiftBThreeReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("00:00:00 AM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("02:20:00 AM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_twoHoursThree":
                        ringFrameRest.setShift_b_twoHoursThree(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftBTwoThree(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateshiftBFour/{id}")
    public ResponseEntity<?> UpdateShiftBFourReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("02:00:00 AM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("04:20:00 AM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_twoHoursFour":
                        ringFrameRest.setShift_b_twoHoursFour(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftBTwoFour(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateshiftBFive/{id}")
    public ResponseEntity<?> UpdateShiftBFiveReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("04:00:00 AM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("06:20:00 AM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_twoHoursFive":
                        ringFrameRest.setShift_b_twoHoursFive(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftBTwoFive(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    @PatchMapping("/updateshiftBSix/{id}")
    public ResponseEntity<?> UpdateShiftBSixReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        if (
                LocalTime.now().isAfter(LocalTime.from(timeColonFormatter.parse("06:00:00 AM")))
                        &&
                        LocalTime.now().isBefore(LocalTime.from(timeColonFormatter.parse("08:20:00 AM")
                        ))
        ) {
            System.out.println(LocalTime.now());
            RingFrame ringFrame = ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            RingFrameRest ringFrameRest = mapPersistenceToRestModelOne(ringFrame);

            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_twoHoursSix":
                        ringFrameRest.setShift_b_twoHoursSix(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelShiftBTwoSix(ringFrameRest, ringFrame);
            ringFrameRepossitory.save(ringFrame);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timeColonPattern))), HttpStatus.OK);
    }

    private void mapRestModelTopersuistanceModelShiftBTwoSix(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_b_twoHoursSix(ringFrameRest.getShift_b_twoHoursSix());
        ringFrame.setAvervg_difference_b_twoHoursSix((ringFrame.getShift_b_twoHoursSix() - ringFrame.getProductionSpindle2HoursKg()) /
                ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }

    private void mapRestModelTopersuistanceModelShiftBTwoFive(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_b_twoHoursFive(ringFrameRest.getShift_b_twoHoursFive());
        ringFrame.setAvervg_difference_b_twoHoursFive((ringFrame.getShift_b_twoHoursFive() - ringFrame.getProductionSpindle2HoursKg()) /
                ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }


    private void mapRestModelTopersuistanceModelShiftBTwoFour(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_b_twoHoursFour(ringFrameRest.getShift_b_twoHoursFour());
        ringFrame.setAvervg_difference_b_twoHoursFour((ringFrame.getShift_b_twoHoursFour() -
                ringFrame.getProductionSpindle2HoursKg()) /
                ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }

    private void mapRestModelTopersuistanceModelShiftBTwoThree(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_b_twoHoursThree(ringFrameRest.getShift_b_twoHoursThree());
        ringFrame.setAvervg_difference_b_twoHoursThree((ringFrame.getShift_b_twoHoursThree() -
                ringFrame.getProductionSpindle2HoursKg()) /
                ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }

    private void mapRestModelTopersuistanceModelShiftBTwoTwo(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_b_twoHoursTwo(ringFrameRest.getShift_b_twoHoursTwo());
        ringFrame.setAvervg_difference_b_twoHoursTwo((ringFrame.getShift_b_twoHoursTwo() -
                ringFrame.getProductionSpindle2HoursKg()) /
                ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }

    private void mapRestModelTopersuistanceModelShiftBTwoOne(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_b_twoHoursOne(ringFrameRest.getShift_b_twoHoursOne());
        ringFrame.setAvervg_difference_b_twoHoursOne((ringFrame.getShift_b_twoHoursOne() -
                ringFrame.getProductionSpindle2HoursKg()) /
                ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }

    private void mapRestModelTopersuistanceModelShiftATwoSix(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_a_twoHoursSix(ringFrameRest.getShift_a_twoHoursSix());
        ringFrame.setAvervg_difference_a_twoHoursSix((ringFrame.getShift_a_twoHoursSix() - ringFrame.getProductionSpindle2HoursKg()) /
                ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);

    }

    private void mapRestModelTopersuistanceModelShiftATwoFive(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_a_twoHoursFive(ringFrameRest.getShift_a_twoHoursFive());
        ringFrame.setAvervg_difference_a_twoHoursFive((ringFrame.getShift_a_twoHoursFive() - ringFrame.getProductionSpindle2HoursKg()) /
                ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }

    private void mapRestModelTopersuistanceModelShiftATwoFour(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_a_twoHoursFour(ringFrameRest.getShift_a_twoHoursFour());
        ringFrame.setAvervg_difference_a_twoHoursFour((ringFrame.getShift_a_twoHoursFour() - ringFrame.getProductionSpindle2HoursKg()) /
                ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }


    private void mapRestModelTopersuistanceModelShiftATwoThree(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_a_twoHoursThree(ringFrameRest.getShift_a_twoHoursThree());
        ringFrame.setAvervg_difference_a_twoHoursThree((ringFrame.getShift_a_twoHoursThree() - ringFrame.getProductionSpindle2HoursKg()) /
                ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }

    private void mapRestModelTopersuistanceModelShiftATwoTwo(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());
        ringFrame.setShift_a_twoHoursTwo(ringFrameRest.getShift_a_twoHoursTwo());
        ringFrame.setAvervg_difference_a_twoHoursTwo((ringFrame.getShift_a_twoHoursTwo() - ringFrame.getProductionSpindle2HoursKg())
                / ringFrame.getProductionSpindle2HoursKg() * 100);
        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }

    private void mapRestModelTopersuistanceModelShiftATwoOne(RingFrameRest ringFrameRest, RingFrame ringFrame) {
        ringFrame.setMachineId(ringFrameRest.getId());

        ringFrame.setShift_a_twoHoursOne(ringFrameRest.getShift_a_twoHoursOne());
        ringFrame.setAverageshift_a_HankOne((float) ((ringFrame.getShift_a_twoHoursOne()/(2.204*ringFrame.getRingFrameCount())) * 1440 * ringFrame.getMachineEfficiency())/100);
        ringFrame.setAvervg_difference_a_twoHoursOne((ringFrame.getAverageshift_a_HankOne() - ringFrame.getProductionSpindle2HoursKg() )
                / ringFrame.getProductionSpindle2HoursKg() * 100);

        ringFrame.setTotal_shift_prod_a(ringFrame.getShift_a_twoHoursOne() + ringFrame.getShift_a_twoHoursTwo() + ringFrame.getShift_a_twoHoursThree()
                + ringFrame.getShift_a_twoHoursFour() + ringFrame.getShift_a_twoHoursFive() + ringFrame.getShift_a_twoHoursSix());
        ringFrame.setTotal_shift_prod_b(ringFrame.getShift_b_twoHoursOne() + ringFrame.getShift_b_twoHoursTwo() + ringFrame.getShift_b_twoHoursThree()
                + ringFrame.getShift_b_twoHoursFour() + ringFrame.getShift_b_twoHoursFive() + ringFrame.getShift_b_twoHoursSix());
        ringFrame.setActual_producation(ringFrame.getTotal_shift_prod_a() + ringFrame.getTotal_shift_prod_b());
        ringFrame.setEfficiency((ringFrame.getActual_producation() / ringFrame.getNetProduction()) * 100);
        ringFrame.setTarget_prod_variance_kg(ringFrame.getActual_producation() - ringFrame.getNetProduction());
        ringFrame.setTarget_prod_variance((ringFrame.getActual_producation() - ringFrame.getNetProduction()) / ringFrame.getNetProduction() * 100);
    }

    private RingFrameRest mapPersistenceToRestModelOne(RingFrame ringFrame) {
        RingFrameRest ringFrameRest = new RingFrameRest();

        ringFrameRest.setId(ringFrame.getMachineId());

        // Reading Shift A 8:00 AM TO 10:00 AM

        ringFrameRest.setShift_a_twoHoursOne(ringFrame.getShift_a_twoHoursOne());
        ringFrameRest.setAvervg_difference_a_twoHoursOne(ringFrame.getAvervg_difference_a_twoHoursOne());

        // Reading Shift A 10:00 AM TO 12:00 PM

        ringFrameRest.setShift_a_twoHoursTwo(ringFrame.getShift_a_twoHoursTwo());
        ringFrameRest.setAvervg_difference_a_twoHoursTwo(ringFrame.getAvervg_difference_a_twoHoursTwo());

        // Reading Shift A 12:00 PM TO 02:00 PM

        ringFrameRest.setShift_a_twoHoursThree(ringFrame.getShift_a_twoHoursThree());
        ringFrameRest.setAvervg_difference_a_twoHoursThree(ringFrame.getAvervg_difference_a_twoHoursThree());

        // Reading Shift A  02:00 PM TO 04:00 PM

        ringFrameRest.setShift_a_twoHoursFour(ringFrame.getShift_a_twoHoursFour());
        ringFrameRest.setAvervg_difference_a_twoHoursFour(ringFrame.getAvervg_difference_a_twoHoursFour());

        // Reading Shift A 04:00 PM TO 06:00 PM

        ringFrameRest.setShift_a_twoHoursFive(ringFrame.getShift_a_twoHoursFive());
        ringFrameRest.setAvervg_difference_a_twoHoursFive(ringFrame.getAvervg_difference_a_twoHoursFive());

        // Reading Shift A 06:00 PM TO 08:00 PM

        ringFrameRest.setShift_a_twoHoursSix(ringFrame.getShift_a_twoHoursSix());
        ringFrameRest.setAvervg_difference_a_twoHoursSix(ringFrame.getAvervg_difference_a_twoHoursSix());

        //  Shift A Total Reading

        ringFrameRest.setTotal_shift_prod_a(ringFrame.getTotal_shift_prod_a());

        //Shift B
        // Reading Shift B 08:00 PM TO 10:00 PM

        ringFrameRest.setShift_b_twoHoursOne(ringFrame.getShift_b_twoHoursOne());
        ringFrameRest.setAvervg_difference_b_twoHoursOne(ringFrame.getAvervg_difference_b_twoHoursOne());

        // Reading Shift B 10:00 PM TO 00:00 AM

        ringFrameRest.setShift_b_twoHoursTwo(ringFrame.getShift_b_twoHoursTwo());
        ringFrameRest.setAvervg_difference_b_twoHoursTwo(ringFrame.getAvervg_difference_b_twoHoursTwo());

        // Reading Shift B 00:00 AM TO 02:00 AM

        ringFrameRest.setShift_b_twoHoursThree(ringFrame.getShift_b_twoHoursThree());
        ringFrameRest.setAvervg_difference_b_twoHoursThree(ringFrame.getAvervg_difference_b_twoHoursThree());

        // Reading Shift B 02:00 AM TO 04:00 AM

        ringFrameRest.setShift_b_twoHoursFour(ringFrame.getShift_b_twoHoursFour());
        ringFrameRest.setAvervg_difference_b_twoHoursFour(ringFrame.getAvervg_difference_b_twoHoursFour());

        // Reading Shift B 04:00 AM TO 06:00 AM

        ringFrameRest.setShift_b_twoHoursFive(ringFrame.getShift_b_twoHoursFive());
        ringFrameRest.setAvervg_difference_b_twoHoursFive(ringFrame.getAvervg_difference_b_twoHoursFive());

        // Reading Shift B 06:00 AM TO 08:00 AM

        ringFrameRest.setShift_b_twoHoursSix(ringFrame.getShift_b_twoHoursSix());
        ringFrameRest.setAvervg_difference_b_twoHoursSix(ringFrame.getAvervg_difference_b_twoHoursSix());

        // shift B Total Reading

        ringFrameRest.setTotal_shift_prod_b(ringFrame.getTotal_shift_prod_b());

        // Total all over Reading

        ringFrameRest.setActual_producation(ringFrame.getActual_producation());
        ringFrameRest.setEfficiency(ringFrame.getEfficiency());
        ringFrameRest.setTarget_prod_variance_kg(ringFrame.getTarget_prod_variance_kg());
        ringFrameRest.setTarget_prod_variance(ringFrame.getTarget_prod_variance());

        return ringFrameRest;
    }
}

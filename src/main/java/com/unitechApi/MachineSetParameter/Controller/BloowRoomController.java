package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.DtoRest.BloowRoomRest;
import com.unitechApi.MachineSetParameter.ExcelService.BloowRoomExcelService;
import com.unitechApi.MachineSetParameter.model.BloowRoom;
import com.unitechApi.MachineSetParameter.repository.BloowRoomRepository;
import com.unitechApi.MachineSetParameter.service.BloowRoomService;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.addmachine.model.AddBloowroom;
import com.unitechApi.addmachine.repositroy.AddBloowRoomRepository;
import com.unitechApi.exception.ExceptionService.MachineNotFound;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.TimeExtendException;
import com.unitechApi.user.controller.AuthController;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/machine/bloowroom")
public class BloowRoomController {

    private final BloowRoomService bloowRoomService;
    private final BloowRoomRepository bloowRoomRepository;
    private final AddBloowRoomRepository addBloowRoomRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public BloowRoomController(BloowRoomService bloowRoomService, BloowRoomRepository bloowRoomRepository, AddBloowRoomRepository addBloowRoomRepository) {
        this.bloowRoomService = bloowRoomService;
        this.bloowRoomRepository = bloowRoomRepository;
        this.addBloowRoomRepository = addBloowRoomRepository;
    }


    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_QC')")
    public ResponseEntity<BloowRoom> SaveData(@RequestBody BloowRoom bloowroom) {
        return new ResponseEntity<>(bloowRoomService.SaveData(bloowroom), HttpStatus.CREATED);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<BloowRoom>> findByid(@PathVariable Long id) {
        return new ResponseEntity<>(bloowRoomService.FindByData(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findall() {
        return ResponseEntity.ok(bloowRoomService.ViewData());

    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        bloowRoomService.DeleteReading(id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> FindByDate(@RequestParam Date start, @RequestParam Date end) {

        List<BloowRoom> bloowRooms = bloowRoomService.FindByDate(start, end);
        return new ResponseEntity<>(PageResponse.SuccessResponse(bloowRooms), HttpStatus.OK);

    }

    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start, @RequestParam int page, @RequestParam int pagesize) {

        List<BloowRoom> bloowRooms = bloowRoomService.listOfData(start);
        logger.info("search single {}" ,bloowRooms);
        return new ResponseEntity<>(PageResponse.SuccessResponse(bloowRooms), HttpStatus.OK);
    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportToExcel(@RequestParam Date start, @RequestParam Date end, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=BloowRoomData" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<BloowRoom> ListData = bloowRoomService.ExcelDateToDateReport(start, end);
        BloowRoomExcelService c = new BloowRoomExcelService(ListData);
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
        String headerValue = "attachment; filename=BloowRoomData" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<BloowRoom> ListData = bloowRoomService.ExcelDateToPerDateReport(start);
        ListData.forEach(System.out::println);

        BloowRoomExcelService c = new BloowRoomExcelService(ListData);
        c.export(response);

    }

    @PutMapping("/{add_blow_id}/update/{blow_id}")
    public BloowRoom updateidBlowroom(@PathVariable Long add_blow_id, @PathVariable Long blow_id) {
        AddBloowroom addBloowroom = addBloowRoomRepository.findById(add_blow_id).orElseThrow(()->new MachineNotFound("machine Not Found "+ add_blow_id));
        BloowRoom bloowroom = bloowRoomRepository.findById(blow_id).orElseThrow(()->new ResourceNotFound("Bloow Room Not Found"));
        bloowroom.UpdateidBlowRoom(addBloowroom);
        return bloowRoomRepository.save(bloowroom);
    }

    @PatchMapping("/updateshiftAone/{id}")
    public ResponseEntity<?> UpdateShiftAReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) throws ParseException {

        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("08:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("02:20:00 PM")))
        ) {
            BloowRoom bloowRoom = bloowRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            BloowRoomRest bloowRoomRest = mapPersistenceToRestModel(bloowRoom);
            System.out.println(bloowRoom.getMachineefficencykgcardpersixhours());
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_sixHoursOne":
                        bloowRoomRest.setShift_a_sixHoursOne(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelone(bloowRoomRest, bloowRoom);
            bloowRoomRepository.save(bloowRoom);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ");
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateshiftAtwo/{id}")
    public ResponseEntity<?> UpdateShiftBTWoReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) throws ParseException {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("02:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("08:20:00 PM")))
        ) {
            BloowRoom bloowRoom = bloowRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            BloowRoomRest bloowRoomRest = mapPersistenceToRestModel(bloowRoom);
            System.out.println(bloowRoom.getMachineefficencykgcardpersixhours());
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_a_sixHoursTwo":
                        bloowRoomRest.setShift_a_sixHoursTwo(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModeltwo(bloowRoomRest, bloowRoom);
            bloowRoomRepository.save(bloowRoom);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ");
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateshiftBOne/{id}")
    public ResponseEntity<?> UpdateShiftBOneReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) throws ParseException {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if ((LocalTime.now().isAfter(LocalTime.from(timeformat.parse("08:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("11:59:59 PM"))))
                || (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("00:00:00 AM")))
                && LocalTime.now().isBefore(LocalTime.from(timeformat.parse("02:20:00 AM"))))
        ) {
            System.out.println(LocalTime.now());
            BloowRoom bloowRoom = bloowRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            BloowRoomRest bloowRoomRest = mapPersistenceToRestModel(bloowRoom);
            System.out.println(bloowRoom.getMachineefficencykgcardpersixhours());
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_sixHoursOne":
                        bloowRoomRest.setShift_b_sixHoursOne(value);
                        ;
                        break;
                }
            });
            mapRestModelTopersuistanceModelThree(bloowRoomRest, bloowRoom);
            bloowRoomRepository.save(bloowRoom);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ");
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateshiftBtwo/{id}")
    public ResponseEntity<?> UpdateShiftBtwoReading(@PathVariable Long id, @RequestBody Map<String, Float> reading) throws ParseException {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("02:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("08:20:00 AM")))
        ) {
            BloowRoom bloowRoom = bloowRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            BloowRoomRest bloowRoomRest = mapPersistenceToRestModel(bloowRoom);
            System.out.println(bloowRoom.getMachineefficencykgcardpersixhours());
            reading.forEach((change, value) -> {
                switch (change) {
                    case "shift_b_sixHoursTwo":
                        bloowRoomRest.setShift_b_sixHoursTwo(value);
                        break;
                }
            });
            mapRestModelTopersuistanceModelFourth(bloowRoomRest, bloowRoom);
            bloowRoomRepository.save(bloowRoom);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ");
        }
        return ResponseEntity.ok().build();
    }

    private void mapRestModelTopersuistanceModelone(BloowRoomRest bloowRoomRest, BloowRoom bloowRoom) {

        //  Shift b  data and avereage data  shift One

        bloowRoom.setMachineId(bloowRoomRest.getId());
        bloowRoom.setShift_a_sixHoursOne(bloowRoomRest.getShift_a_sixHoursOne());
        bloowRoom.setAvervg_difference_a_sixHoursOne(((bloowRoom.getShift_a_sixHoursOne() - bloowRoom.getMachineefficencykgcardpersixhours()) / bloowRoom.getMachineefficencykgcardpersixhours()) * 100);

    }

    private void mapRestModelTopersuistanceModeltwo(BloowRoomRest bloowRoomRest, BloowRoom bloowRoom) {
        //  Shift b   data and avereage data
        //  Shift two

        bloowRoom.setMachineId(bloowRoomRest.getId());
        bloowRoom.setShift_a_sixHoursTwo(bloowRoomRest.getShift_a_sixHoursTwo());
        bloowRoom.setAvervg_difference_a_sixHoursTwo(((bloowRoom.getShift_a_sixHoursTwo() - bloowRoom.getMachineefficencykgcardpersixhours()) / bloowRoom.getMachineefficencykgcardpersixhours()) * 100);
        bloowRoom.setTotal_shift_prod_a(bloowRoom.getShift_a_sixHoursOne() + bloowRoom.getShift_a_sixHoursTwo());
    }

    private void mapRestModelTopersuistanceModelThree(BloowRoomRest bloowRoomRest, BloowRoom bloowRoom) {
        //  Shift a  data and avereage data
        //  shift six hours one means 6 hours

        bloowRoom.setMachineId(bloowRoomRest.getId());
        bloowRoom.setShift_b_sixHoursOne(bloowRoomRest.getShift_b_sixHoursOne());
        bloowRoom.setAvervg_difference_b_sixHoursOne(((bloowRoom.getShift_b_sixHoursOne() - bloowRoom.getMachineefficencykgcardpersixhours())
                / bloowRoom.getMachineefficencykgcardpersixhours()) * 100);
        // bloowRoom.setTotal_shift_prod_b(bloowRoom.getShift_b_sixHoursOne() + bloowRoom.getShift_b_sixHoursTwo());
    }

    private void mapRestModelTopersuistanceModelFourth(BloowRoomRest bloowRoomRest, BloowRoom bloowRoom) {
        //  Shift a  data and avereage data
        //  shift six hours one means 6 hours

        bloowRoom.setMachineId(bloowRoomRest.getId());
        bloowRoom.setShift_b_sixHoursTwo(bloowRoomRest.getShift_b_sixHoursTwo());
        bloowRoom.setAvervg_difference_b_sixHoursTwo(((bloowRoom.getShift_b_sixHoursTwo() - bloowRoom.getMachineefficencykgcardpersixhours())
                / bloowRoom.getMachineefficencykgcardpersixhours()) * 100);
        bloowRoom.setTotal_shift_prod_b(bloowRoom.getShift_b_sixHoursOne() + bloowRoom.getShift_b_sixHoursTwo());
        bloowRoom.setActual_producation(bloowRoom.getTotal_shift_prod_b() + bloowRoom.getTotal_shift_prod_a());
        //System.out.println(bloowRoom.getTotal_shift_prod_b()+bloowRoom.getTotal_shift_prod_a());
        bloowRoom.setEfficiency((bloowRoom.getActual_producation() / bloowRoom.getMachineefficencykgcardperday()) * 100);
        bloowRoom.setTarget_prod_variance_kg(bloowRoom.getMachineefficencykgcardperday() - bloowRoom.getActual_producation());
        bloowRoom.setTarget_prod_variance(((bloowRoom.getActual_producation() - bloowRoom.getMachineefficencykgcardperday())
                / bloowRoom.getMachineefficencykgcardperday()) * 100);

    }

    private BloowRoomRest mapPersistenceToRestModel(BloowRoom bloowRoom) {
        BloowRoomRest bloowRoomRest = new BloowRoomRest();
        bloowRoomRest.setId(bloowRoom.getMachineId());

        //  Shift b  data and avereage data

        bloowRoomRest.setShift_b_sixHoursOne(bloowRoom.getShift_b_sixHoursOne());
        bloowRoomRest.setAvervg_difference_b_sixHoursOne(bloowRoom.getAvervg_difference_b_sixHoursOne());
        bloowRoomRest.setShift_b_sixHoursTwo(bloowRoom.getShift_b_sixHoursTwo());
        bloowRoomRest.setAvervg_difference_b_sixHoursTwo(bloowRoom.getAvervg_difference_b_sixHoursTwo());
        bloowRoomRest.setTotal_shift_prod_b(bloowRoom.getTotal_shift_prod_b());

        //  Shift a  data and avereage data

        bloowRoomRest.setShift_a_sixHoursOne(bloowRoom.getShift_a_sixHoursOne());
        bloowRoomRest.setAvervg_difference_a_sixHoursOne(bloowRoom.getAvervg_difference_a_sixHoursOne());
        bloowRoomRest.setShift_a_sixHoursTwo(bloowRoom.getShift_a_sixHoursTwo());
        bloowRoomRest.setAvervg_difference_a_sixHoursTwo(bloowRoom.getAvervg_difference_b_sixHoursTwo());
        bloowRoomRest.setTotal_shift_prod_a(bloowRoom.getTotal_shift_prod_a());

        // actual producatiion and efficiency data
        bloowRoomRest.setActual_producation(bloowRoom.getActual_producation());
        bloowRoomRest.setEfficiency(bloowRoom.getEfficiency());
        bloowRoomRest.setTarget_prod_variance_kg(bloowRoom.getTarget_prod_variance_kg());
        bloowRoomRest.setTarget_prod_variance(bloowRoom.getTarget_prod_variance());
        System.out.println(bloowRoom.getMachineefficencykgcardpersixhours());
        return bloowRoomRest;
    }


}

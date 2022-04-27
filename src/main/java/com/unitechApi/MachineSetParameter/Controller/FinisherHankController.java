package com.unitechApi.MachineSetParameter.Controller;

import com.unitechApi.MachineSetParameter.DtoRest.FinisherPerHankRest;
import com.unitechApi.MachineSetParameter.ExcelService.FinisherPerHankExcelService;
import com.unitechApi.MachineSetParameter.model.FinisherperHank;
import com.unitechApi.MachineSetParameter.repository.FinisherperHankRepository;
import com.unitechApi.MachineSetParameter.service.FinisherPerHankService;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddFinisherMachine;
import com.unitechApi.addmachine.repositroy.AddFinisherRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.TimeExtendException;
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
@RequestMapping("/unitech/api/v1/machine/finisherperhank")
public class FinisherHankController {

    private final FinisherPerHankService finisherPerHankService;
    private final FinisherperHankRepository finisherperHankRepository;
    private final AddFinisherRepository addfinisherRepository;

    public FinisherHankController(FinisherPerHankService finisherPerHankService, FinisherperHankRepository finisherperHankRepository, AddFinisherRepository addfinisherRepository) {
        this.finisherPerHankService = finisherPerHankService;
        this.finisherperHankRepository = finisherperHankRepository;
        this.addfinisherRepository = addfinisherRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<FinisherperHank> SaveData(@RequestBody FinisherperHank finisherperHank) {
        return new ResponseEntity<>(finisherPerHankService.save(finisherperHank), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<FinisherperHank>> findByid(@PathVariable Long id) {
        return ResponseEntity.ok(finisherperHankRepository.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> FindData(@RequestParam Date start, @RequestParam Date end,
                                      @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<FinisherperHank> finisherperHanks = finisherPerHankService.FindData(start, end, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(finisherperHanks, pagination), HttpStatus.OK);
    }

    @GetMapping("/searchsingle")
    public ResponseEntity<?> ParticularDate(@RequestParam Date start, @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<FinisherperHank> bloowRooms = finisherPerHankService.FindBySingleDate(start, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(bloowRooms, pagination), HttpStatus.OK);
    }

    @PutMapping("{fh_id}/update/{f_id}")
    public FinisherperHank updateid(@PathVariable Long fh_id, @PathVariable Long f_id) {
        AddFinisherMachine addFinisherMachine = addfinisherRepository.findById(fh_id).get();
        FinisherperHank finisherperHank = finisherperHankRepository.findById(f_id).get();
        finisherperHank.updateid(addFinisherMachine);
        return finisherperHankRepository.save(finisherperHank);
    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportToExcel(@RequestParam Date start,
                              @RequestParam Date end, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=FinisherHankData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<FinisherperHank> ListData = finisherPerHankService.ExcelDateToDateReport(start, end);
        ListData.forEach(finisherperHank -> System.out.println(finisherperHank));

        FinisherPerHankExcelService c = new FinisherPerHankExcelService(ListData);
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
        String headerValue = "attachment; filename=FinisherHankData_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<FinisherperHank> ListData = finisherPerHankService.ExcelDateToPerDateReport(start);
        ListData.forEach(finisherperHank -> System.out.println(finisherperHank));

        FinisherPerHankExcelService c = new FinisherPerHankExcelService(ListData);
        c.export(response);
    }

    @PatchMapping("/updateShiftAOne/{id}")
    public ResponseEntity<?> UpdateShiftAOne(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("08:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("02:20:00 PM")))
        ) {
            FinisherperHank finisherperHank = finisherperHankRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            FinisherPerHankRest finisherPerHankRest = mapPersitenceToRestmodel(finisherperHank);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursOne":
                        finisherPerHankRest.setShift_a_sixHoursOne(values);
                }
            });
            mapRestModelToPersistenceShiftAOne(finisherperHank, finisherPerHankRest);
            finisherperHankRepository.save(finisherperHank);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftAOne(FinisherperHank finisherperHank, FinisherPerHankRest finisherPerHankRest) {
        finisherperHank.setMachineId(finisherPerHankRest.getId());
        finisherperHank.setShift_a_sixHoursOne(finisherPerHankRest.getShift_a_sixHoursOne());
        finisherperHank.setAvervg_difference_a_sixHoursOne((finisherperHank.getShift_a_sixHoursOne() - finisherperHank.getMachineefficencykgdfper6hours()
        ) / finisherperHank.getMachineefficencykgdfper6hours() * 100);
        finisherperHank.setTotal_shift_prod_a(finisherperHank.getShift_a_sixHoursOne() + finisherperHank.getShift_a_sixHoursTwo());
        finisherperHank.setTotal_shift_prod_b(finisherperHank.getShift_b_sixHoursOne() + finisherperHank.getShift_b_sixHoursTwo());

        finisherperHank.setActual_producation(finisherperHank.getTotal_shift_prod_b() + finisherperHank.getTotal_shift_prod_a());

        finisherperHank.setEfficiency((finisherperHank.getActual_producation() / finisherperHank.getMachineefficencykgdfperday()) * 100);
        finisherperHank.setTarget_prod_variance_kg(finisherperHank.getMachineefficencykgdfperday() - finisherperHank.getActual_producation());
        finisherperHank.setTarget_prod_variance(((finisherperHank.getActual_producation() -
                finisherperHank.getMachineefficencykgdfperday())
                / finisherperHank.getMachineefficencykgdfperday()) * 100);
    }

    @PatchMapping("/updateShiftATwo/{id}")
    public ResponseEntity<?> UpdateShiftATwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("02:00:00 PM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("08:20:00 PM")))
        ) {
            FinisherperHank finisherperHank = finisherperHankRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            FinisherPerHankRest finisherPerHankRest = mapPersitenceToRestmodel(finisherperHank);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_a_sixHoursTwo":
                        finisherPerHankRest.setShift_a_sixHoursTwo(values);
                }
            });
            mapRestModelToPersistenceShiftATwo(finisherperHank, finisherPerHankRest);
            finisherperHankRepository.save(finisherperHank);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftATwo(FinisherperHank finisherperHank, FinisherPerHankRest finisherPerHankRest) {
        finisherperHank.setMachineId(finisherPerHankRest.getId());
        finisherperHank.setShift_a_sixHoursTwo(finisherPerHankRest.getShift_a_sixHoursTwo());
        finisherperHank.setAvervg_difference_a_sixHoursTwo((finisherperHank.getShift_a_sixHoursTwo() - finisherperHank.getMachineefficencykgdfper6hours()) /
                finisherperHank.getMachineefficencykgdfper6hours() * 100);
        finisherperHank.setTotal_shift_prod_a(finisherperHank.getShift_a_sixHoursOne() + finisherperHank.getShift_a_sixHoursTwo());
        finisherperHank.setTotal_shift_prod_b(finisherperHank.getShift_b_sixHoursOne() + finisherperHank.getShift_b_sixHoursTwo());

        finisherperHank.setActual_producation(finisherperHank.getTotal_shift_prod_b() + finisherperHank.getTotal_shift_prod_a());

        finisherperHank.setEfficiency((finisherperHank.getActual_producation() / finisherperHank.getMachineefficencykgdfperday()) * 100);
        finisherperHank.setTarget_prod_variance_kg(finisherperHank.getMachineefficencykgdfperday() - finisherperHank.getActual_producation());
        finisherperHank.setTarget_prod_variance(((finisherperHank.getActual_producation() -
                finisherperHank.getMachineefficencykgdfperday())
                / finisherperHank.getMachineefficencykgdfperday()) * 100);
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
            FinisherperHank finisherperHank = finisherperHankRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            FinisherPerHankRest finisherPerHankRest = mapPersitenceToRestmodel(finisherperHank);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursOne":
                        finisherPerHankRest.setShift_b_sixHoursOne(values);
                }
            });
            mapRestModelToPersistenceShiftBOne(finisherperHank, finisherPerHankRest);
            finisherperHankRepository.save(finisherperHank);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftBOne(FinisherperHank finisherperHank, FinisherPerHankRest finisherPerHankRest) {
        finisherperHank.setMachineId(finisherPerHankRest.getId());
        finisherperHank.setShift_b_sixHoursOne(finisherPerHankRest.getShift_b_sixHoursOne());
        finisherperHank.setAvervg_difference_b_sixHoursOne((finisherperHank.getShift_b_sixHoursOne() - finisherperHank.getMachineefficencykgdfper6hours()
        ) / finisherperHank.getMachineefficencykgdfper6hours() * 100);
        finisherperHank.setTotal_shift_prod_a(finisherperHank.getShift_a_sixHoursOne() + finisherperHank.getShift_a_sixHoursTwo());
        finisherperHank.setTotal_shift_prod_b(finisherperHank.getShift_b_sixHoursOne() + finisherperHank.getShift_b_sixHoursTwo());

        finisherperHank.setActual_producation(finisherperHank.getTotal_shift_prod_b() + finisherperHank.getTotal_shift_prod_a());

        finisherperHank.setEfficiency((finisherperHank.getActual_producation() / finisherperHank.getMachineefficencykgdfperday()) * 100);
        finisherperHank.setTarget_prod_variance_kg(finisherperHank.getMachineefficencykgdfperday() - finisherperHank.getActual_producation());
        finisherperHank.setTarget_prod_variance(((finisherperHank.getActual_producation() -
                finisherperHank.getMachineefficencykgdfperday())
                / finisherperHank.getMachineefficencykgdfperday()) * 100);
    }

    @PatchMapping("/updateShiftBTwo/{id}")
    public ResponseEntity<?> UpdateShiftBTwo(@PathVariable Long id, @RequestBody Map<String, Float> reading) {
        String timepattern = "hh:mm:ss a";
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timepattern);
        if (LocalTime.now().isAfter(LocalTime.from(timeformat.parse("02:00:00 AM"))) &&
                LocalTime.now().isBefore(LocalTime.from(timeformat.parse("08:20:00 AM")))
        ) {
            FinisherperHank finisherperHank = finisherperHankRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
            FinisherPerHankRest finisherPerHankRest = mapPersitenceToRestmodel(finisherperHank);
            reading.forEach((changes, values) -> {
                switch (changes) {
                    case "shift_b_sixHoursTwo":
                        finisherPerHankRest.setShift_b_sixHoursTwo(values);
                }
            });
            mapRestModelToPersistenceShiftBTwo(finisherperHank, finisherPerHankRest);
            finisherperHankRepository.save(finisherperHank);
        } else {
            throw new TimeExtendException("સમય વયો ગયો ભાઈ = " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern)) + " થોડાક ટાઈમે આવો");
        }
        return new ResponseEntity<>(new MessageResponse("Reading Update in LapFormer " + LocalTime.now().format(DateTimeFormatter.ofPattern(timepattern))), HttpStatus.OK);
    }

    private void mapRestModelToPersistenceShiftBTwo(FinisherperHank finisherperHank, FinisherPerHankRest finisherPerHankRest) {
        finisherperHank.setMachineId(finisherPerHankRest.getId());
        finisherperHank.setShift_b_sixHoursTwo(finisherPerHankRest.getShift_b_sixHoursTwo());
        finisherperHank.setAvervg_difference_b_sixHoursTwo((finisherperHank.getShift_b_sixHoursTwo() - finisherperHank.getMachineefficencykgdfper6hours()
        ) / finisherperHank.getMachineefficencykgdfper6hours() * 100);
        finisherperHank.setTotal_shift_prod_a(finisherperHank.getShift_a_sixHoursOne() + finisherperHank.getShift_a_sixHoursTwo());
        finisherperHank.setTotal_shift_prod_b(finisherperHank.getShift_b_sixHoursOne() + finisherperHank.getShift_b_sixHoursTwo());

        finisherperHank.setActual_producation(finisherperHank.getTotal_shift_prod_b() + finisherperHank.getTotal_shift_prod_a());

        finisherperHank.setEfficiency((finisherperHank.getActual_producation() / finisherperHank.getMachineefficencykgdfperday()) * 100);
        finisherperHank.setTarget_prod_variance_kg(finisherperHank.getMachineefficencykgdfperday() - finisherperHank.getActual_producation());
        finisherperHank.setTarget_prod_variance(((finisherperHank.getActual_producation() -
                finisherperHank.getMachineefficencykgdfperday())
                / finisherperHank.getMachineefficencykgdfperday()) * 100);
    }

    private FinisherPerHankRest mapPersitenceToRestmodel(FinisherperHank finisherperHank) {
        FinisherPerHankRest finisherPerHankRest = new FinisherPerHankRest();
        finisherPerHankRest.setId(finisherperHank.getMachineId());

        // data shift A  part one Reading

        finisherPerHankRest.setShift_a_sixHoursOne(finisherperHank.getShift_a_sixHoursOne());
        finisherPerHankRest.setAvervg_difference_a_sixHoursOne(finisherperHank.getAvervg_difference_a_sixHoursOne());

        // data shift A  part second Reading

        finisherPerHankRest.setShift_a_sixHoursTwo(finisherperHank.getShift_a_sixHoursTwo());
        finisherPerHankRest.setAvervg_difference_a_sixHoursTwo(finisherperHank.getAvervg_difference_a_sixHoursTwo());
        finisherPerHankRest.setTotal_shift_prod_a(finisherperHank.getTotal_shift_prod_a());

        // data shift B  part one Reading

        finisherPerHankRest.setShift_b_sixHoursOne(finisherperHank.getShift_b_sixHoursOne());
        finisherPerHankRest.setAvervg_difference_b_sixHoursOne(finisherperHank.getAvervg_difference_b_sixHoursOne());

        // data shift B  part second Reading

        finisherPerHankRest.setShift_b_sixHoursTwo(finisherperHank.getShift_b_sixHoursTwo());
        finisherPerHankRest.setAvervg_difference_b_sixHoursTwo(finisherperHank.getAvervg_difference_b_sixHoursTwo());

        // total production Reading

        finisherPerHankRest.setActual_producation(finisherperHank.getActual_producation());
        finisherPerHankRest.setEfficiency(finisherperHank.getEfficiency());
        finisherPerHankRest.setTarget_prod_variance_kg(finisherperHank.getTarget_prod_variance_kg());
        finisherPerHankRest.setTarget_prod_variance(finisherperHank.getTarget_prod_variance());
        return finisherPerHankRest;
    }
}

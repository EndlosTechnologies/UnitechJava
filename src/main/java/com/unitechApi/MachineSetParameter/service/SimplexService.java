package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.Simplex;
import com.unitechApi.MachineSetParameter.repository.SimplexRepository;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SimplexService {
    private final SimplexRepository simplexRepository;
    private static final float CONSTANT = (float) 7.2;

    public SimplexService(SimplexRepository simplexRepository) {
        this.simplexRepository = simplexRepository;
    }

    DecimalFormat df = new DecimalFormat("#.###");

    /*
     * save Data with necessary calculation
     * */
    public Simplex SaveData(Simplex simplex) {
        simplex.setTPI(Float.parseFloat(df.format((float) (simplex.getTM() * Math.sqrt(simplex.getRovingHank())))));
        log.info(" TPI {}", simplex.getTPI());
        simplex.setProductionSpindle8hours(Float.parseFloat(df.format(CONSTANT * simplex.getSpeedRpm() * simplex.getMachineEfficiency() / (simplex.getTPI() * simplex.getRovingHank() * 1000 * 100))));
        simplex.setConversionTo12HoursSpindleShift(Float.parseFloat(df.format((simplex.getProductionSpindle8hours() / 8 * 12))));
        simplex.setProductionMachine200SpindlesMachineKgPer12Hours(Float.parseFloat(df.format(simplex.getConversionTo12HoursSpindleShift() * 200)));
        simplex.setProductionSpindle8hoursHank(Float.parseFloat(df.format((1 * simplex.getSpeedRpm() * simplex.getMachineEfficiency() / (63 * simplex.getTPI() * 100)))));
        simplex.setProductionMachine200SpindlesMachineHankPer12Hours(Float.parseFloat(df.format(simplex.getProductionSpindle8hoursHank() * 200)));
        simplex.setConversionTo12HoursSpindleShiftHank(Float.parseFloat(df.format((simplex.getProductionSpindle8hoursHank() / 8) * 12)));
        simplex.setConversionTo6HoursSpindleShift(Float.parseFloat(df.format(simplex.getProductionSpindle8hours() * 6 / 8)));
        simplex.setConversionTo6HoursMachineShiftKgs(Float.parseFloat(df.format(simplex.getConversionTo6HoursSpindleShift() * 200)));
        simplex.setConversionTo24HoursMachineShiftKgs(Float.parseFloat(df.format(simplex.getConversionTo6HoursMachineShiftKgs() * 4)));
        simplex.setConversionTo24HoursMachineShiftHank(Float.parseFloat(df.format(simplex.getConversionTo12HoursSpindleShiftHank() * 2)));
        log.info("{ } Simplex data ", simplex);
        return simplexRepository.save(simplex);
    }
    /*
     * get All Added Reading  from MachineReadingParameter schema
     * */
    public List<Simplex> ViewData() {
        return simplexRepository.findAll();

    }

    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */
    public void DeleteReading(Long id) {
        try {
            simplexRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);

        }

    }

    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<Simplex> FindByData(Long id) {
        return Optional.ofNullable(simplexRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    /*
     * parameter Start CreatedDate and End CreatedDate
     * get  Data By CreatedDate
     * if data has not in the database then throw an exception DateMisMatchException
     * */
    public List<Simplex> FindData(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return simplexRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o->o.getSimplex().getId()))
                .collect(Collectors.toList());
    }

    /*
     * parameter Start CreatedDate
     * get  Data By CreatedDate
     * if data has not in the database then throw an exception DateMisMatchException
     */
    public List<Simplex> FindBySingleDate(Date start) {
        return simplexRepository.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(o -> o.getSimplex().getId()))
                .collect(Collectors.toList());
    }

    public List<Simplex> ExcelDateToPerDateReport(Date start) {
        return simplexRepository.findByShiftdate(start)
                .stream()
                .sorted(Comparator.comparing(o -> o.getSimplex().getId()))
                .collect(Collectors.toList());
    }

    public List<Simplex> ExcelDateToDateReport(Date start, Date end) {
        return simplexRepository.findByShiftdateBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o -> o.getSimplex().getId()))
                .collect(Collectors.toList());
    }
}

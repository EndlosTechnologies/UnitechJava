package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.Simplex;
import com.unitechApi.MachineSetParameter.repository.SimplexRepository;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SimplexService {
    private final SimplexRepository simplexRepository;
    private static final float CONSTANT = (float) 7.2;

    public SimplexService(SimplexRepository simplexRepository) {
        this.simplexRepository = simplexRepository;
    }

    DecimalFormat df = new DecimalFormat("#.###");

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

    public Object ViewData() {
        return simplexRepository.findAll();

    }

    public void DeleteReading(Long id) {
        try {
            simplexRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);

        }

    }

    public Optional<Simplex> FindByData(Long id) {
        return Optional.ofNullable(simplexRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public Page<Simplex> FindData(Date start, Date end, Pagination pagination) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return simplexRepository.findByCreatedAtBetween(start, end, pagination.getpageble());
    }

    public Page<Simplex> FindBySingleDate(Date start, Pagination pagination) {
        return simplexRepository.findByCreatedAt(start, pagination.getpageble());
    }

    public List<Simplex> ExcelDateToPerDateReport(Date start) {
        return simplexRepository.findByShiftdate(start);
    }

    public List<Simplex> ExcelDateToDateReport(Date start, Date end) {
        return simplexRepository.findByShiftdateBetween(start, end);
    }
}

package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.RingFrame;
import com.unitechApi.MachineSetParameter.repository.RingFrameRepossitory;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RingframesService {
    private static final float COSTANT = (float) 7.2;
    private static final int SPINDLE = 1440;
    private final RingFrameRepossitory ringFrameRepossitory;

    public RingframesService(RingFrameRepossitory ringFrameRepossitory) {
        this.ringFrameRepossitory = ringFrameRepossitory;
    }

    DecimalFormat df = new DecimalFormat("#.###");

    public RingFrame SaveData(RingFrame ringFrame) {
        df.setMaximumFractionDigits(3);

        ringFrame.setTPI(Float.parseFloat(df.format( (ringFrame.getTM() * (Math.sqrt(ringFrame.getRingFrameCount()))))));
        ringFrame.setProductionSpindleGrams(Float.parseFloat((df.format(COSTANT * ringFrame.getSpindleRpm() * ringFrame.getMachineEfficiency()
                / (ringFrame.getRingFrameCount() * ringFrame.getTPI() * 100)))));
        ringFrame.setProductionSpindle8HoursKg(Float.parseFloat(df.format(ringFrame.getProductionSpindleGrams() * SPINDLE / 1000)));
        ringFrame.setProductionSpindle24HoursKg(Float.parseFloat(df.format(ringFrame.getProductionSpindle8HoursKg() * 3)));
        ringFrame.setTotalLoss(Float.parseFloat(df.format(ringFrame.getPneumafilWaste() + ringFrame.getIdleSpindle() + ringFrame.getDoffingLoss())));
        ringFrame.setTotalLossKg(Float.parseFloat(df.format((ringFrame.getProductionSpindle24HoursKg() * ringFrame.getTotalLoss()) / 100)));
        ringFrame.setNetProduction(Float.parseFloat(df.format(ringFrame.getProductionSpindle24HoursKg() - ringFrame.getTotalLossKg())));
        ringFrame.setProductionSpindle2HoursKg(Float.parseFloat(df.format(ringFrame.getNetProduction() / 12)));
        log.info(" { } info Ring Frame Data ", ringFrame);
        return ringFrameRepossitory.save(ringFrame);
    }

    public Object ViewData() {
        return ringFrameRepossitory.findAll();

    }

    public void DeleteReading(Long id) {
        try {
            ringFrameRepossitory.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<RingFrame> FindByData(Long id) {
        return Optional.ofNullable(ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<RingFrame> FindData(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return ringFrameRepossitory.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o -> o.getRingframe().getId()))
                .collect(Collectors.toList());
    }

    public List<RingFrame> FindBySingleDate(Date start) {
        return ringFrameRepossitory.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(o -> o.getRingframe().getId()))
                .collect(Collectors.toList());
    }

    public List<RingFrame> ExcelDateToPerDateReport(Date start) {
        return ringFrameRepossitory.findByShiftdate(start).stream().sorted(Comparator.comparing(o -> o.getRingframe().getId())).collect(Collectors.toList());
    }

    public List<RingFrame> ExcelDateToDateReport(Date start, Date end) {
        return ringFrameRepossitory.findByShiftdateBetween(start, end).stream().sorted(Comparator.comparing(o -> o.getRingframe().getId())).collect(Collectors.toList());
    }
}

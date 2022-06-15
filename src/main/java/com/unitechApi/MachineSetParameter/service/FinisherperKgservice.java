package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.FinisherperKg;
import com.unitechApi.MachineSetParameter.repository.FinisherRepository;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FinisherperKgservice {
    public static final float CONSTANT = (float) 0.2835;
    private final FinisherRepository finisherRepository;

    public FinisherperKgservice(FinisherRepository finisherRepository) {
        this.finisherRepository = finisherRepository;
    }

    DecimalFormat df = new DecimalFormat("#.###");

    public FinisherperKg SaveData(FinisherperKg finisherperKg) {
        df.setMaximumFractionDigits(3);
        finisherperKg.setProductiononratekgdfper8hour(Float.parseFloat(df.format((CONSTANT * finisherperKg.getDeliveryspeed() * finisherperKg.getMachineefficency()) / (finisherperKg.getSilverhank() * 100))));
        finisherperKg.setMachineefficencykgdfper6hours(Float.parseFloat(df.format(finisherperKg.getProductiononratekgdfper8hour() * 6 / 8)));
        finisherperKg.setMachineefficencykgdfperday(Float.parseFloat(df.format(finisherperKg.getProductiononratekgdfper8hour() * 24 / 8)));
        log.debug("{ } Simplex Data ", finisherperKg);
        return finisherRepository.save(finisherperKg);
    }

    public Object ViewData() {
        return finisherRepository.findAll();
    }

    public void DeleteReading(Long id) {
        try {
            finisherRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<FinisherperKg> FindByData(Long id) {
        return Optional.ofNullable(finisherRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<FinisherperKg> FindData(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return finisherRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o -> o.getFinisherMachine().getId()))
                .collect(Collectors.toList());

    }

    public List<FinisherperKg> FindBySingleDate(Date start) {
        return finisherRepository.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(o -> o.getFinisherMachine().getId()))
                .collect(Collectors.toList());
    }

    public List<FinisherperKg> ExcelDateToDateReport(Date start, Date end) {
        return finisherRepository.findByShiftdateBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o -> o.getFinisherMachine().getId()))
                .collect(Collectors.toList());
    }

    public List<FinisherperKg> ExcelDateToPerDateReport(Date start) {
        return finisherRepository.findByShiftdate(start)
                .stream()
                .sorted(Comparator.comparing(o -> o.getFinisherMachine().getId()))
                .collect(Collectors.toList());
    }
}

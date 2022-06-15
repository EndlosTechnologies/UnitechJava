package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.FinisherperHank;
import com.unitechApi.MachineSetParameter.repository.FinisherperHankRepository;
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

@Slf4j
@Service
public class FinisherPerHankService {
    public static final float CONSTANT = (float) 0.6251;
    private final FinisherperHankRepository finisherperHankRepository;

    public FinisherPerHankService(FinisherperHankRepository finisherperHankRepository) {
        this.finisherperHankRepository = finisherperHankRepository;
    }

    DecimalFormat df = new DecimalFormat("#.###");

    public FinisherperHank save(FinisherperHank finisherperHank) {
        df.setMaximumFractionDigits(3);
        finisherperHank.setProductiononratekgdfper8hour(Float.parseFloat(df.format(CONSTANT * finisherperHank.getDeliveryspeed() * finisherperHank.getMachineefficency() / 100)));
        finisherperHank.setMachineefficencykgdfper6hours(Float.parseFloat(df.format(finisherperHank.getProductiononratekgdfper8hour() / 8 * 6)));
        finisherperHank.setMachineefficencykgdfperday(Float.parseFloat(df.format(finisherperHank.getProductiononratekgdfper8hour() * 3)));
        log.info("{ } Finisher Data in Hank ", finisherperHank);
        return finisherperHankRepository.save(finisherperHank);
    }

    public Object ViewData() {
        return finisherperHankRepository.findAll();

    }

    public void DeleteReading(Long id) {
        try {
            finisherperHankRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<FinisherperHank> FindByData(Long id) {
        return Optional.ofNullable(finisherperHankRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<FinisherperHank> FindData(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return finisherperHankRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o->o.getFinisherhankMachine().getId()))
                .collect(Collectors.toList());
    }

    public List<FinisherperHank> FindBySingleDate(Date start) {
        return finisherperHankRepository.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(o->o.getFinisherhankMachine().getId()))
                .collect(Collectors.toList());
    }

    public List<FinisherperHank> ExcelDateToPerDateReport(Date start) {
        return finisherperHankRepository.findByShiftdate(start)
                .stream()
                .sorted(Comparator.comparing(o->o.getFinisherhankMachine().getId()))
                .collect(Collectors.toList());
    }

    public List<FinisherperHank> ExcelDateToDateReport(Date start, Date end) {
        return finisherperHankRepository.findByShiftdateBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o->o.getFinisherhankMachine().getId()))
                .collect(Collectors.toList());
    }
}

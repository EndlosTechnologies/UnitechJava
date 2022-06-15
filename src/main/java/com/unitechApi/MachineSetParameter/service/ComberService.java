package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.Comber;
import com.unitechApi.MachineSetParameter.repository.ComberRepository;
import com.unitechApi.addmachine.model.AddComber;
import com.unitechApi.addmachine.repositroy.AddComberRepository;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ComberService {
    public static final float CONSTANT = (float) 0.0035;
    private final ComberRepository comberRepository;
    private final AddComberRepository addComberRepository;

    public ComberService(ComberRepository comberRepository, AddComberRepository addComberRepository) {
        this.comberRepository = comberRepository;
        this.addComberRepository = addComberRepository;
    }

    DecimalFormat df = new DecimalFormat("#.###");

    public Comber SaveData(Comber comber) {
        df.setMaximumFractionDigits(3);
        comber.setProductioMc8Hour(Float.parseFloat(df.format((CONSTANT * comber.getComberSpeedRpm() * comber.getFeedNip() * comber.getLapWeight() * comber.getMachineEfficency() * (100 - comber.getNoil())) / (100 * 100))));
        comber.setProductioMc6Hour(Float.parseFloat(df.format(comber.getProductioMc8Hour() * 6 / 8)));
        comber.setProductioMcShift(Float.parseFloat(df.format(comber.getProductioMc8Hour() / 8 * 12)));
        comber.setProductioMc24Hour(Float.parseFloat(df.format(comber.getProductioShift() * 2)));
        log.info("{ } Comber Data ", comber);
        return comberRepository.save(comber);
    }

    public Comber FindById(Long id) {
        Optional<Comber> comber = comberRepository.findById(id);
        if (!comber.isPresent()) {
            throw new UserNotFound("Comber Not Not Found");
        }
        return comber.get();

    }

    public List<Comber> FindByDate(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return comberRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(O->O.getAddcomber().getId()))
                .collect(Collectors.toList());
    }

    public List<Comber> FindBySingleDate(Date start) {
        return comberRepository.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(O->O.getAddcomber().getId()))
                .collect(Collectors.toList());
    }

    public Comber updateid(Long co_r_id, Long co_a_id) {
        AddComber addComber = addComberRepository.findById(co_a_id).get();
        Comber comber = comberRepository.findById(co_r_id).get();
        comber.updateid(addComber);
        return comberRepository.save(comber);
    }

    public List<Comber> ExcelDateToDateReport(Date start, Date end) {
        return comberRepository.findByShiftdateBetween(start, end);
    }

    public List<Comber> ExcelDateToPerDateReport(Date start) {
        return comberRepository.findByShiftdate(start);
    }
}

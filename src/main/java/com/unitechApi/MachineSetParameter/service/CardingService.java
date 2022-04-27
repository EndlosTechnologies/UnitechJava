package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.Carding;
import com.unitechApi.MachineSetParameter.repository.CardingRepository;
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

@Slf4j
@Service
public class CardingService {
    private final CardingRepository cardingRepository;

    public CardingService(CardingRepository cardingRepository) {
        this.cardingRepository = cardingRepository;
    }

    public static final float CONSTANT = (float) 0.0354;
    DecimalFormat df = new DecimalFormat("#.###");

    public Carding SaveData(Carding carding) {
        df.setMaximumFractionDigits(3);
        carding.setProductiononratekgcardperhour(Float.parseFloat(df.format(CONSTANT * carding.getDeliveryspeed() * carding.getMachineefficency() / (carding.getSilverhank() * 100))));
        carding.setMachineefficencykgcardpershift(Float.parseFloat(df.format(carding.getProductiononratekgcardperhour() * 12)));
        carding.setMachineefficencykgcardpersixhours(Float.parseFloat(df.format(carding.getMachineefficencykgcardpershift() / 2)));
        carding.setMachineefficencykgcardperday(Float.parseFloat(df.format(carding.getMachineefficencykgcardpershift() * 2)));
        log.info("{ } Carding Data ", carding);
        return cardingRepository.save(carding);
    }

    public List<Carding> ViewData() {
        return cardingRepository.findAll();
    }
    public void DeleteReading(Long id) {
        try {
            cardingRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<Carding> FindByData(Long id) {
        return Optional.ofNullable(cardingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public Page<Carding> FindByDate(Date start, Date end, Pagination pagination) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return cardingRepository.findByCreatedAtBetween(start, end, pagination.getpageble());
    }

    public Page<Carding> FindBySingleDate(Date start, Pagination pagination) {
        return cardingRepository.findByCreatedAt(start, pagination.getpageble());
    }

    public List<Carding> ExcelDateToDateReport(Date start, Date end) {
        return cardingRepository.findByShiftdateBetween(start, end);
    }

    public List<Carding> ExcelDateToPerDateReport(Date start) {
        return cardingRepository.findByShiftdate(start);
    }
}

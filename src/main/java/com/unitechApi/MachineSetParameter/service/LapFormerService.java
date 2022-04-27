package com.unitechApi.MachineSetParameter.service;


import com.unitechApi.MachineSetParameter.model.LapFormer;
import com.unitechApi.MachineSetParameter.repository.LapFormerRepository;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddLapFormer;
import com.unitechApi.addmachine.repositroy.AddLapFormerRepository;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.UserNotFound;
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
public class LapFormerService {
    public static final float CONSTANT = 480;
    private final LapFormerRepository lapFormerRepository;
    private final AddLapFormerRepository addlapFormerRepository;

    public LapFormerService(LapFormerRepository lapFormerRepository, AddLapFormerRepository addlapFormerRepository) {
        this.lapFormerRepository = lapFormerRepository;
        this.addlapFormerRepository = addlapFormerRepository;
    }

    DecimalFormat df = new DecimalFormat("#.###");

    public LapFormer SaveData(LapFormer lapFormer) {
        df.setMaximumFractionDigits(3);
        lapFormer.setProductiononratekgcard8perhour(Float.parseFloat(df.format(CONSTANT * lapFormer.getDeliveryspeed() * lapFormer.getLapWeight() * lapFormer.getMachineefficency() / (1000 * 100))));
        lapFormer.setMachineefficencykgcardpersixhours(Float.parseFloat(df.format(lapFormer.getProductiononratekgcard8perhour() * 6 / 8)));
        lapFormer.setMachineefficencykgcardperday(Float.parseFloat(df.format(lapFormer.getMachineefficencykgcardpersixhours() * 4)));
        log.debug("{ } Simplex Data ", lapFormer);
        return lapFormerRepository.save(lapFormer);
    }

    public LapFormer FindById(Long id) {
        Optional<LapFormer> lapFormer = lapFormerRepository.findById(id);
        if (!lapFormer.isPresent()) {
            throw new UserNotFound("Comber Not Not Found");
        }
        return lapFormer.get();
    }

    public Page<LapFormer> DateToDateSearch(Date start, Date end, Pagination pagination) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return lapFormerRepository.findByCreatedAtBetween(start, end, pagination.getpageble());
    }

    public Page<LapFormer> FindBySingleDate(Date start, Pagination pagination) {
        return lapFormerRepository.findByCreatedAt(start, pagination.getpageble());
    }

    public LapFormer UpdateId(Long l_a_id, Long l_r_id) {
        AddLapFormer addLapFormer = addlapFormerRepository.findById(l_a_id).get();
        LapFormer lapFormer = lapFormerRepository.findById(l_r_id).get();
        lapFormer.updateid(addLapFormer);
        return lapFormerRepository.save(lapFormer);
    }

    public List<LapFormer> ExcelDateToDateReport(Date start, Date end) {
        return lapFormerRepository.findByShiftdateBetween(start, end);
    }

    public List<LapFormer> ExcelDateToPerDateReport(Date start) {
        return lapFormerRepository.findByShiftdate(start);
    }
}

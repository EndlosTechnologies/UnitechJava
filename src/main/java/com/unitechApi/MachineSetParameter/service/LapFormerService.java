package com.unitechApi.MachineSetParameter.service;


import com.unitechApi.MachineSetParameter.model.BloowRoom;
import com.unitechApi.MachineSetParameter.model.LapFormer;
import com.unitechApi.MachineSetParameter.repository.LapFormerRepository;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.addmachine.model.AddLapFormer;
import com.unitechApi.addmachine.repositroy.AddLapFormerRepository;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
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
public class LapFormerService {
    public static final float CONSTANT = 480;
    private final LapFormerRepository lapFormerRepository;
    private final AddLapFormerRepository addlapFormerRepository;

    public LapFormerService(LapFormerRepository lapFormerRepository, AddLapFormerRepository addlapFormerRepository) {
        this.lapFormerRepository = lapFormerRepository;
        this.addlapFormerRepository = addlapFormerRepository;
    }

    DecimalFormat df = new DecimalFormat("#.###");

    /*
     * save Data with necessary calculation
     * */
    public LapFormer SaveData(LapFormer lapFormer) {
        df.setMaximumFractionDigits(3);
        lapFormer.setProductiononratekgcard8perhour(Float.parseFloat(df.format(CONSTANT * lapFormer.getDeliveryspeed() * lapFormer.getLapWeight() * lapFormer.getMachineefficency() / (1000 * 100))));
        lapFormer.setMachineefficencykgcardpersixhours(Float.parseFloat(df.format(lapFormer.getProductiononratekgcard8perhour() * 6 / 8)));
        lapFormer.setMachineefficencykgcardperday(Float.parseFloat(df.format(lapFormer.getMachineefficencykgcardpersixhours() * 4)));
        log.debug("{ } Simplex Data ", lapFormer);
        return lapFormerRepository.save(lapFormer);
    }

    /*
     * get All Added Reading  from MachineReadingParameter schema
     * */
    public List<LapFormer> ViewData() {
        return lapFormerRepository.findAll();
    }
    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */

    public LapFormer FindById(Long id) {
        Optional<LapFormer> lapFormer = lapFormerRepository.findById(id);
        if (!lapFormer.isPresent()) {
            throw new UserNotFound("Comber Not Not Found");
        }
        return lapFormer.get();
    }


    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */
    public void DeleteReading(Long id) {
        try {
            lapFormerRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + id + ": ");
        }
    }
    /*
     * parameter Start CreatedDate and End CreatedDate
     * get  Data By CreatedDate
     * if data has not in the database then throw an exception DateMisMatchException
     * */
    public List<LapFormer> DateToDateSearch(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return lapFormerRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o->o.getAddLapFormer().getId()))
                .collect(Collectors.toList());
    }

    /*
     * parameter Start CreatedDate
     * get  Data By CreatedDate
     * if data has not in the database then throw an exception DateMisMatchException
     * */
    public List<LapFormer> FindBySingleDate(Date start) {
        return lapFormerRepository.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(o->o.getAddLapFormer().getId()))
                .collect(Collectors.toList());
    }

    public LapFormer UpdateId(Long l_a_id, Long l_r_id) {
        AddLapFormer addLapFormer = addlapFormerRepository.findById(l_a_id).get();
        LapFormer lapFormer = lapFormerRepository.findById(l_r_id).get();
        lapFormer.updateid(addLapFormer);
        return lapFormerRepository.save(lapFormer);
    }

    public List<LapFormer> ExcelDateToDateReport(Date start, Date end) {
        return lapFormerRepository.findByShiftdateBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o->o.getAddLapFormer().getId()))
                .collect(Collectors.toList());
    }

    public List<LapFormer> ExcelDateToPerDateReport(Date start) {
        return lapFormerRepository.findByShiftdate(start)
                .stream()
                .sorted(Comparator.comparing(o->o.getAddLapFormer().getId()))
                .collect(Collectors.toList());
    }
}

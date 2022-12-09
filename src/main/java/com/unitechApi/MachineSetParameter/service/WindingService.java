package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.Winding;
import com.unitechApi.MachineSetParameter.repository.WindingRepository;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WindingService {
    private final WindingRepository windingRepository;
    public WindingService(WindingRepository windingRepository) {
        this.windingRepository = windingRepository;
    }




    /*
     * save Data with necessary calculation
     * */
    public Winding SaveData(Winding winding) {
        return windingRepository.save(winding);
    }

    /*
     * get All Added Reading  from MachineReadingParameter schema
     * */
    public List<Winding> ViewData() {
        return windingRepository.findAll();

    }
    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */
    public void DeleteReading(Long id) {
        try {
            windingRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw  new ResourceNotFound("data already deleted present " +ResourceNotFound.class);

        }

    }
    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */

    public Optional<Winding> FindByData(Long id) {
        return Optional.ofNullable(windingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    /*
     * parameter Start CreatedDate and End CreatedDate
     * get  Data By CreatedDate
     * if data has not in the database then throw an exception DateMisMatchException
     * */
    public List<Winding> FindData(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }

        return windingRepository.findByCreatedAtBetween(start, end);
    }
    /*
     * parameter Start CreatedDate
     * get  Data By CreatedDate
     * if data has not in the database then throw an exception DateMisMatchException
     * */
    public List<Winding> FindBySingleDate(Date start)
    {
        return  windingRepository.findByCreatedAt(start);
    }
}

package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.Winding;
import com.unitechApi.MachineSetParameter.repository.WindingRepository;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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




    public Winding SaveData(Winding winding) {
        return windingRepository.save(winding);
    }

    public Object ViewData() {
        return windingRepository.findAll();

    }
    public void DeleteReading(Long id) {
        try {
            windingRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw  new ResourceNotFound("data already deleted present " +ResourceNotFound.class);

        }

    }

    public Optional<Winding> FindByData(Long id) {
        return Optional.ofNullable(windingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    public List<Winding> FindData(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }

        return windingRepository.findByCreatedAtBetween(start, end);
    }
    public List<Winding> FindBySingleDate(Date start)
    {
        return  windingRepository.findByCreatedAt(start);
    }
}

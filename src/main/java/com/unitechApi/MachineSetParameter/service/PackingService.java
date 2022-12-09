package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.Packing;
import com.unitechApi.MachineSetParameter.repository.PackingRepository;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PackingService {

    private final PackingRepository packingRepository;

    public PackingService(PackingRepository packingRepository) {
        this.packingRepository = packingRepository;
    }

    /*
     * save Data with necessary calculation
     * */
    public Packing SaveData(Packing packing) {
        return packingRepository.save(packing);
    }

    /*
     * get All Added Reading  from MachineReadingParameter schema
     * */
    public List<Packing> ViewData() {
        return packingRepository.findAll();

    }
    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */

    public void DeleteReading(Long id) {
        try {
            packingRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }

    }

    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */

    public Optional<Packing> FindByData(Long id) {
        return Optional.ofNullable(packingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    /*
     * parameter Start CreatedDate and End CreatedDate
     * get  Data By CreatedDate
     * if data has not in the database then throw an exception DateMisMatchException
     * */

    public List<Packing> FindData(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }

        return packingRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o -> o.getPacking().getId()))
                .collect(Collectors.toList());
    }
    /*
     * parameter Start CreatedDate
     * get  Data By CreatedDate
     * if data has not in the database then throw an exception DateMisMatchException
     * */

    public List<Packing> FindBySingleDate(Date start) {
        return packingRepository.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(o -> o.getPacking().getId()))
                .collect(Collectors.toList());
    }

}

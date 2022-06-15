package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.Utiliity;
import com.unitechApi.MachineSetParameter.repository.UtilityRepository;
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
public class UtilityService {

    private final UtilityRepository utilityRepository;

    public UtilityService(UtilityRepository utilityRepository) {
        this.utilityRepository = utilityRepository;
    }

    public Utiliity SaveData(Utiliity utiliity) {
        return utilityRepository.save(utiliity);
    }

    public Object ViewData() {
        return utilityRepository.findAll().stream().sorted(Comparator.comparing(Utiliity::getMachineId)).collect(Collectors.toList());
    }

    public void DeleteReading(Long id) {
        try {
            utilityRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);

        }

    }

    public Optional<Utiliity> FindByData(Long id) {
        return Optional.ofNullable(utilityRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<Utiliity> FindData(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }

        return utilityRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o->o.getUtillity().getId()))
                .collect(Collectors.toList());
    }

    public List<Utiliity> FindBySingleDate(Date start) {
        return utilityRepository.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(o->o.getUtillity().getId()))
                .collect(Collectors.toList());
    }
}

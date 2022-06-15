package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.Wasteroom;
import com.unitechApi.MachineSetParameter.repository.WasteRoomRepository;
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
public class WasteRoomService {
    private final WasteRoomRepository wasteRoomRepository;

    public WasteRoomService(WasteRoomRepository wasteRoomRepository) {
        this.wasteRoomRepository = wasteRoomRepository;
    }

    public Wasteroom SaveData(Wasteroom wasteroom) {
        return wasteRoomRepository.save(wasteroom);
    }

    public Object ViewData() {
        return wasteRoomRepository.findAll();

    }

    public void DeleteReading(Long id) {
        try {
            wasteRoomRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }

    }

    public Optional<Wasteroom> FindByData(Long id) {
        return Optional.ofNullable(wasteRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<Wasteroom> FindData(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }

        return wasteRoomRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o->o.getWasteroom().getId()))
                .collect(Collectors.toList());
    }

    public List<Wasteroom> FindBySingleDate(Date start ) {
        return wasteRoomRepository.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(o->o.getWasteroom().getId()))
                .collect(Collectors.toList());
    }
}

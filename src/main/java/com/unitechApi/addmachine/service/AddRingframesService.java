package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddRingFramesMachine;
import com.unitechApi.addmachine.repositroy.AddRingFrameRepossitory;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddRingframesService {

    private final AddRingFrameRepossitory ringFrameRepossitory;

    public AddRingframesService(AddRingFrameRepossitory ringFrameRepossitory) {
        this.ringFrameRepossitory = ringFrameRepossitory;
    }

    public AddRingFramesMachine SaveData(AddRingFramesMachine ringFrame) {
        return ringFrameRepossitory.save(ringFrame);
    }

    public Object ViewData() {
        return ringFrameRepossitory.findAll();
    }

    public void DeleteReading(Long id) {
        try {
            ringFrameRepossitory.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<AddRingFramesMachine> FindByData(Long id) {
        return Optional.ofNullable(ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<AddRingFramesMachine> Status(boolean status) {
        return ringFrameRepossitory.findByStatus(status);
    }
}

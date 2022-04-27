package com.unitechApi.addmachine.service;


import com.unitechApi.addmachine.model.AddDrawFramesMachine;
import com.unitechApi.addmachine.repositroy.AddDrawFramesRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddDrawMachienService {

    private final AddDrawFramesRepository addDrawFramesRepository;

    public AddDrawMachienService(AddDrawFramesRepository addDrawFramesRepository) {
        this.addDrawFramesRepository = addDrawFramesRepository;
    }

    public AddDrawFramesMachine savemachineDraw(AddDrawFramesMachine addDrawFramesMachine) {
        return addDrawFramesRepository.save(addDrawFramesMachine);

    }

    public Object ViewData() {
        return addDrawFramesRepository.findAll();
    }

    public void DeleteReading(Long id) {
        try {
            addDrawFramesRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<AddDrawFramesMachine> FindByData(Long id) {
        return Optional.ofNullable(addDrawFramesRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<AddDrawFramesMachine> Status(boolean status) {
        return addDrawFramesRepository.findByStatus(status);
    }
}

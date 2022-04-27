package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddWindingMachine;
import com.unitechApi.addmachine.repositroy.AddWindingRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddWindinfService {

    private final AddWindingRepository windingRepository;

    public AddWindinfService(AddWindingRepository windingRepository) {
        this.windingRepository = windingRepository;
    }

    public AddWindingMachine SaveData(AddWindingMachine winding) {
        return windingRepository.save(winding);
    }

    public Object ViewData() {
        return windingRepository.findAll();
    }

    public void DeleteReading(Long id) {
        try {
            windingRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<AddWindingMachine> FindByData(Long id) {
        return Optional.ofNullable(windingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<AddWindingMachine> Status(boolean status) {
        return windingRepository.findByStatus(status);
    }
}

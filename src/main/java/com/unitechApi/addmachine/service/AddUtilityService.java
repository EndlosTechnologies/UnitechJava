package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddUtillityMachine;
import com.unitechApi.addmachine.repositroy.AddUtilityRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AddUtilityService {

    private final AddUtilityRepository utilityRepository;

    public AddUtilityService(AddUtilityRepository utilityRepository) {
        this.utilityRepository = utilityRepository;
    }

    public AddUtillityMachine SaveData(AddUtillityMachine utiliity) {
        return utilityRepository.save(utiliity);
    }

    public Object ViewData() {
        return utilityRepository.findAll().stream().sorted(Comparator.comparing(AddUtillityMachine::getId));
    }

    public void DeleteReading(Long id) {
        try {
            utilityRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<AddUtillityMachine> FindByData(Long id) {
        return Optional.ofNullable(utilityRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<AddUtillityMachine> Status(boolean status) {
        return utilityRepository.findByStatus(status);
    }
}

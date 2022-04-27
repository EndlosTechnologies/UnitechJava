package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddCardingMachine;
import com.unitechApi.addmachine.repositroy.AddCardingRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AddCardingService {

    private final AddCardingRepository addCardingRepository;

    public AddCardingService(AddCardingRepository addCardingRepository) {
        this.addCardingRepository = addCardingRepository;
    }

    public AddCardingMachine Savecarding(AddCardingMachine addCardingMachine) {
        return addCardingRepository.save(addCardingMachine);
    }

    public Object ViewData() {
        return addCardingRepository.findAll();

    }

    public void DeleteReading(Long id) {
        try {
            addCardingRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    public Optional<AddCardingMachine> FindByData(Long id) {
        return Optional.ofNullable(addCardingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    public List<AddCardingMachine> Status(boolean status) {
        return addCardingRepository.findByStatus(status);
    }
}

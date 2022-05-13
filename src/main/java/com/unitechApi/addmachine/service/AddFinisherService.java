package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddFinisherMachine;
import com.unitechApi.addmachine.repositroy.AddFinisherRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AddFinisherService {

   private final AddFinisherRepository addFinisherRepository;

    public AddFinisherService(AddFinisherRepository addFinisherRepository) {
        this.addFinisherRepository = addFinisherRepository;
    }

    public AddFinisherMachine savemachine(AddFinisherMachine addFinisherMachined) {
        return addFinisherRepository.save(addFinisherMachined);
    }

    public Object ViewData() {
        return addFinisherRepository.findAll().stream().sorted(Comparator.comparing(AddFinisherMachine::getId));
    }
    public void DeleteReading(Long id) {
        try {
            addFinisherRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    public Optional<AddFinisherMachine> FindByData(Long id) {
        return Optional.ofNullable(addFinisherRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    public List<AddFinisherMachine> Status(boolean status)
    {
        return  addFinisherRepository.findByStatus(status);
    }
}

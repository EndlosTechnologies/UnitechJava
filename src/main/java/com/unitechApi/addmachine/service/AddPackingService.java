package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddPackingMachine;
import com.unitechApi.addmachine.repositroy.AddPackingRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class AddPackingService {

    private final AddPackingRepository packingRepository;

    public AddPackingService(AddPackingRepository packingRepository) {
        this.packingRepository = packingRepository;
    }

    public AddPackingMachine SaveData(AddPackingMachine packing) {
        return packingRepository.save(packing);
    }

    public Object ViewData() {
        return packingRepository.findAll();
    }
    public void DeleteReading(Long id) {
        try {
            packingRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    public Optional<AddPackingMachine> FindByData(Long id) {
        return Optional.ofNullable(packingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    public List<AddPackingMachine> Status(boolean status)
    {
        return  packingRepository.findByStatus(status);
    }
}

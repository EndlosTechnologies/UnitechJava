package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddLapFormer;
import com.unitechApi.addmachine.repositroy.AddLapFormerRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AddLapFormerService {

    private final AddLapFormerRepository addLapFormerRepository;

    public AddLapFormerService(AddLapFormerRepository addLapFormerRepository) {
        this.addLapFormerRepository = addLapFormerRepository;
    }

    public AddLapFormer savemachine(AddLapFormer addLapFormer) {
        return addLapFormerRepository.save(addLapFormer);
    }

    public Optional<AddLapFormer> FindByData(Long id) {
        return Optional.ofNullable(addLapFormerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public Object ViewData() {
        return addLapFormerRepository.findAll().stream().sorted(Comparator.comparing(AddLapFormer::getId));
    }

    public List<AddLapFormer> Status(boolean status) {
        return addLapFormerRepository.findByStatus(status);
    }
}

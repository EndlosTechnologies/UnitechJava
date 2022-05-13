package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddRingFramesMachine;
import com.unitechApi.addmachine.model.AddSimplexMAchine;
import com.unitechApi.addmachine.repositroy.AddSimplexRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AddSimplexService {

    private final AddSimplexRepository simplexRepository;

    public AddSimplexService(AddSimplexRepository simplexRepository) {
        this.simplexRepository = simplexRepository;
    }

    public AddSimplexMAchine SaveData(AddSimplexMAchine simplex) {
        return simplexRepository.save(simplex);
    }

    public Object ViewData() {
        return simplexRepository.findAll().stream().sorted(Comparator.comparing(AddSimplexMAchine::getId));
    }

    public void DeleteReading(Long id) {
        try {
            simplexRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<AddSimplexMAchine> FindByData(Long id) {
        return Optional.ofNullable(simplexRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<AddSimplexMAchine> Status(boolean status) {
        return simplexRepository.findByStatus(status);
    }
}

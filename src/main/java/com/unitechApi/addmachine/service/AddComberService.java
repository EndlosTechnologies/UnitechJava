package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddComber;
import com.unitechApi.addmachine.repositroy.AddComberRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AddComberService {

   private final AddComberRepository addComberRepository;

    public AddComberService(AddComberRepository addComberRepository) {
        this.addComberRepository = addComberRepository;
    }

    public AddComber savemachine(AddComber addComber) {
        return addComberRepository.save(addComber);
    }
    public Optional<AddComber> FindByData(Long id) {
        return Optional.ofNullable(addComberRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    public Object ViewData() {
        return addComberRepository.findAll().stream().sorted(Comparator.comparing(AddComber::getId));

    }
    public List<AddComber> Status(boolean status)
    {
        return  addComberRepository.findByStatus(status);
    }
}

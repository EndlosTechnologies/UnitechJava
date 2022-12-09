package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddComber;
import com.unitechApi.addmachine.repositroy.AddComberRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddComberService {

   private final AddComberRepository addComberRepository;

    public AddComberService(AddComberRepository addComberRepository) {
        this.addComberRepository = addComberRepository;
    }

    // *  parameter addComber
    // *  AddComber   data  save in a db
    public AddComber saveComberMachine(AddComber addComber) {
        return addComberRepository.save(addComber);
    }
    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<AddComber> FindByData(Long id) {
        return Optional.ofNullable(addComberRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    /*
     * get All Added Comber MachineDetails from  AddMachine with sorted machineId
     * */
    public List<AddComber> ViewData() {
        return addComberRepository.findAll().stream().sorted(Comparator.comparing(AddComber::getId))
                .collect(Collectors.toList());

    }
    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */
    public void DeleteReading(Long id) {
        try {
            addComberRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    /*
     * parameter Boolean status
     * will be getting All Comber Details from comber machine where the status is true or False
     *
     * */
    public List<AddComber> Status(boolean status)
    {
        return  addComberRepository.findByStatus(status);
    }
}

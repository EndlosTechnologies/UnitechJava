package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddFinisherMachine;
import com.unitechApi.addmachine.repositroy.AddFinisherRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddFinisherService {

   private final AddFinisherRepository addFinisherRepository;

    public AddFinisherService(AddFinisherRepository addFinisherRepository) {
        this.addFinisherRepository = addFinisherRepository;
    }
    // *  parameter addFinisherMachine
    // *  AddFinisher   data  save in a db
    public AddFinisherMachine savemachine(AddFinisherMachine addFinisherMachined) {
        return addFinisherRepository.save(addFinisherMachined);
    }

    /*
     * get All Added Finisher MachineDetails from  AddMachine with sorted machineId
     * */
    public List<AddFinisherMachine> ViewData() {
        return addFinisherRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(AddFinisherMachine::getId))
                .collect(Collectors.toList());
    }
    /*
    *   parameter Long machineI
    *   it's hard delete
    *   NOTE ->  develop a Soft Delete Machine Service
    * */
    public void DeleteReading(Long id) {
        try {
            addFinisherRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<AddFinisherMachine> FindByData(Long id) {
        return Optional.ofNullable(addFinisherRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    /*
     * parameter Boolean status
     * will be getting All Finisher Details from finisher machine where the status is true or False
     *
     * */
    public List<AddFinisherMachine> Status(boolean status)
    {
        return  addFinisherRepository.findByStatus(status);
    }
}

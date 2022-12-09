package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddCardingMachine;
import com.unitechApi.addmachine.repositroy.AddCardingRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddCardingService {

    private final AddCardingRepository addCardingRepository;

    public AddCardingService(AddCardingRepository addCardingRepository) {
        this.addCardingRepository = addCardingRepository;
    }

    // *  parameter AddCardingMachine addCardingMachine
    // *  AddCardingMachine  data  save in a db
    public AddCardingMachine Savecarding(AddCardingMachine addCardingMachine) {
        return addCardingRepository.save(addCardingMachine);
    }

    /*
     * get All Added Carding MachineDetails from  AddMachine with sorted machineId
     * */
    public List<AddCardingMachine> ViewData() {
        return addCardingRepository.findAll()
                .stream().
                sorted(Comparator.comparing(AddCardingMachine::getId))
                .collect(Collectors.toList());

    }
    /*
        parameter Long machineI
    * it's hard delete
    NOTE ->  develop a Soft Delete Machine Service
    * */

    public void DeleteReading(Long id) {
        try {
            addCardingRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<AddCardingMachine> FindByData(Long id) {
        return Optional.ofNullable(addCardingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    /*
     * parameter Boolean status
     * will be getting All Carding Details from carding machine where the status is true or False
     * */
    public List<AddCardingMachine> Status(boolean status) {
        return addCardingRepository.findByStatus(status);
    }
}

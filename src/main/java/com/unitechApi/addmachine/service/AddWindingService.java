package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddWindingMachine;
import com.unitechApi.addmachine.repositroy.AddWindingRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddWindingService {

    private final AddWindingRepository windingRepository;

    public AddWindingService(AddWindingRepository windingRepository) {
        this.windingRepository = windingRepository;
    }

    // *  parameter addWindingMachine
    // *  AddWiningMachine   data  save in a db
    public AddWindingMachine SaveData(AddWindingMachine winding) {
        return windingRepository.save(winding);
    }


    /*
     * get All Added Winding MachineDetails from  AddMachine with sorted machineId
     * */
    public List<AddWindingMachine> ViewData() {
        return windingRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(AddWindingMachine::getId))
                .collect(Collectors.toList());
    }
    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */
    public void DeleteReading(Long id) {
        try {
            windingRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */

    public Optional<AddWindingMachine> FindByData(Long id) {
        return Optional.ofNullable(windingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    /*
     * parameter Boolean status
     * will be getting All WindIng Details from Winding machine where the status is true or False
     *
     * */
    public List<AddWindingMachine> Status(boolean status) {
        return windingRepository.findByStatus(status);
    }
}

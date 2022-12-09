package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddUtillityMachine;
import com.unitechApi.addmachine.repositroy.AddUtilityRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddUtilityService {

    private final AddUtilityRepository utilityRepository;

    public AddUtilityService(AddUtilityRepository utilityRepository) {
        this.utilityRepository = utilityRepository;
    }

    // *  parameter addUtilityMachine
    // *  AddUtility   data  save in a db
    public AddUtillityMachine SaveData(AddUtillityMachine utiliity) {
        return utilityRepository.save(utiliity);
    }
    /*
     * get All Added Utility MachineDetails from  AddMachine with sorted machineId
     * */
    public List<AddUtillityMachine> ViewData() {
        return utilityRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(AddUtillityMachine::getId))
                .collect(Collectors.toList());
    }
    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */

    public void DeleteReading(Long id) {
        try {
            utilityRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<AddUtillityMachine> FindByData(Long id) {
        return Optional.ofNullable(utilityRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    /*
     * parameter Boolean status
     * will be getting All Utility Details from Utility machine where the status is true or False
     *
     * */

    public List<AddUtillityMachine> Status(boolean status) {
        return utilityRepository.findByStatus(status);
    }
}

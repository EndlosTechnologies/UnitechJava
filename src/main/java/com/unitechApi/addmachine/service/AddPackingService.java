package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddPackingMachine;
import com.unitechApi.addmachine.repositroy.AddPackingRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class AddPackingService {

    private final AddPackingRepository packingRepository;

    public AddPackingService(AddPackingRepository packingRepository) {
        this.packingRepository = packingRepository;
    }

    // *  parameter addPackingMachine
    // *  AddPacking   data  save in a db
    public AddPackingMachine SaveData(AddPackingMachine packing) {
        return packingRepository.save(packing);
    }

    /*
     * get All Added Packing MachineDetails from  AddMachine with sorted machineId
     * */
    public List<AddPackingMachine> ViewData() {
        return packingRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(AddPackingMachine::getId))
                .collect(Collectors.toList());
    }
    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */
    public void DeleteReading(Long id) {
        try {
            packingRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<AddPackingMachine> FindByData(Long id) {
        Optional<AddPackingMachine> addPackingMachine = Optional.ofNullable(packingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
        return addPackingMachine;
    }
    /*
     * parameter Boolean status
     * will be getting All Packing Details from packing machine where the status is true or False
     *
     * */
    public List<AddPackingMachine> Status(boolean status)
    {
        return  packingRepository.findByStatus(status);
    }
}

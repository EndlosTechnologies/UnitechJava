package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddLapFormer;
import com.unitechApi.addmachine.repositroy.AddLapFormerRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddLapFormerService {

    private final AddLapFormerRepository addLapFormerRepository;

    public AddLapFormerService(AddLapFormerRepository addLapFormerRepository) {
        this.addLapFormerRepository = addLapFormerRepository;
    }
    // *  parameter addLap formerMachine
    // *  AddLapFormer   data  save in a db
    public AddLapFormer savemachine(AddLapFormer addLapFormer) {
        return addLapFormerRepository.save(addLapFormer);
    }

    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<AddLapFormer> FindByData(Long id) {
        return Optional.ofNullable(addLapFormerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    /*
     * get All Added LapFormer MachineDetails from  AddMachine with sorted machineId
     * */

    public List<Object> ViewData() {
        return addLapFormerRepository.findAll().stream().sorted(Comparator.comparing(AddLapFormer::getId))
                .collect(Collectors.toList());
    }
    /*
     * parameter Boolean status
     * will be getting All LapFormer Details from LapFramer machine where the status is true or False
     *
     * */
    public List<AddLapFormer> Status(boolean status) {
        return addLapFormerRepository.findByStatus(status);
    }
}

package com.unitechApi.addmachine.service;


import com.unitechApi.addmachine.model.AddDrawFramesMachine;
import com.unitechApi.addmachine.repositroy.AddDrawFramesRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddDrawMachienService {

    private final AddDrawFramesRepository addDrawFramesRepository;

    public AddDrawMachienService(AddDrawFramesRepository addDrawFramesRepository) {
        this.addDrawFramesRepository = addDrawFramesRepository;
    }
    // *  parameter addDrawFramesMachine
    // *  AddDrawFramesMachine   data  save in a db

    public AddDrawFramesMachine savemachineDraw(AddDrawFramesMachine addDrawFramesMachine) {
        return addDrawFramesRepository.save(addDrawFramesMachine);

    }
    /*
     * get All Added DrawFrames MachineDetails from  AddMachine with sorted machineId
     * */
    public List<AddDrawFramesMachine> ViewData() {
        return addDrawFramesRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(AddDrawFramesMachine::getId))
                .collect(Collectors.toList());
    }

    /*
      parameter Long machineI
  * it's hard delete
  NOTE ->  develop a Soft Delete Machine Service
  * */
    public void DeleteReading(Long id) {
        try {
            addDrawFramesRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<AddDrawFramesMachine> FindByData(Long id) {
        return Optional.ofNullable(addDrawFramesRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    /*
     * parameter Boolean status
     * will be getting All DrawFrames Details from drawFrames machine where the status is true or False
     *
     * */
    public List<AddDrawFramesMachine> Status(boolean status) {
        return addDrawFramesRepository.findByStatus(status);
    }
}

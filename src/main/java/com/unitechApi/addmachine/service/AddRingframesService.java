package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddRingFramesMachine;
import com.unitechApi.addmachine.repositroy.AddRingFrameRepossitory;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddRingframesService {

    private final AddRingFrameRepossitory ringFrameRepossitory;

    public AddRingframesService(AddRingFrameRepossitory ringFrameRepossitory) {
        this.ringFrameRepossitory = ringFrameRepossitory;
    }
    // *  parameter addRingFrameMachine
    // *  AddRingFramesMachine's   data  save in a db

    public AddRingFramesMachine SaveData(AddRingFramesMachine ringFrame) {
        return ringFrameRepossitory.save(ringFrame);
    }
    /*
     * get All Added AddRingFrameMachine MachineDetails from  AddMachine with sorted machineId
     * */

    public List<AddRingFramesMachine> ViewData() {
        return ringFrameRepossitory.findAll().stream().sorted(Comparator.comparing(AddRingFramesMachine::getId))
                .collect(Collectors.toList());
    }
    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */

    public void DeleteReading(Long id) {
        try {
            ringFrameRepossitory.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<AddRingFramesMachine> FindByData(Long id) {
        return Optional.ofNullable(ringFrameRepossitory.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    /*
     * parameter Boolean status
     * will be getting All RingFramesMachine Details from RingFrame machine where the status is true or False
     *
     * */
    public List<AddRingFramesMachine> Status(boolean status) {
        return ringFrameRepossitory.findByStatus(status);
    }
}

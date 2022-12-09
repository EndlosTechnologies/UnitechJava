package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddWasteRoomeMAchine;
import com.unitechApi.addmachine.repositroy.AddWasteRoomRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddWasteRoomService {

    private final AddWasteRoomRepository wasteRoomRepository;

    public AddWasteRoomService(AddWasteRoomRepository wasteRoomRepository) {
        this.wasteRoomRepository = wasteRoomRepository;
    }
    // *  parameter addWasteRoom
    // *  AddWasteRoom    data  save in a db

    public AddWasteRoomeMAchine SaveData(AddWasteRoomeMAchine wasteroom) {
        return wasteRoomRepository.save(wasteroom);
    }

    /*
     * get All Added WasteRoom MachineDetails from  AddMachine with sorted machineId
     * */
    public List<AddWasteRoomeMAchine> ViewData() {
        return wasteRoomRepository.findAll().stream().sorted(Comparator.comparing(AddWasteRoomeMAchine::getId))
                .collect(Collectors.toList());
    }

    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */
    public void DeleteReading(Long id) {
        try {
            wasteRoomRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */

    public Optional<AddWasteRoomeMAchine> FindByData(Long id) {
        return Optional.ofNullable(wasteRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    /*
     * parameter Boolean status
     * will be getting All WasteRoom Details from WasteRoom machine where the status is true or False
     *
     * */
    public List<AddWasteRoomeMAchine> Status(boolean status) {
        return wasteRoomRepository.findByStatus(status);
    }

}

package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddBloowroom;
import com.unitechApi.addmachine.repositroy.AddBloowRoomRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class Addbloowroomservice {

    private final AddBloowRoomRepository addBloowRoomRepository;

    public Addbloowroomservice(AddBloowRoomRepository addBloowRoomRepository) {
        this.addBloowRoomRepository = addBloowRoomRepository;
    }
    // *  parameter AddBloowroom addBloowroom
    // *  AddBloowRoom data  save in a db

    public AddBloowroom savemachine(AddBloowroom addBloowroom) {
        return addBloowRoomRepository.save(addBloowroom);
    }

    /*
     * get All Added Machine Data from  AddMachine with sorted machineId
     * */
    public List<AddBloowroom> ViewData() {
        return addBloowRoomRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(AddBloowroom::getId))
                .collect(Collectors.toList());
    }

    public void DeleteReading(Long id) {
        try {
            addBloowRoomRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<AddBloowroom> FindByData(Long id) {
        return Optional.ofNullable(addBloowRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }




}

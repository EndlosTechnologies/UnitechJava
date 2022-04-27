package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddBloowroom;
import com.unitechApi.addmachine.repositroy.AddBloowRoomRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class Addbloowroomservice {

    private final AddBloowRoomRepository addBloowRoomRepository;
    public Addbloowroomservice(AddBloowRoomRepository addBloowRoomRepository) {
        this.addBloowRoomRepository = addBloowRoomRepository;
    }

    public AddBloowroom savemachine(AddBloowroom addBloowroom) {
        return addBloowRoomRepository.save(addBloowroom);
    }

    public Object ViewData() {
        return addBloowRoomRepository.findAll().stream().sorted(Comparator.comparing(AddBloowroom::getId));
    }

    public void DeleteReading(Long id) {
        try {
            addBloowRoomRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<AddBloowroom> FindByData(Long id) {
        return Optional.ofNullable(addBloowRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }


    public List<AddBloowroom> Status(boolean status) {
        AddBloowroom addBloowroom = new AddBloowroom();
        log.info("{}   Status ", status);
        if (addBloowroom.isStatus() == false) {
            addBloowroom.setStatus(true);
            log.info("{} Status changed ", status);
            addBloowRoomRepository.findByStatus(status);
        }
        return addBloowRoomRepository.findByStatus(status);
    }


}

package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddWasteRoomeMAchine;
import com.unitechApi.addmachine.repositroy.AddWasteRoomRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddWasteRoomService {

    private final AddWasteRoomRepository wasteRoomRepository;

    public AddWasteRoomService(AddWasteRoomRepository wasteRoomRepository) {
        this.wasteRoomRepository = wasteRoomRepository;
    }

    public AddWasteRoomeMAchine SaveData(AddWasteRoomeMAchine wasteroom) {
        return wasteRoomRepository.save(wasteroom);
    }

    public Object ViewData() {
        return wasteRoomRepository.findAll();
    }

    public void DeleteReading(Long id) {
        try {
            wasteRoomRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    public Optional<AddWasteRoomeMAchine> FindByData(Long id) {
        return Optional.ofNullable(wasteRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<AddWasteRoomeMAchine> Status(boolean status) {
        return wasteRoomRepository.findByStatus(status);
    }

}

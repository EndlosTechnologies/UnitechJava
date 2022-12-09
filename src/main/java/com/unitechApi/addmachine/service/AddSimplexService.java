package com.unitechApi.addmachine.service;

import com.unitechApi.addmachine.model.AddRingFramesMachine;
import com.unitechApi.addmachine.model.AddSimplexMAchine;
import com.unitechApi.addmachine.repositroy.AddSimplexRepository;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddSimplexService {

    private final AddSimplexRepository simplexRepository;

    public AddSimplexService(AddSimplexRepository simplexRepository) {
        this.simplexRepository = simplexRepository;
    }

    // *  parameter addSimplexMachine
    // *  AddSimplex   data  save in a db
    public AddSimplexMAchine SaveData(AddSimplexMAchine simplex) {
        return simplexRepository.save(simplex);
    }
    /*
     * get All Added Simplex MachineDetails from  AddMachine with sorted machineId
     * */
    public List<AddSimplexMAchine> ViewData() {
        return simplexRepository
                .findAll()
                .stream().sorted(Comparator.comparing(AddSimplexMAchine::getId))
                .collect(Collectors.toList());
    }
    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */
    public void DeleteReading(Long id) {
        try {
            simplexRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */
    public Optional<AddSimplexMAchine> FindByData(Long id) {
        return Optional.ofNullable(simplexRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }
    /*
     * parameter Boolean status
     * will be getting All Simplex Details from comber machine where the status is true or False
     *
     * */
    public List<AddSimplexMAchine> Status(boolean status) {
        return simplexRepository.findByStatus(status);
    }
}

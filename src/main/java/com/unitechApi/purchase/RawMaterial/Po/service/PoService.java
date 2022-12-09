package com.unitechApi.purchase.RawMaterial.Po.service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;
import com.unitechApi.purchase.RawMaterial.Contract.Repository.ContractRepository;
import com.unitechApi.purchase.RawMaterial.Po.Model.PoModel;
import com.unitechApi.purchase.RawMaterial.Po.Repository.PoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PoService {

    private final PoRepository poRepository;
    private final ContractRepository contractRepository;

    public PoService(PoRepository poRepository, ContractRepository contractRepository) {
        this.poRepository = poRepository;
        this.contractRepository = contractRepository;
    }

    /*
     * save personal Order Data
     * */
    public PoModel SaveData(PoModel poModel) {
        PoModel poModel1 = poRepository.save(poModel);
        return poModel1;
    }

    /*
     * params  PoId PoId
     * return get by personal order data  by sampleId
     * */
    public PoModel FindById(Long PoId) {
        PoModel poModel = poRepository.findById(PoId).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
        return poModel;
    }

    public Object DeleteById(Long PoId) {
        Optional<PoModel> poModel = Optional.ofNullable(poRepository.findById(PoId).orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (poModel.isPresent())
            poRepository.deleteById(PoId);
        return null;
    }

    /*
     * params  cid  cid
     * params pid pid
     * return update personalOrder in contract    by cid
     * */
    public PoModel UpdateId(Long cid, Long pid) {
        ContractModel contractModel = contractRepository.findById(cid).get();
        PoModel poModel = poRepository.findById(pid).get();
        poModel.UpdateId(contractModel);
        return poRepository.save(poModel);

    }
}

package com.unitechApi.purchase.RawMaterial.Contract.Service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;
import com.unitechApi.purchase.RawMaterial.Contract.Model.PartyLotModel;
import com.unitechApi.purchase.RawMaterial.Contract.Repository.ContractRepository;
import com.unitechApi.purchase.RawMaterial.Contract.Repository.PartyLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartyLotService {

    private final PartyLotRepository partyLotRepository;
    private final ContractRepository contractRepository;

    public PartyLotService(PartyLotRepository partyLotRepository, ContractRepository contractRepository) {
        this.partyLotRepository = partyLotRepository;
        this.contractRepository = contractRepository;
    }

    /*
    * save partylot
    * */
    public PartyLotModel saveData(PartyLotModel partyLotModel) {
        return partyLotRepository.save(partyLotModel);
    }

    /*
     *
     * @params partyId
     *
     *   return get party-lot data by ID
     * */
    public PartyLotModel FindById(Long partyId) {
        return partyLotRepository.findById(partyId).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
    }

    /*
    * return
    *    get All partyLot with Assign aor nor
    * */
    public Object FindAll() {
        return partyLotRepository.findAll();
    }

    public Optional Delete(Long partyId) {
        Optional<PartyLotModel> partyLotModel = Optional.ofNullable(partyLotRepository.findById(partyId).orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
        if (partyLotModel.isPresent()) {
            partyLotRepository.deleteById(partyId);
        }
        return Optional.empty();
    }


    /*
     *
     * @params c_id
     * params d_id
     * return update partylot in contract  data
     * */
    public PartyLotModel UpdateId(Long c_id, Long p_id) {
        ContractModel contractModel = contractRepository.findById(c_id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
        PartyLotModel partyLotModel = partyLotRepository.findById(p_id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
        partyLotModel.idUpdate(contractModel);
        return partyLotRepository.save(partyLotModel);
    }
}

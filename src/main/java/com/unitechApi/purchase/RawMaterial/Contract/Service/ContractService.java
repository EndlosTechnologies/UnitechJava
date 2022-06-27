package com.unitechApi.purchase.RawMaterial.Contract.Service;


import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;
import com.unitechApi.purchase.RawMaterial.Contract.Repository.ContractRepository;
import com.unitechApi.purchase.RawMaterial.Do.Model.DeliveryOrderModel;
import com.unitechApi.purchase.RawMaterial.Do.Repository.DoRepository;
import com.unitechApi.purchase.RawMaterial.item.model.Itemmodel;
import com.unitechApi.purchase.RawMaterial.item.repository.ItemRepository;
import com.unitechApi.store.vendor.Repository.VendorRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final VendorRepository vendorRepository;
    private final ItemRepository itemRepository;
    private final DoRepository doRepository;

    public ContractService(ContractRepository contractRepository, VendorRepository vendorRepository, ItemRepository itemRepository, DoRepository doRepository) {
        this.contractRepository = contractRepository;
        this.vendorRepository = vendorRepository;
        this.itemRepository = itemRepository;
        this.doRepository = doRepository;
    }

    public ContractModel Save(ContractModel contractModel) {
        return contractRepository.save(contractModel);
    }
    public ContractModel FindById(Long id) {
        return contractRepository.findById(id).orElseThrow(()->new ResourceNotFound("Can Not Find Any Contract"));
    }
    public Object FindAll()
    {
        return contractRepository.findAll();
    }
    public ContractModel DeleteData(Long id)
    {
        Optional<ContractModel> contractModel = contractRepository.findById(id);
        if (!contractModel.isPresent())
        {
            throw new UserNotFound("Sorry ! cann't Do This cause User Not Found In this id");

        }
         contractRepository.deleteById(id);

        return null;
    }
    public Page<ContractModel> FindCOntractByDate(Date start, Date end, Pagination pagination)
    {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return contractRepository.findByCreatedAtBetween(start, end, pagination.getpageble());
    }
    public Page<ContractModel> FindByParticularDate(Date pdate, Pagination pagination)
    {
        return contractRepository.findByCreatedAt(pdate,pagination.getpageble());
    }

    public ContractModel UpdateIdItem(Long c_id, Long v_id)
    {
        ContractModel contractModel= contractRepository.findById(c_id).get();
        Itemmodel itemmodel =itemRepository.findById(v_id).get();
        contractModel.updateiddata(itemmodel);
        return contractRepository.save(contractModel);
    }


    public Object UpdateIdItemDelivery(Long c_id, Long d_id) {
        ContractModel contractModel= contractRepository.findById(c_id).get();
        DeliveryOrderModel deliveryOrderModel=doRepository.findById(d_id).get();

        contractModel.updatedeliveryid(deliveryOrderModel);
        return contractRepository.save(contractModel);


    }
}

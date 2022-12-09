package com.unitechApi.purchase.RawMaterial.Do.Service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.purchase.RawMaterial.Contract.Repository.ContractRepository;
import com.unitechApi.purchase.RawMaterial.Do.Model.DeliveryOrderModel;
import com.unitechApi.purchase.RawMaterial.Do.Repository.DoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class DoService {
    private final DoRepository doRepository;
    private final ContractRepository contractRepository;

    public DoService(DoRepository doRepository, ContractRepository contractRepository) {
        this.doRepository = doRepository;
        this.contractRepository = contractRepository;
    }
    /*
    * save  DeliveryOrder
    * */
    public DeliveryOrderModel SaveData(DeliveryOrderModel deliveryOrderModel) {
        DeliveryOrderModel Model = doRepository.save(deliveryOrderModel);
        return Model;
    }

    /*
    params id
    * get DeliveryOrder data By Id
    * */
    public DeliveryOrderModel GetOrderByid(Long id) {
        return doRepository.findById(id).orElseThrow(() -> new ResourceNotFound("sorry Can Not Find A Any Resource"));
    }


    public DeliveryOrderModel DeleteData(Long Doid) {
        Optional<DeliveryOrderModel> doModel = Optional.ofNullable(doRepository.findById(Doid).orElseThrow(() -> new ResourceNotFound("Resource Not Found " + Doid)));
        if (doModel.isPresent())
            doRepository.deleteById(Doid);
        return null;
    }

    /*
    * params doId
    * params changes
    * return update DeliveryOrder in by doid
    *
    * */
    public Object updateData(Long doId, Map<String, Object> changes) {
        DeliveryOrderModel deliveryOrderModel = doRepository.findById(doId).orElseThrow(() -> new ResourceNotFound("Sorry !  i cann't Do This cause your Data Not Found"));
        changes.forEach((key, values) -> {
            Field field = ReflectionUtils.findField(DeliveryOrderModel.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, deliveryOrderModel, values);
        });
        DeliveryOrderModel deliveryOrderModelsave = doRepository.save(deliveryOrderModel);
        return deliveryOrderModel;
    }
}

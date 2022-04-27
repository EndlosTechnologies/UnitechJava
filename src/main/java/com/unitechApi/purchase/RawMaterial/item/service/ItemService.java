package com.unitechApi.purchase.RawMaterial.item.service;

import com.unitechApi.exception.ExceptionService.UserNotFound;
import com.unitechApi.purchase.RawMaterial.Contract.Repository.ContractRepository;
import com.unitechApi.purchase.RawMaterial.item.model.Itemmodel;
import com.unitechApi.purchase.RawMaterial.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ContractRepository contractRepository;

    public ItemService(ItemRepository itemRepository, ContractRepository contractRepository) {
        this.itemRepository = itemRepository;
        this.contractRepository = contractRepository;
    }

    public Itemmodel SaveData(Itemmodel itemmodel) {
        return itemRepository.save(itemmodel);
    }

    public Itemmodel FindById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new UserNotFound("User Not Found " + itemId));
    }

    public Object DeleteById(Long itemId) {
        Optional<Itemmodel> itemmodel = Optional.ofNullable(itemRepository.findById(itemId).orElseThrow(() -> new UserNotFound("User Not Found " + itemId)));
        if (itemmodel.isPresent())
            itemRepository.deleteById(itemId);
        return Optional.empty();
    }

    public List<Itemmodel> FindAll() {

        return itemRepository.findAll();
    }


}

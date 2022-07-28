package com.unitechApi.store.indent.Service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.store.indent.Model.IndentQuantity;
import com.unitechApi.store.indent.Repository.IndentRepository;
import com.unitechApi.store.indent.Repository.QuantityRepository;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuantityService {
    private final QuantityRepository quantityRepository;
    private final StoreItemRepository storeItemRepository;
    private final IndentRepository indentRepository;
    public static final Logger log = LoggerFactory.getLogger(QuantityService.class);

    public QuantityService(QuantityRepository quantityRepository, StoreItemRepository storeItemRepository, IndentRepository indentRepository) {
        this.quantityRepository = quantityRepository;
        this.storeItemRepository = storeItemRepository;
        this.indentRepository = indentRepository;
    }

    public IndentQuantity saveData(IndentQuantity indentQuantity) {
        StoreItemModel storeItemModel = storeItemRepository.findById(indentQuantity.getStoreItemGetQuantity().getItemId()).get();
        //((indent.getTotal() * itemModel.getPaytax())/100)+indent.getTotal()
        float calculateTotal = indentQuantity.getEstimatedPrice() * indentQuantity.getQuantity();
        indentQuantity.setTotal(calculateTotal);
        float calculateInculdingTax = ((indentQuantity.getTotal() * storeItemModel.getPaytax()) / 100) + indentQuantity .getTotal();
        indentQuantity.setInculdingTax(calculateInculdingTax);
        log.info("tax -> {}, Quantity ->{}", storeItemModel.getPaytax(), indentQuantity.getQuantity());
        log.info("without Tax-> {} , Including Tax->{}", calculateTotal, calculateInculdingTax);
        return quantityRepository.save(indentQuantity);
    }

    public List<?> saveAllData(List<IndentQuantity> indentQuantities) {

        return quantityRepository
                .saveAll(indentQuantities)
                    .stream()
                        .map((data) -> {
                         StoreItemModel storeItemModel = storeItemRepository.findById(data.getStoreItemGetQuantity().getItemId()).get();
                    //((indent.getTotal() * itemModel.getPaytax())/100)+indent.getTotal()
                        float calculateTotal = data.getEstimatedPrice() * data.getQuantity();
                        data.setTotal(calculateTotal);
                        float calculateInculdingTax = ((data.getTotal() * storeItemModel.getPaytax()) / 100) + data.getTotal();
                        data.setInculdingTax(calculateInculdingTax);
                        log.info("tax -> {}, Quantity ->{}", storeItemModel.getPaytax(), data.getQuantity());
                        log.info("without Tax-> {} , Including Tax->{}", calculateTotal, calculateInculdingTax);
                        return quantityRepository.save(data);
                    }).collect(Collectors.toList());

    }


    public IndentQuantity findById(Long quantityId) {
        return quantityRepository.findById(quantityId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
    }

    public List<IndentQuantity> dataGetByIndentId(Long indentId) {
        return quantityRepository.findAllByIndentid(indentId);
    }
}


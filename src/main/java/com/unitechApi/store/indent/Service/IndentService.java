package com.unitechApi.store.indent.Service;

import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.exception.ExceptionService.ItemNotFound;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentQuantity;
import com.unitechApi.store.indent.Model.IndentStatus;
import com.unitechApi.store.indent.Repository.IndentRepository;
import com.unitechApi.store.indent.Repository.QuantityRepository;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import com.unitechApi.store.vendor.Service.VendorService;
import com.unitechApi.store.vendor.model.VendorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndentService {
    private final IndentRepository indentRepository;
    private final StoreItemRepository storeItemRepository;
    private final VendorService vendorService;
    private final QuantityRepository quantityRepository;
    private static final Logger log = LoggerFactory.getLogger(IndentService.class);

    public IndentService(IndentRepository indentRepository, StoreItemRepository storeItemRepository, VendorService vendorService, QuantityRepository quantityRepository) {
        this.indentRepository = indentRepository;
        this.storeItemRepository = storeItemRepository;
        this.vendorService = vendorService;
        this.quantityRepository = quantityRepository;
    }


    public Indent saveData(Indent indent) {

        float dta = 0;
        float tax = 0;
        float total = 0;
        float withoutTax = 0;

        for (IndentQuantity i : indent.getIndentQuantityList()) {
            StoreItemModel item = storeItemRepository.findById(i.getStoreItemIndentQuantityData().getItemId()).get();
            tax = (i.getQuantity() * i.getEstimatedPrice() * item.getPaytax()) / 100;
            withoutTax = i.getEstimatedPrice() * i.getQuantity();
            total = withoutTax + tax;
            i.setWithoutTax(withoutTax);
            i.setInculdingTax(tax);
            i.setTotal(total);
            dta+=withoutTax+tax;
            quantityRepository.save(i);
            log.info("indent estimated Price {}  And tax ", indent.getEstimatedPrice(), i.getStoreItemIndentQuantityData().getPaytax());
            log.info("quantity {}", i.getQuantity());
        }
        indent.setTotal(dta);

        /// indent.setTotal(indent.getEstimatedPrice() * data.getQuantity());

//        indent.getStoreItemList()
//                .addAll(
//                        indent
//                                .getStoreItemList()
//                                .stream()
//                                .map(x -> {
//                                            StoreItemModel itemModel = storeItemRepository.findById(x.getItemId())
//                                                    .orElseThrow(() -> new ItemNotFound("item not Found"));
//
//                                            indent.setIncludingTax(((indent.getTotal() * itemModel.getPaytax()) / 100) + indent.getTotal());
//                                            log.info("get Including Tax {} And Item {}", indent.getIncludingTax(), itemModel);
//                                            return x;
//                                        }
//
//                                )
//
//                                .collect(Collectors.toList())
//                        );

        return indentRepository.save(indent);
    }


    public Indent findByid(Long id) {
        return indentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Sorry ! Indent Was Not Found"));
    }

    public Indent updateData(Long id, Map<Object, Object> request) {
        Indent req = indentRepository.findById(id).orElseThrow(() -> new ItemNotFound("Sorry ! Item Was Not Found"));
        request.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Indent.class, (String) key);
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, req, value);
        });
        Indent requestData = indentRepository.save(req);
        log.trace("updated Data  {}", requestData);
        return requestData;
    }


    public Object changeStatus(long itemId, Indent dta) {
        Indent itemRequest = indentRepository.findById(itemId).orElseThrow(() -> new ItemNotFound("Sorry ! Item Was Not Found"));

        if (itemRequest.getIndentStatus().equals(IndentStatus.ADMIN)) {
            log.info("vendor id {}", dta.getVendorData().getId());
            VendorModel vendorModel = vendorService.FindById(dta.getVendorData().getId());
            vendorModel.getIndentList().add(itemRequest);
            log.info(" vendor first details {} ", vendorModel);
            itemRequest.setVendorData(vendorModel);
            log.info(" vendor details {} ", vendorModel);

        }
        itemRequest.setIndentStatus(dta.getIndentStatus());
        log.info("item Request {}", itemRequest);
        return indentRepository.save(itemRequest);
    }

    public List<Indent> findAll() {
        return indentRepository.findAll().stream().sorted(Comparator.comparing(Indent::getIndentId).reversed()).collect(Collectors.toList());
    }


    public List<Indent> findByCreatedDate(Date date) {
        return indentRepository.findByCreated(date);
    }

    public List<Indent> findByStatus(IndentStatus indentStatus) {
        return indentRepository.findByIndentStatus(indentStatus).stream().sorted(Comparator.comparing(Indent::getIndentId).reversed()).collect(Collectors.toList());
    }

    public List<Indent> findByListDateBetween(Date start, Date end) {
        return indentRepository.ffindByDateBEtween(start, end).stream().sorted(Comparator.comparing(Indent::getIndentId)).collect(Collectors.toList());
    }
    public Page<Indent> getAllByPagination(Pagination pagination)
    {
        return indentRepository.findAll(pagination.getpageble());
    }
}

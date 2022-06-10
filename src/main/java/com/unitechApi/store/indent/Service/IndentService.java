package com.unitechApi.store.indent.Service;

import com.unitechApi.exception.ExceptionService.ItemNotFound;
import com.unitechApi.purchase.RawMaterial.vendor.Repository.VendorRepository;
import com.unitechApi.purchase.RawMaterial.vendor.Service.VendorService;
import com.unitechApi.purchase.RawMaterial.vendor.model.VendorModel;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentStatus;
import com.unitechApi.store.indent.Repository.IndentRepository;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(IndentService.class);

    public IndentService(IndentRepository indentRepository, StoreItemRepository storeItemRepository, VendorService vendorService) {
        this.indentRepository = indentRepository;
        this.storeItemRepository = storeItemRepository;
        this.vendorService = vendorService;
    }

    public Indent saveData(Indent indent ) {
        StoreItemModel itemModel=storeItemRepository.findById(indent.getStoreItem().getItemId())
                .orElseThrow(()-> new ItemNotFound("item not Found"));
//        VendorModel vendorModel=vendorService.FindById(indent.getVendorDetails().getId());
//        indent.setVendorDetails(vendorModel);
        indent.setTotal((long) (indent.getEstimatedPrice() * indent.getQuantity()));
        indent.setIncludingTax(((indent.getTotal() * itemModel.getPaytax())/100)+indent.getTotal());
  //      indent.set((long) (indent.getTotal() + indent.getIncludingTax()));
        return indentRepository.save(indent);
    }

    public Indent findByid(Long id) {
        return indentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFound("Sorry ! Item Was Not Found"));
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
        LOG.trace("updated Data  {}", requestData);
        return requestData;
    }




    public Object changeStatus(long itemId, Indent dta) {
        Indent itemRequest = indentRepository
                .findById(itemId)
                .orElseThrow(() -> new ItemNotFound("Sorry ! Item Was Not Found"));

        if (itemRequest.getIndentStatus().equals(IndentStatus.ADMIN))
        {
            Indent indent=findByid(itemRequest.getIndentId());
            dta.setVendorDetails(indent.getVendorDetails());
        }
//        if (itemRequest.getIndentStatus().equals(IndentStatus.VP)) {
                itemRequest.setIndentStatus(dta.getIndentStatus());

            LOG.info("item Request {}", itemRequest);
            indentRepository.save(itemRequest);
//        }
        return null;
    }

    public List<Indent> findAll() {
        return indentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Indent::getIndentId).reversed()).collect(Collectors.toList());
    }


    public List<Indent> findByCreatedDate(Date date) {
        return indentRepository.findByCreated(date);
    }
    public List<Indent> findByStatus(IndentStatus indentStatus) {
        return indentRepository.findByIndentStatus(indentStatus)
                .stream()
                .sorted(Comparator.comparing(Indent::getIndentId).reversed())
                .collect(Collectors.toList());
    }
}

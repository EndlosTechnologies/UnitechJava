package com.unitechApi.store.indent.Service;

import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.common.query.SearchRequest;
import com.unitechApi.common.query.SearchSpecification;
import com.unitechApi.exception.ExceptionService.ItemNotFound;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.store.indent.Model.*;
import com.unitechApi.store.indent.Repository.IndentEventRepository;
import com.unitechApi.store.indent.Repository.IndentRepository;
import com.unitechApi.store.indent.Repository.PriceModelRepository;
import com.unitechApi.store.indent.Repository.QuantityRepository;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import com.unitechApi.store.vendor.Service.VendorService;
import com.unitechApi.user.Repository.UserRepository;
import com.unitechApi.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final IndentEventRepository indentEventRepository;
    private final VendorService vendorService;
    private final QuantityRepository quantityRepository;
    private final UserRepository userRepository;
    private final PriceModelRepository priceModelRepository;
    private static final Logger log = LoggerFactory.getLogger(IndentService.class);

    public IndentService(IndentRepository indentRepository, StoreItemRepository storeItemRepository, IndentEventRepository indentEventRepository, VendorService vendorService, QuantityRepository quantityRepository, UserRepository userRepository, PriceModelRepository priceModelRepository) {
        this.indentRepository = indentRepository;
        this.storeItemRepository = storeItemRepository;
        this.indentEventRepository = indentEventRepository;
        this.vendorService = vendorService;
        this.quantityRepository = quantityRepository;
        this.userRepository = userRepository;

        this.priceModelRepository = priceModelRepository;
    }


    public Indent saveData(Indent indent) {

        float dta = 0;
        float dtatotal = 0;
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
            dta += withoutTax + tax;
            dtatotal += withoutTax;
            //indent.getIndentQuantityList().add(i);
            quantityRepository.save(i);
            log.info("indent estimated Price {}  And tax ", indent.getEstimatedPrice(), i.getStoreItemIndentQuantityData().getPaytax());
            log.info("quantity {}", i.getQuantity());
        }
        for (IndentQuantity i : indent.getIndentQuantityList()) {
            i.setIndentItemQuantity(indent);
        }
        indent.setTotal(dtatotal);
        indent.setIncludingTax(dta);
        indentRepository.save(indent);
        indentEventRepository.save(new IndentCreateHistory(indent.getIndentNumber(), indent.getIndentId(), IndentStatus.GM, indent.getEmployee().getId(), indent.getEmployee().getUsername(), indent.getComment()));

        return indent;
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
        User user = userRepository.getById(dta.getEmployee().getId());
        if (itemRequest.getIndentStatus() == IndentStatus.GM && dta.getIndentStatus() == IndentStatus.ADMIN) {
            itemRequest.setIndentStatus(IndentStatus.ADMIN);
        } else if (itemRequest.getIndentStatus() == IndentStatus.ADMIN && dta.getIndentStatus() == IndentStatus.STORE) {
            itemRequest.setIndentStatus(IndentStatus.STORE);
        } else if (itemRequest.getIndentStatus() == IndentStatus.STORE && dta.getIndentStatus() == IndentStatus.ADMIN_LAST) {
            for (VendorWisePriceModel v : dta.getVendorWisePriceSet()) {
                v.setIndentPrice(itemRequest);
                priceModelRepository.save(v);
            }
            itemRequest.setIndentStatus(IndentStatus.ADMIN_LAST);
        } else if (dta.getIndentStatus() == IndentStatus.CANCEL) {
            itemRequest.setIndentStatus(IndentStatus.CANCEL);
        } else if (dta.getIndentStatus() == IndentStatus.REJECT) {
            itemRequest.setIndentStatus(IndentStatus.REJECT);
        } else if (dta.getIndentStatus() == IndentStatus.DONE) {
            itemRequest.setIndentStatus(IndentStatus.DONE);
        }
        //else if (itemRequest.getIndentStatus()==IndentStatus.ADMIN_LAST && dta.getIndentStatus()== )

        //        if (itemRequest.getIndentStatus().equals(IndentStatus.ADMIN)) {
//            log.info("vendor id {}", dta.getVendorData().getId());
//            VendorModel vendorModel = vendorService.FindById(dta.getVendorData().getId());
//            vendorModel.getIndentList().add(itemRequest);
//            log.info(" vendor first details {} ", vendorModel);
//            itemRequest.setVendorData(vendorModel);
//            log.info(" vendor details {} ", vendorModel);
//        }
//        if (dta.getIndentStatus() == IndentStatus.STORE) {
//            for (VendorWisePriceModel v : dta.getVendorWisePriceSet()) {
//                v.setIndentPrice(itemRequest);
//                priceModelRepository.save(v);
//            }
//        }
        //itemRequest.setIndentStatus(dta.getIndentStatus());
        log.info("item Request {}", itemRequest);
        indentRepository.save(itemRequest);
        indentEventRepository.save(new IndentCreateHistory(itemRequest.getIndentNumber(), itemRequest.getIndentId(), itemRequest.getIndentStatus(), user.getId(), user.getUsername(), dta.getComment()));
        return itemRequest;
    }

    public List<Indent> findAll() {
        return indentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Indent::getIndentId).reversed())
                .collect(Collectors.toList());
    }


    public List<Indent> findByCreatedDate(Date date) {
        return indentRepository.findByCreated(date);
    }

    public List<Indent> findByStatus(IndentStatus indentStatus) {
        return indentRepository.findByIndentStatus(indentStatus).stream().sorted(Comparator.comparing(Indent::getIndentId).reversed()).collect(Collectors.toList());
    }


    public Page<List<Indent>> findByListDateBetweenWithIndentNumber(Date start, Date end, Pagination pagination, String indentNumber) {
        if (indentNumber == null) {
            return indentRepository.ffindByDateBEtween(start, end, pagination.getpageble());
        }
        return indentRepository.ffindByDateBEtweenWithindentNumber(start, end, pagination.getpageble(), indentNumber);
    }

    public List<?> findByIndnentNumber(String indentNumber) {
        return indentRepository.findByIndentNumber(indentNumber);
    }

    public Page<Indent> getAllByPagination(Pagination pagination) {
        return indentRepository.findAll(pagination.getpageble());
    }

    public Page<Indent> getByAll(String keyword, Pagination pagination) {
        return indentRepository.searchInAllIndent(keyword, pagination.getpageble());
    }

    public Page<Indent> searchIndent(SearchRequest request) {
        SearchSpecification<Indent> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return indentRepository.findAll(specification, pageable);
    }


    public Object changeRequestStatus(long itemId, Indent dta) {


        Indent itemRequest = indentRepository.findById(itemId).orElseThrow(() -> new ItemNotFound("Sorry ! Item Was Not Found"));
        User user = userRepository.getById(dta.getEmployee().getId());
      //  if (itemRequest.Approoved())

            indentRepository.save(itemRequest);
        indentEventRepository.save(new IndentCreateHistory(itemRequest.getIndentNumber(), itemRequest.getIndentId(), itemRequest.getIndentStatus(), user.getId(), user.getUsername(), dta.getComment()));
        return itemRequest;
    }


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
}

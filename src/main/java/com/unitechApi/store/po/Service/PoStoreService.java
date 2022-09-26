package com.unitechApi.store.po.Service;

import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.common.query.SearchRequest;
import com.unitechApi.common.query.SearchSpecification;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentCreateHistory;
import com.unitechApi.store.indent.Model.IndentStatus;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.indent.Repository.IndentEventRepository;
import com.unitechApi.store.indent.Repository.IndentRepository;
import com.unitechApi.store.indent.Repository.PriceModelRepository;
import com.unitechApi.store.po.Model.PoPrice;
import com.unitechApi.store.po.Model.PoStore;
import com.unitechApi.store.po.Repository.PoPriceRepository;
import com.unitechApi.store.po.Repository.PoStoreRepository;
import com.unitechApi.store.po.view.PoByIndentView;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import com.unitechApi.store.vendor.Repository.VendorRepository;
import com.unitechApi.store.vendor.model.VendorModel;
import com.unitechApi.user.Repository.UserRepository;
import com.unitechApi.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PoStoreService {
    private final PoStoreRepository poStoreRepository;
    private final PriceModelRepository priceModelRepository;
    private final PoPriceRepository poPriceRepository;
    private final IndentRepository indentRepository;
    private final StoreItemRepository storeItemRepository;
    private final VendorRepository vendorRepository;
    private final IndentEventRepository indentEventRepository;
    private final UserRepository userRepository;
    public static final Logger log = LoggerFactory.getLogger(PoStoreService.class);


    public PoStoreService(PoStoreRepository poStoreRepository, PriceModelRepository priceModelRepository, PoPriceRepository poPriceRepository, IndentRepository indentRepository, StoreItemRepository storeItemRepository, VendorRepository vendorRepository, IndentEventRepository indentEventRepository, UserRepository userRepository) {
        this.poStoreRepository = poStoreRepository;
        this.priceModelRepository = priceModelRepository;
        this.poPriceRepository = poPriceRepository;
        this.indentRepository = indentRepository;
        this.storeItemRepository = storeItemRepository;
        this.vendorRepository = vendorRepository;
        this.indentEventRepository = indentEventRepository;
        this.userRepository = userRepository;
    }

    public PoStore saveData(PoStore poStore) {
        float totalAmount = 0;
        Indent indent = indentRepository.getById(poStore.getIndentDAta().getIndentId());
        log.info("indent  {}", indent);
        List<VendorWisePriceModel> vend = priceModelRepository.findByIndentId(indent.getIndentId());

        log.info("vendor price model  {}", vend);
        //   List<String> data = new ArrayList<>();
        for (VendorWisePriceModel i : vend) {
            PoPrice poPrice = new PoPrice();
            log.info("for in loop  {}", i);
            log.info("po price {}", poPrice);
            poPrice.setPoPrice((float) i.getPriceItem());
            poPrice.setItemPriceInPersonalOrder(i.getItemModelPrice());
            poPrice.setIndentDAtaPo(i.getIndentPrice());
            //  poPrice.setVendorWisePriceModel(i);
            poPrice.setIncludingTax(i.getIncludingTax());
            poPrice.setWithoutTax(i.getWithoutTax());
            totalAmount += i.getIncludingTax();
            poStore.setAmount(totalAmount);
            poPriceRepository.save(poPrice);

        }

        return poStoreRepository.save(poStore);

    }

//    public List<?> findByLock() {
//        return poPriceRepository.findByLock();
//    }

    public PoStore findById(Long poId) {
        return poStoreRepository.findById(poId).orElseThrow(() -> new ResourceNotFound("Resource Not Found " + poId));
    }

    public Page<PoStore> findAll(Pagination pagination) {
        return poStoreRepository.findAll(pagination.getpageble());


    }

    public void changeDeleteStatus(boolean deleteView, Long poId) {
        poStoreRepository.changeStattus(deleteView, poId);
    }

    public List<PoStore> findByDeleteViewStatus() {
        return poStoreRepository.findByDeleteView().stream().filter(PoStore::isDeleteView).sorted(Comparator.comparing(PoStore::getPoId)).collect(Collectors.toList());
    }

//    public List<PoStore> findByDescOrder(Long itemId, Long poId) {
//        return poStoreRepository.findBypo_item_data_(itemId, poId);
//    }

    public List<?> findByPoNumber(String poNumber) {
        return poStoreRepository.findByPosNumber(poNumber);
    }

    public List<?> findByUtrNumber(String utrNumber) {
        return poStoreRepository.findByUtrNumber(utrNumber);
    }

    public List<PoByIndentView> findByIndentId(Long indentId) {
        return poStoreRepository.getIndentId(indentId);
    }


    public PoStore doublePosaveData(PoStore poStore) {
        AtomicReference<Float> totalAmount = new AtomicReference<>((float) 0);
        Indent indent = indentRepository.getById(poStore.getIndentDAta().getIndentId());
        User user = userRepository.getById(poStore.getUserListData().getId());
        if (indent.getIndentStatus() == IndentStatus.ADMIN_LAST) {
            indent.setIndentStatus(IndentStatus.DONE);
            indentEventRepository.save(new IndentCreateHistory(indent.getIndentNumber(), indent.getIndentId(), indent.getIndentStatus(), user.getId(), user.getUsername(), indent.getComment()));
        }
        log.info("indent  {}", indent);
        List<VendorWisePriceModel> vend = priceModelRepository.findByIndentId(indent.getIndentId());
//        VendorWisePriceModel vendorWisePriceModel=priceModelRepository.getById(poStore.getListOfpO())
        Set<VendorWisePriceModel> helllo = poStore
                .getListOfpO()
                .stream()
                .peek(data -> {
                    log.info("inner in loop  {}", data);
                    PoPrice poPrice = new PoPrice();
                    VendorWisePriceModel v = priceModelRepository.getById(data.getPrice_id());
                    VendorModel vendorModel = vendorRepository.getById(v.getVendorModelData().getId());
                    poPrice.setPoPrice((float) v.getPriceItem());
                    poPrice.setItemPriceInPersonalOrder(v.getItemModelPrice());
                    poPrice.setIndentDAtaPo(v.getIndentPrice());
                    //poPrice.setVendorWisePriceModel(i);
                    poPrice.setItemQuantity(v.getItemQuantity());
                    poPrice.setIncludingTax(v.getIncludingTax());
                    //    poPrice.setVendorModels(v.getVendorModelData());
                    poPrice.setWithoutTax(v.getWithoutTax());
                    totalAmount.updateAndGet(v1 -> v1 + v.getTotalAmount());
                    poStore.setAmount(totalAmount.get());
                    poPriceRepository.save(poPrice);
                    log.info("price the id{} and vendor id {}", v, vendorModel.getId());
                }).collect(Collectors.toSet());
        log.info("price Id {}", helllo);


        return poStoreRepository.save(poStore);

    }

    public Page<PoStore> getSearchingInPo(SearchRequest searchRequest) {
        SearchSpecification<PoStore> specification = new SearchSpecification<>(searchRequest);
        Pageable pageable = SearchSpecification.getPageable(searchRequest.getPage(), searchRequest.getSize());
        return poStoreRepository.findAll(specification, pageable);
    }
}

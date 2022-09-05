package com.unitechApi.store.indent.Service.ServiceImpl;

import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentQuantity;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.indent.Repository.IndentRepository;
import com.unitechApi.store.indent.Repository.PriceModelRepository;
import com.unitechApi.store.indent.Service.PriceService;
import com.unitechApi.store.indent.view.ViewByDistinct;
import com.unitechApi.store.po.Repository.PoStoreRepository;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import com.unitechApi.store.vendor.Repository.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PriceServiceImpl implements PriceService {
    private final PriceModelRepository priceModelRepository;
    private final IndentRepository indentRepository;
    private final StoreItemRepository storeItemRepository;
    private final VendorRepository vendorRepository;
    private final PoStoreRepository poStoreRepository;
    private static final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);

    public PriceServiceImpl(PriceModelRepository priceModelRepository, IndentRepository indentRepository, StoreItemRepository storeItemRepository, VendorRepository vendorRepository, PoStoreRepository poStoreRepository) {
        this.priceModelRepository = priceModelRepository;
        this.indentRepository = indentRepository;
        this.storeItemRepository = storeItemRepository;
        this.vendorRepository = vendorRepository;
        this.poStoreRepository = poStoreRepository;
    }

    /**
     * @param id
     * @return get All Data By Id
     */
    @Override
    public VendorWisePriceModel findById(Long id) {
        return priceModelRepository.findById(id).orElse(null);
    }

    /**
     * @param indentId
     * @return
     */
    @Override
    public List<VendorWisePriceModel> findByIndentId(Long indentId) {
        return priceModelRepository.findByIndentId(indentId)
                .stream()
                .sorted(Comparator.comparing(o -> o.getItemModelPrice().getItemId()))
                .collect(Collectors.toList());
    }

    /**
     * @param indentId
     * @return
     */


    /**
     * @param vendorWisePriceModel
     * @return save data
     */
    @Override
    public VendorWisePriceModel saveData(VendorWisePriceModel vendorWisePriceModel) {
        StoreItemModel storeItemModel = storeItemRepository.getById(vendorWisePriceModel.getItemModelPrice().getItemId());
        Indent indent = indentRepository.getById(vendorWisePriceModel.getIndentPrice().getIndentId());
        List<Indent> indentdata = indentRepository.getAllByIndentId(vendorWisePriceModel.getIndentPrice().getIndentId());
        log.info("List GetAllById {}",indentdata);
        List<Indent> datr = Arrays.asList(indent);
        for (IndentQuantity i : indent.getIndentQuantityList()) {
            vendorWisePriceModel.setItemQuantity(i.getQuantity());
            vendorWisePriceModel.setWithoutTax((float) (vendorWisePriceModel.getPriceItem() * i.getQuantity()));
            log.info("without tax {}", vendorWisePriceModel.getWithoutTax());
            Object tax = (i.getQuantity() * i.getEstimatedPrice() * storeItemModel.getPaytax()) / 100;
            vendorWisePriceModel.setIncludingTax((float) tax + vendorWisePriceModel.getWithoutTax());
            log.info("including  tax {}", vendorWisePriceModel.getIncludingTax());
            priceModelRepository.save(vendorWisePriceModel);
        }

        for (Indent i : indentdata) {
          //  if ()
            log.info("data lose{}",i.toString());
        }


        return priceModelRepository.save(vendorWisePriceModel);
    }

    /**
     * @param id 
     * @return
     */
    @Override
    public List<?> countDistinctByVendorModelDataId(Long id) {
        return priceModelRepository.getDistinctByVendorId(id);
    }

    /**
     * @param indentId 
     * @return
     */
    @Override
    public List<?> getDistinctByItemId(Long indentId) {
        return priceModelRepository.getDistinctByItemId(indentId);
    }
}

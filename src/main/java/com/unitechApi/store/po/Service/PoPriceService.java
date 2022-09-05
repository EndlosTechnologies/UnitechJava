package com.unitechApi.store.po.Service;

import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.po.Model.PoPrice;
import com.unitechApi.store.po.Repository.PoPriceRepository;
import org.springframework.stereotype.Service;

@Service
public class PoPriceService {
    private final PoPriceRepository poPriceRepository;

    public PoPriceService(PoPriceRepository poPriceRepository) {
        this.poPriceRepository = poPriceRepository;
    }
    public PoPrice saveData(PoPrice poPrice)
    {
        return poPriceRepository.save(poPrice);
    }

}

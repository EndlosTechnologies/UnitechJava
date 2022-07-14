package com.unitechApi.store.po.Service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.store.po.Model.PoStore;
import com.unitechApi.store.po.Repository.PoStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PoStoreService {
    private final PoStoreRepository poStoreRepository;
    public static final Logger log= LoggerFactory.getLogger(PoStoreService.class);


    public PoStoreService(PoStoreRepository poStoreRepository) {
        this.poStoreRepository = poStoreRepository;
    }

    public PoStore saveData(PoStore poStore) {
        return poStoreRepository.save(poStore);
    }

    public PoStore findById(Long poId) {
        return poStoreRepository.findById(poId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found " + poId));
    }

    public List<PoStore> findAll() {
        return poStoreRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(PoStore::getPoId))
                .collect(Collectors.toList());
    }

    public void changeDeleteStatus(boolean deleteView,Long poId) {
        poStoreRepository.changeStattus( deleteView,poId);
    }
    public List<PoStore> findByDeleteViewStatus()
    {
        return poStoreRepository.findByDeleteView()
                .stream()
                .filter(x-> x.isDeleteView()==true)
                .sorted(Comparator.comparing(PoStore::getPoId))
                .collect(Collectors.toList());
    }
}

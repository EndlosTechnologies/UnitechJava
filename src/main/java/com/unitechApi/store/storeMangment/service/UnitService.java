package com.unitechApi.store.storeMangment.service;

import com.unitechApi.exception.ExceptionService.UnitNotFound;
import com.unitechApi.store.storeMangment.Model.ProductCategory;
import com.unitechApi.store.storeMangment.Model.Unit;
import com.unitechApi.store.storeMangment.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnitService {
    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public Unit saveData(Unit unit) {
        return unitRepository.save(unit);
    }

    public Optional<Unit> findById(Long id) {
        return Optional.ofNullable(unitRepository.findById(id)).orElseThrow(() -> new UnitNotFound("Unit Not Found " + id));
    }

    public Unit deleteData(Long id) {
        Optional<Unit> data = Optional.ofNullable(unitRepository.findById(id).orElseThrow(() -> new UnitNotFound("Unit Not Found " + id)));
        if (data.isPresent()) {
            unitRepository.deleteById(id);
        }
        return null;
    }

    public List<Unit> findAll() {
        return unitRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Unit::getUid))
                .collect(Collectors.toList());
    }
    public List<Unit> findByUnitName(String name)
    {
        return unitRepository.findByUnitName(name);
    }
    public List<Unit>  findByDate(Date date)
    {
        return unitRepository.findByCreated(date);
    }
}

package com.unitechApi.store.unit.service;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.common.query.SearchRequest;
import com.unitechApi.common.query.SearchSpecification;
import com.unitechApi.exception.ExceptionService.UnitNotFound;
import com.unitechApi.store.unit.model.Unit;
import com.unitechApi.store.unit.repository.UnitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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



    /*
    * save Unit
    * */
    public Unit saveData(Unit unit) {
        return unitRepository.save(unit);
    }
    /*
    * params id
    * get Unit by unitId
    *
    * */
    public Optional<Unit> findById(Long id) {
        return Optional.of(unitRepository.findById(id)).orElseThrow(() -> new UnitNotFound("Unit Not Found " + id));
    }

    /*
      parameter Long id
    * it's hard delete
    NOTE ->  develop a Soft Delete Machine Service
    * */

    public Unit deleteData(Long id) {
        Optional<Unit> data = Optional.ofNullable(unitRepository.findById(id).orElseThrow(() -> new UnitNotFound("Unit Not Found " + id)));
        if (data.isPresent()) {
            unitRepository.deleteById(id);
        }
        return null;
    }

    /*
    * getAll Unit
    * */
    public List<Unit> findAll() {
        return unitRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Unit::getUid))
                .collect(Collectors.toList());
    }

    /*
    * params  name
    * get Unit by unitname
    * */
    public List<Unit> findByUnitName(String name)
    {
        return unitRepository.findByUnitName(name);
    }

    /*
    * params date
    *
    * get All Unit By date
    * */
    public List<Unit>  findByDate(Date date)
    {
        return unitRepository.findByCreated(date);
    }

    public Page<Unit> searchingInUnit(SearchRequest searchRequest){
        SearchSpecification<Unit> data=new SearchSpecification<>(searchRequest);
        Pageable pageable=SearchSpecification.getPageable(searchRequest.getPage(),searchRequest.getSize());
        return unitRepository.findAll(data,pageable);
    }
}

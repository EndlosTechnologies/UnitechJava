package com.unitechApi.store.Response.Service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.store.Response.Model.ResEntity;
import com.unitechApi.store.Response.Model.ResStatus;
import com.unitechApi.store.Response.Repository.ResEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResService {
    private final ResEntityRepository resEntityRepository;

    public ResService(ResEntityRepository resEntityRepository) {
        this.resEntityRepository = resEntityRepository;
    }

    public ResEntity saveData(ResEntity resEntity) {
        return resEntityRepository.save(resEntity);
    }

    public Optional<ResEntity> findById(Long id) {
        return Optional.ofNullable(resEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("can not find a any Response")));
    }

    public List<ResEntity> findAll() {
        return resEntityRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(ResEntity::getRid))
                .collect(Collectors.toList());
    }

    public Optional<ResEntity> deleteByResponseId(@PathVariable Long id) {
        Optional<ResEntity> data = Optional.ofNullable(resEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("can not find a any Response")));
        if (data.isPresent()) {
            resEntityRepository.deleteById(id);
        }
        return Optional.empty();
    }

    public List<ResEntity> findByCreatedDate(Date date) {
        return resEntityRepository.findByCreated(date);
    }

    public List<ResEntity> findByResStatus(String staus) {
        return resEntityRepository.findByResStatus(staus);
    }



    public List<ResEntity> findByPoRaised(boolean poRaised) {
        return resEntityRepository.findByPoRaised(poRaised);
    }

    public List<ResEntity> findByDoRaised(boolean doRaised) {
        return resEntityRepository.findByDoRaised(doRaised);
    }
    public List<ResEntity> findByIndentRaised(boolean indentRaised)
    {
        return resEntityRepository.findByIndentRaised(indentRaised);
    }
    public List<ResEntity> findByResStatus(ResStatus resStatus,Long pdiId)
    {
        return resEntityRepository.findByResStatusAndAndPdiId(resStatus, pdiId);

    }
}

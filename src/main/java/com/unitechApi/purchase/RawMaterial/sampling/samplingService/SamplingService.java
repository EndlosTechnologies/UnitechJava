package com.unitechApi.purchase.RawMaterial.sampling.samplingService;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;
import com.unitechApi.purchase.RawMaterial.Contract.Repository.ContractRepository;
import com.unitechApi.purchase.RawMaterial.sampling.samplingModel.SampleEntity;
import com.unitechApi.purchase.RawMaterial.sampling.samplingRepository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SamplingService {

    private final SampleRepository sampleRepository;
    private final ContractRepository contractRepository;

    public SamplingService(SampleRepository sampleRepository, ContractRepository contractRepository) {
        this.sampleRepository = sampleRepository;
        this.contractRepository = contractRepository;
    }

    public SampleEntity sampleEntity(SampleEntity sampleEntity) {
        return sampleRepository.save(sampleEntity);
    }

    public SampleEntity FindById(Long sampleId) {
        return sampleRepository.findById(sampleId).orElseThrow(() -> new ResourceNotFound("Resource Not Found "));
    }

    public Object FindAll()
    {

        return sampleRepository.findAll();
    }
    public Optional DeleteDataId(Long sampleId) {
        Optional<SampleEntity> sample = Optional.ofNullable(sampleRepository.findById(sampleId).orElseThrow(() -> new ResourceNotFound("Resource Not Found ")));

        if (sample.isPresent()) {
            sampleRepository.deleteById(sampleId);
        }
        return null;
    }
    public SampleEntity IdUpdate(Long c_id,Long s_id)
    {
        ContractModel contractModel=contractRepository.findById(c_id).orElseThrow(()-> new ResourceNotFound("Resource Not Found "));
        SampleEntity sample=sampleRepository.findById(s_id).orElseThrow(()-> new ResourceNotFound("Resource Not Found "));
        sample.updateId(contractModel);
        return sampleRepository.save(sample);
    }
}

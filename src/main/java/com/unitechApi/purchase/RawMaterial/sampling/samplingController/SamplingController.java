package com.unitechApi.purchase.RawMaterial.sampling.samplingController;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.purchase.RawMaterial.sampling.samplingModel.SampleEntity;
import com.unitechApi.purchase.RawMaterial.sampling.samplingService.SamplingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/unitech/api/v1/purchase/sampling")
public class SamplingController {


    private final SamplingService samplingService;

    public SamplingController(SamplingService samplingService) {
        this.samplingService = samplingService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> SaveData(@RequestBody SampleEntity sample) {
        SampleEntity sampleEntity = samplingService.sampleEntity(sample);
        return new ResponseEntity<>(PageResponse.SuccessResponse(sampleEntity), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{sampleId}")
    public ResponseEntity<?> FindById(@PathVariable Long sampleId) {
        SampleEntity sample = samplingService.FindById(sampleId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(sample), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> FindAll() {
        Object sample = samplingService.FindAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(sample), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{sampleId}")
    public ResponseEntity<?> DeleteData(@PathVariable Long sampleId) {
        return new ResponseEntity<>(samplingService.DeleteDataId(sampleId), HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{c_id}/update/{s_id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long c_id, @PathVariable Long s_id) {
        return new ResponseEntity<>(samplingService.IdUpdate(c_id, s_id), HttpStatus.OK);
    }
}

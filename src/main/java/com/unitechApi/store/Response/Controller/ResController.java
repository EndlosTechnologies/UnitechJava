package com.unitechApi.store.Response.Controller;


import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.Response.Model.ResEntity;
import com.unitechApi.store.Response.Model.ResStatus;
import com.unitechApi.store.Response.Service.ResService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/response")
public class ResController {
    private final ResService resService;

    public ResController(ResService resService) {
        this.resService = resService;
    }

    @PostMapping
    public ResponseEntity<?> saveResponseData(@RequestBody ResEntity resEntity) {
        ResEntity data = resService.saveData(resEntity);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findByResponseId(@PathVariable Long id) {
        Optional<ResEntity> data = resService.findById(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllResponse() {
        List<ResEntity> data = resService.findAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteResponseId(@PathVariable Long id) {
        Optional<ResEntity> data = resService.deleteByResponseId(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/createDate")
    public ResponseEntity<?> findByCreatedResponse(@RequestParam Date create) {
        List<ResEntity> data = resService.findByCreatedDate(create);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<?> findByResStatus(@RequestParam String status) {
        List<ResEntity> data = resService.findByResStatus(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }


    @GetMapping(value = "/poRaised")
    public ResponseEntity<?> findByPoRaised(@RequestParam boolean poRaised) {
        List<ResEntity> data = resService.findByPoRaised(poRaised);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/doRaised")
    public ResponseEntity<?> findByDoRaised(@RequestParam boolean doRaised) {
        List<ResEntity> data = resService.findByDoRaised(doRaised);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/indentRaised")
    public ResponseEntity<?> indByIndentRaised(@RequestParam boolean indentRaised) {
        List<ResEntity> data = resService.findByIndentRaised(indentRaised);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }
    @GetMapping(value = "/resAndPdi")
    public ResponseEntity<?> findByResStausAndPdiId(@RequestParam ResStatus resStatus,@RequestParam Long pdiId) {
        List<ResEntity> data = resService.findByResStatus(resStatus,pdiId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

}

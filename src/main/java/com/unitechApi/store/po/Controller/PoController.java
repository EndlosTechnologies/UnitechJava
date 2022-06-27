package com.unitechApi.store.po.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.po.Model.PoStore;
import com.unitechApi.store.po.Service.PoStoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/unitech/api/v1/po")
public class PoController {
    private final PoStoreService poStoreService;

    public PoController(PoStoreService poStoreService) {
        this.poStoreService = poStoreService;
    }


    public ResponseEntity<?> saveData(@RequestBody PoStore poStore)
    {
        PoStore data=poStoreService.saveData(poStore);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }
}

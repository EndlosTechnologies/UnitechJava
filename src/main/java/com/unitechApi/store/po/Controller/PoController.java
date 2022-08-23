package com.unitechApi.store.po.Controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.po.Model.PoStore;
import com.unitechApi.store.po.Service.PoStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/unitech/api/v1/store/po")
public class PoController {
    private final PoStoreService poStoreService;
    private static final Logger log= LoggerFactory.getLogger(PoController.class);

    public PoController(PoStoreService poStoreService) {
        this.poStoreService = poStoreService;
    }


    @PostMapping
    public ResponseEntity<?> saveData(@RequestBody PoStore poStore)
    {
        PoStore data=poStoreService.saveData(poStore);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }
    @PutMapping("/{poId}")
    public ResponseEntity<?> deleteData(@RequestParam boolean  deleteView,@PathVariable Long poId)
    {
        poStoreService.changeDeleteStatus(deleteView,poId);
        return new ResponseEntity<>(new MessageResponse("deleted " +poId),HttpStatus.OK);
    }
    @GetMapping(value = "/status")
    public ResponseEntity<?> findByStatus()
    {
        List<PoStore> data=poStoreService.findByDeleteViewStatus();
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll(){
        List<?> getAll=poStoreService.findAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(getAll),HttpStatus.OK);
    }
    @GetMapping(value = "/getByValue")
    public ResponseEntity<?> GetByName(@RequestParam Long itemId,@RequestParam Long poId)
    {
        PoStore poStore=poStoreService.findByDescOrder( itemId,poId);
        log.info("po Data {} =>",poStore);
        return new ResponseEntity<>(PageResponse.SuccessResponse(poStore),HttpStatus.OK);
    }



}

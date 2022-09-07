package com.unitechApi.store.po.Controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.store.po.Model.PoStore;
import com.unitechApi.store.po.Service.PoStoreService;
import com.unitechApi.store.po.view.PoByIndentView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/store/po")
public class PoController {
    private final PoStoreService poStoreService;
    private static final Logger log = LoggerFactory.getLogger(PoController.class);

    public PoController(PoStoreService poStoreService) {
        this.poStoreService = poStoreService;
    }


    @PostMapping
    public ResponseEntity<?> saveData(@RequestBody PoStore poStore) {
        PoStore data = poStoreService.saveData(poStore);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @PutMapping("/{poId}")
    public ResponseEntity<?> deleteData(@RequestParam boolean deleteView, @PathVariable Long poId) {
        poStoreService.changeDeleteStatus(deleteView, poId);
        return new ResponseEntity<>(new MessageResponse("deleted " + poId), HttpStatus.OK);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<?> findByStatus() {
        List<PoStore> data = poStoreService.findByDeleteViewStatus();
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll(@RequestParam int page,
                                    @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize, Sort.by("poId"));
        Page<?> getAll = poStoreService.findAll(pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(getAll, pagination), HttpStatus.OK);
    }

//    @GetMapping(value = "/getByValue")
//    public ResponseEntity<?> GetByName(@RequestParam Long itemId, @RequestParam Long poId) {
//        List<PoStore> poStore = poStoreService.findByDescOrder(itemId, poId);
//        log.info("po Data {} =>", poStore);
//        return new ResponseEntity<>(PageResponse.SuccessResponse(poStore), HttpStatus.OK);
//    }

    @GetMapping(value = "/poNumber/{poNumber}")
    public ResponseEntity<?> findByPoNumber(@PathVariable String poNumber) {
        List<?> poNumberData = poStoreService.findByPoNumber(poNumber);
        log.info("poNumber {} => ", poNumberData);
        return new ResponseEntity<>(PageResponse.SuccessResponse(poNumberData), HttpStatus.OK);
    }

    @GetMapping(value = "/utrNumber/{utrNumber}")
    public ResponseEntity<?> findByUtrNumber(@PathVariable String utrNumber) {
        List<?> utrNumberData = poStoreService.findByUtrNumber(utrNumber);
        log.info("utr Number Data -> {}", utrNumberData);
        return new ResponseEntity<>(PageResponse.SuccessResponse(utrNumberData), HttpStatus.OK);
    }

    @GetMapping(value = "/indentGetById/{indentId}")
    public ResponseEntity<?> findByIndentId(@PathVariable Long indentId) {
        List<PoByIndentView> utrNumberData = poStoreService.findByIndentId(indentId);
        log.info("indent  Number Data -> {}", utrNumberData.toString());
        return new ResponseEntity<>(PageResponse.SuccessResponse(utrNumberData), HttpStatus.OK);
    }

        @GetMapping("/getById/{poId}")
    public ResponseEntity<?> getPriceService(@PathVariable Long poId) {
        PoStore data=poStoreService.findById(poId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @PostMapping(value = "/d")
    public ResponseEntity<?> DoublesaveData(@RequestBody PoStore poStore) {
        PoStore data = poStoreService.doublePosaveData(poStore);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }


}

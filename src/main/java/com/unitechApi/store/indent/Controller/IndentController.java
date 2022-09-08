package com.unitechApi.store.indent.Controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.common.query.SearchRequest;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentQuantity;
import com.unitechApi.store.indent.Model.IndentStatus;
import com.unitechApi.store.indent.Model.VendorWisePriceModel;
import com.unitechApi.store.indent.Repository.IndentRepository;
import com.unitechApi.store.indent.Repository.QuantityRepository;
import com.unitechApi.store.indent.Service.IndentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/store/req")
public class IndentController {
    private final IndentService indentService;
    private final IndentRepository indentRepository;
    private final QuantityRepository quantityRepository;


    public IndentController(IndentService indentService, IndentRepository indentRepository, QuantityRepository quantityRepository) {
        this.indentService = indentService;
        this.indentRepository = indentRepository;
        this.quantityRepository = quantityRepository;
    }

    @PostMapping()
    public ResponseEntity<?> saveData(@RequestBody Indent indent) {
        Indent requestData = indentService.saveData(indent);
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData), HttpStatus.CREATED);
    }

    @GetMapping(value = "/update/{indentId}/{quantityId}")
    public ResponseEntity<?> idUpdate(@PathVariable Long indentId, @PathVariable Long quantityId) {
        Indent indent = indentService.findByid(indentId);
        IndentQuantity indentQuantity = quantityRepository.findById(quantityId).get();
        indentQuantity.saveQuantityUpdate(indent);
        quantityRepository.save(indentQuantity);
        return new ResponseEntity<>(new MessageResponse("update Data"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findByAll() {
        List<Indent> requestData = indentService.findAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData), HttpStatus.OK);
    }

    @GetMapping(value = "/{itemId}")
    public ResponseEntity<?> findById(@PathVariable long itemId) {
        Indent requestData = indentService.findByid(itemId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData), HttpStatus.OK);
    }

    @PatchMapping(value = " /{itemId}")
    public ResponseEntity<?> updateData(@PathVariable Long itemId, @RequestBody Map<Object, Object> request) {
        Indent requestData = indentService.updateData(itemId, request);
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestBody Indent data) {
        Object request = indentService.changeStatus(id, data);
        return new ResponseEntity<>(PageResponse.SuccessResponse(request), HttpStatus.OK);
    }

    @GetMapping(value = "/cDate")
    public ResponseEntity<?> findByReqCreated(@RequestParam Date date) {
        List<Indent> data = indentService.findByCreatedDate(date);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/Istatus")
    public ResponseEntity<?> findByIndentStatus(@RequestParam IndentStatus indentStatus) {
        List<Indent> data = indentService.findByStatus(indentStatus);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/show/fil")
    public ResponseEntity<?> findByListDateBetweenWithIndentNumber(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pagesize,
            @RequestParam(required = false) String indentNumber

    ) {
        Pagination pagination = new Pagination(page, pagesize, Sort.by(Sort.Direction.ASC, "indentId"));
        Page<?> data = indentService.findByListDateBetweenWithIndentNumber(start, end, pagination, indentNumber);
        return new ResponseEntity<>(PageResponse.pagebleResponse(data, pagination), HttpStatus.OK);
    }

    @GetMapping(value = "/show/indentNumber")
    public ResponseEntity<?> findByIndentNumber(@RequestParam String indentNumber

    ) {
        List<?> data = indentService.findByIndnentNumber(indentNumber);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/page/")
    public ResponseEntity<?> getAllByPagination(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pagesize
    ) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<Indent> getPage = indentService.getAllByPagination(pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(getPage, pagination), HttpStatus.OK);
    }

    @GetMapping(value = "/getByIndent/")
    public ResponseEntity<?> getByIndetn(@RequestParam String keyword, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pagesize
    ) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<Indent> getPage = indentService.getByAll(keyword, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(getPage, pagination), HttpStatus.OK);
    }

    @PatchMapping(value = "/update/PvendorPrice/{indentId}")
    public ResponseEntity<?> updateTaxOrWithoutTax(@PathVariable Long indentId, @RequestBody VendorWisePriceModel vendorWisePriceModel) {
        Object updateData = indentService.updateVendorPrice(indentId,vendorWisePriceModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(updateData), HttpStatus.PARTIAL_CONTENT);
    }

    @PostMapping(value = "/getAllByIndent")
    public ResponseEntity<?> getAllIndent(@RequestBody SearchRequest request) {
        return new ResponseEntity<>(indentService.searchIndent(request), HttpStatus.OK);
        /*
        *
        *  "filters": [
        {
            "key": "total",
            "operator": "EQUAL",
            "field_type": "INTEGER",
            "value": 7500
        }
        ],
        *  "sorts": [
        {
            "key": "includingTax",
            "direction": "DESC"
        }
    ],
        * */
    }


}

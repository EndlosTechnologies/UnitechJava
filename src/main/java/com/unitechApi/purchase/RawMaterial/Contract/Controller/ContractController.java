package com.unitechApi.purchase.RawMaterial.Contract.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.purchase.RawMaterial.Contract.Model.ContractModel;
import com.unitechApi.purchase.RawMaterial.Contract.Repository.ContractRepository;
import com.unitechApi.purchase.RawMaterial.Contract.Service.ContractService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/purchase/contract")
public class ContractController {

    private final ContractService contractService;
    private final ContractRepository contractRepository;

    public ContractController(ContractService contractService, ContractRepository contractRepository) {
        this.contractService = contractService;
        this.contractRepository = contractRepository;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> SaveData(@RequestBody ContractModel contractModel) {
        ContractModel contractModelData = contractService.Save(contractModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(contractModel), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> FindById(@PathVariable Long id) {
        ContractModel contractModel = contractService.FindById(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(contractModel), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> FindAll() {
        Object contractModel = contractService.FindAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(contractModel), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteData(@PathVariable Long id) {
        return new ResponseEntity<>(contractService.DeleteData(id), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<?> FindCOntractByDate(@RequestParam Date start, @RequestParam Date end, @RequestParam int page, @RequestParam int size) {
        Pagination pagination = new Pagination(page, size);
        Page<ContractModel> contractModelPage = contractService.FindCOntractByDate(start, end, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(contractModelPage, pagination), HttpStatus.OK);
    }

    @GetMapping("/searchsingle")
    public ResponseEntity<?> FindByParticularDate(@RequestParam Date pdate, @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<ContractModel> contractModel = contractService.FindByParticularDate(pdate, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(contractModel, pagination), HttpStatus.OK);
    }

    @PutMapping("/{c_id}/updatevendor/{v_id}")
    public ResponseEntity<?> vendorIdUpdate(@PathVariable Long v_id, @PathVariable Long c_id) {
        return new ResponseEntity<>(contractService.UpdateIdvendor(c_id, v_id), HttpStatus.OK);
    }

    @PutMapping("/{c_id}/updateitem/{i_id}")
    public ResponseEntity<?> ItemIdUpdate(@PathVariable Long i_id, @PathVariable Long c_id) {
        return new ResponseEntity<>(contractService.UpdateIdItem(c_id, i_id), HttpStatus.OK);
    }

    @PutMapping("/{c_id}/updatedo/{i_id}")
    public ResponseEntity<?> DeliveryIdUpdate(@PathVariable Long d_id, @PathVariable Long c_id) {
        return new ResponseEntity<>(contractService.UpdateIdItemDelivery(d_id, c_id), HttpStatus.OK);
    }
}

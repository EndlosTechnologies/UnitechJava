package com.unitechApi.purchase.RawMaterial.vendor.Controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.purchase.RawMaterial.vendor.Service.VendorService;
import com.unitechApi.purchase.RawMaterial.vendor.model.VendorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/purchase/vendor")
public class VendorController {
    private static final Logger logger= LoggerFactory.getLogger(VendorController.class);
     private final VendorService vendorService;
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> SaveData(@RequestBody VendorModel vendorModel)

    {
        VendorModel vendorModeldata =vendorService.SaveData(vendorModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(vendorModeldata), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> FindByIdData(@PathVariable  Long id) {
        VendorModel vendorModel= vendorService.FindById(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(vendorModel),HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        Object vendor =  vendorService.FindAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(vendor),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public   ResponseEntity<?> DeleteData(@PathVariable Long id)
    {
        Object vendorModel =vendorService.DeleteData(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(vendorModel),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<?> FIndyDate(@RequestParam(required = false) Date start, @RequestParam(required = false)
                                        Date end)
    {

        List<VendorModel> vendorModels=vendorService.FindDateByData(start, end);
        return new ResponseEntity<>(PageResponse.SuccessResponse(vendorModels), HttpStatus.OK);
    }
    @GetMapping("/searchsingle")
    public ResponseEntity<?> FindByParticularDate(@RequestParam Date pdate) {

        List<VendorModel> vendorModels=vendorService.FindByParticularDate(pdate);
        return new ResponseEntity<>(PageResponse.SuccessResponse(vendorModels), HttpStatus.OK);
    }
    @DeleteMapping(value = "item/{itemId}/remove/{vendorId}")
    public ResponseEntity<?> DeleteItem(@PathVariable Long itemId,@PathVariable Long vendorId)
    {
        Object dataDelete = vendorService.DeleteItem(itemId,vendorId);
        logger.info("deleted data {} =>",dataDelete);
        return new ResponseEntity<>(new MessageResponse("Record Delete SuccessFully "),HttpStatus.NO_CONTENT);
    }


}

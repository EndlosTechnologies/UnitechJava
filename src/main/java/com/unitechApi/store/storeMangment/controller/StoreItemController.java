package com.unitechApi.store.storeMangment.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.storeMangment.ExcelService.ImportExcel;
import com.unitechApi.store.storeMangment.ExcelService.ItemExcel;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.service.StoreItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/store")
public class StoreItemController {
    public static final Logger log= LoggerFactory.getLogger(StoreItemController.class);
    private final StoreItemService storeItemService;

    public StoreItemController(StoreItemService storeitemService) {
        this.storeItemService = storeitemService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> saveItemdata(@RequestBody StoreItemModel storeItemModel) {
        StoreItemModel itemData = storeItemService.saveData(storeItemModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(itemData), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<StoreItemModel> data = storeItemService.findAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findByid(@PathVariable Long id) {
        StoreItemModel storeItemModel = storeItemService.findById(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(storeItemModel), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateData(@PathVariable long id, @RequestBody Map<Object, Object> itemData) {
        StoreItemModel storeItemModel = storeItemService.updateItem(id, itemData);
        return new ResponseEntity<>(PageResponse.SuccessResponse(storeItemModel), HttpStatus.OK);
    }

    @GetMapping(value = "/itemName/")
    public ResponseEntity<?> findByItemName(@RequestParam String name) {
        List<StoreItemModel> data = storeItemService.findByItemName(name);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/cNo/")
    public ResponseEntity<?> findByCatalogNo(@RequestParam String name) {
        List<StoreItemModel> data = storeItemService.findByCno(name);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/dNo/")
    public ResponseEntity<?> findByDrawingNo(@RequestParam String name) {
        List<StoreItemModel> data = storeItemService.findByDno(name);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping(value = "/sDate/")
    public ResponseEntity<?> findBysDate(@RequestParam Date date) {
        List<StoreItemModel> data = storeItemService.findByCreatedDate(date);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }
    @PatchMapping("update/{uid}/{quantity}")
    public ResponseEntity<?> updateStock(@PathVariable long uid,@PathVariable Long quantity)
    {
        storeItemService.AddStock(uid,quantity);
        return new ResponseEntity<>(new MessageResponse("added Stock " + quantity),HttpStatus.OK);
    }

    @GetMapping(value = "/ex/d")
    public void downloadFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=itemList.xlsx";
        response.setHeader(headerKey, headerValue);
        List<StoreItemModel> data = storeItemService.findAll();
        data.stream().forEach(storeItemModel -> System.out.println(storeItemModel));
        ItemExcel itemExcel = new ItemExcel(data);
        itemExcel.export(response);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "tutorials.xlsx";
        InputStreamResource file = new InputStreamResource(storeItemService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }



}

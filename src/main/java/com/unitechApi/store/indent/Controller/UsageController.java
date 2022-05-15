package com.unitechApi.store.indent.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.indent.Model.UsageItem;
import com.unitechApi.store.indent.Service.UsageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.Date;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/unitech/api/v1/itemusage/")
public class UsageController {
   private static final Logger log= LoggerFactory.getLogger(UsageController.class);
    private final UsageService usageService;

    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }
    @PostMapping
    public ResponseEntity<?> saveData(@RequestBody UsageItem usageItem)
    {
        usageService.save(usageItem);
        return new ResponseEntity<>(new MessageResponse("save Data "), HttpStatus.CREATED);
    }
    @GetMapping("data")
    public ResponseEntity<?> FindAlle()
    {
        BaseStream<UsageItem, Stream<UsageItem>> data=usageService.FindAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> FindByDeptName(@RequestParam String name)
    {
        List<UsageItem> data=usageService.findByDepName(name);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping(value = "bdeptname")
    public ResponseEntity<?> findByDeptNameAndBId(
            @RequestParam(required = false) String deptname
            ,@RequestParam (required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date start
            ,@RequestParam (required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date end
           , @RequestParam (required = false) Long id,
            @RequestParam (required = false) Long issueid
              )
    {
        List<UsageItem> data=usageService.findByBloowDataAndDeptName(deptname,start,end,id,issueid);
        log.info(" searching {} data ",data);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }

}

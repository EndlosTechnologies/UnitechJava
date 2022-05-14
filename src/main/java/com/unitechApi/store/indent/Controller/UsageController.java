package com.unitechApi.store.indent.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.indent.Model.UsageItem;
import com.unitechApi.store.indent.Service.UsageService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/unitech/api/v1/itemusage/")
public class UsageController {
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
           , @RequestParam (required = false) Long id
              )
    {
        List<UsageItem> data=usageService.findByBloowDataAndDeptName(deptname,start,end,id);
        System.out.println(data.size());
         data.forEach(s-> System.out.println(s));
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }

}

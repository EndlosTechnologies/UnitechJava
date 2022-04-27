package com.unitechApi.store.indent.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentStatus;
import com.unitechApi.store.indent.Service.IndentService;
import com.unitechApi.store.issue.model.IssueItem;
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

    public IndentController(IndentService indentService) {
        this.indentService = indentService;
    }
    @PostMapping()
    public ResponseEntity<?> saveData(@RequestBody Indent indent)
    {
        Indent requestData= indentService.saveData(indent);
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findByAll()
    {
        List<Indent> requestData= indentService.findAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData),HttpStatus.OK);
    }

    @GetMapping(value = "/{itemId}")
    public ResponseEntity<?> findById(@PathVariable long itemId)
    {
        Indent requestData= indentService.findByid(itemId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData),HttpStatus.OK);
    }
    @PatchMapping(value = " /{itemId}")
    public ResponseEntity<?> updateData(@PathVariable Long itemId, @RequestBody Map<Object ,Object> request)
    {
        Indent requestData= indentService.updateData(itemId,request);
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData),HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id,@RequestBody Indent data)
    {
        Object request= indentService.changeStatus(id,data);
        return new ResponseEntity<>(PageResponse.SuccessResponse(request),HttpStatus.OK);
    }

    @GetMapping(value = "/cDate")
    public ResponseEntity<?> findByReqCreated(@RequestParam Date date)
    {
        List<Indent> data= indentService.findByCreatedDate(date);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping(value = "/Istatus")
    public ResponseEntity<?> findByIndenetStatus(@RequestParam IndentStatus indentStatus)
    {
        List<Indent> data= indentService.findByStatus(indentStatus);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }

}

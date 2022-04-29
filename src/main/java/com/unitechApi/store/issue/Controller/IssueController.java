package com.unitechApi.store.issue.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.issue.Service.IssueService;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.issue.model.IssueStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/issue")
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }
    @PostMapping
    public ResponseEntity<?> saveIssueData(@RequestBody IssueItem issueItem) {
        IssueItem data = issueService.saveData(issueItem);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.CREATED);
    }
    @GetMapping("/{iId}")
    public ResponseEntity<?> findByIssueId(@PathVariable Long iId) {
        IssueItem data = issueService.findByIdIssue(iId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> findAllIssue() {
        List<IssueItem> data = issueService.findAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDataById(@PathVariable Long id) {
        Object data = issueService.deleteIssueId(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.NO_CONTENT);
    }
    @GetMapping("/status")
    public ResponseEntity<?> findByIssueStatus(@RequestParam IssueStatus status)
    {
        Object data=issueService.FindByStatus(status);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping("/date")
    public ResponseEntity<?> findByIssue(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date search)
    {
        List<IssueItem> data=issueService.findByIssueData(search);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping("/Tstatus")
    public ResponseEntity<?> findByRaisedTrue(@RequestParam Boolean status)
    {
        List<IssueItem> data=issueService.FindByRaisedtrue(status);
        System.out.println(data.size());
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping(value = "/dbetween")
    public ResponseEntity<?> findByIssueDatebetween(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date end)
    {
        List<IssueItem> data=issueService.FindByIssueDateBetween(start, end);
        System.out.println(data.size());
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping(value = "/cid/{closeId}")
    public ResponseEntity<?> FindByCloseId(@PathVariable Long closeId)
    {
        Object data=issueService.FindByCloseId(closeId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @PatchMapping(value = "/{issueId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long issueId, @RequestBody IssueItem data )
    {
        Object request= issueService.changeStatus(issueId,data);
        return new ResponseEntity<>(PageResponse.SuccessResponse(request),HttpStatus.OK);
        //"quantity": 1200
    }
}

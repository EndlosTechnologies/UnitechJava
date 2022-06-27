package com.unitechApi.Excel.ExcelController;

import com.unitechApi.Excel.service.*;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.store.vendor.Repository.VendorRepository;
import com.unitechApi.store.vendor.model.VendorModel;
import com.unitechApi.store.indent.Model.UsageItem;
import com.unitechApi.store.indent.Repository.UsageRepository;
import com.unitechApi.store.indent.Service.UsageService;
import com.unitechApi.store.issue.Repository.IssueRepository;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/unitech/api/v1/excel")
public class IssuedExcelController {
    private final UsageRepository usageRepository;
    private final IssueRepository issueRepository;
    private final UsageService usageService;
    private final StoreItemRepository storeItemRepository;
    private final VendorRepository vendorRepository;
    public IssuedExcelController(UsageRepository usageRepository, IssueRepository issueRepository, UsageService usageService, StoreItemRepository storeItemRepository, VendorRepository vendorRepository) {
        this.usageRepository = usageRepository;
        this.issueRepository = issueRepository;
        this.usageService = usageService;

        this.storeItemRepository = storeItemRepository;
        this.vendorRepository = vendorRepository;
    }





    @GetMapping
    public ResponseEntity<?> downloadDept(@RequestParam String deptname,
                                          HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=DepartmentData" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<UsageItem> listdata=usageRepository.findByDeptName(deptname);
        ReportExcelIssedMachine r=new ReportExcelIssedMachine(listdata);
        r.export(response);
        return new ResponseEntity<>(new MessageResponse("upload SuccessFully"), HttpStatus.OK);
    }
    @GetMapping(value = "/issuedItem")
    public ResponseEntity<?> downloadIssuedItem(@RequestParam String itemName,
                                          @RequestParam Date start, @RequestParam Date end,
                                          HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=issuedItemData" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<IssueItem> listData=issueRepository.findByIssueDateBetween(start,end, Long.valueOf(itemName));
        IssueInItemExcel issue=new IssueInItemExcel(listData);
        issue.export(response);
        return new ResponseEntity<>(new MessageResponse("upload SuccessFully"), HttpStatus.OK);
    }
    @GetMapping(value = "/departmentIssuedItem")
    public ResponseEntity<?> downloadMachineWiseAndDate( @RequestParam(required = false) String deptname,
                                                         @RequestParam(required = false) Long id
            , @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date start
            , @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date end,
                                                HttpServletResponse response) throws IOException, ParseException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=issuedItemData" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<UsageItem> listData=usageService.ExcelReportDate(deptname,id,start,end);
        DepartmentWiseExcel issue=new DepartmentWiseExcel(listData);
        issue.export(response);
        return new ResponseEntity<>(new MessageResponse("upload SuccessFully"), HttpStatus.OK);
    }
    @GetMapping("/storeItem")
    public ResponseEntity<?> storeItemExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=item" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<StoreItemModel> storeItemModelList=storeItemRepository.findAll().stream().sorted(Comparator.comparing(StoreItemModel::getItemId)).collect(Collectors.toList());
        ItemExcel itemExcel=new ItemExcel(storeItemModelList);
        itemExcel.export(response);
        return new ResponseEntity<>(new MessageResponse("Download SuccessFully"), HttpStatus.OK);
    }
    @GetMapping(value = "/vendorExcel")
    public ResponseEntity<?> vendorDetails(HttpServletResponse response) throws IOException, ParseException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vendor" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<VendorModel> vendorData=vendorRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(VendorModel::getId))
                .collect(Collectors.toList());
        VendorExcelService data=new VendorExcelService(vendorData);
        data.export(response);
        return new ResponseEntity<>(new MessageResponse("Download SuccessFully"),HttpStatus.OK);
    }
}

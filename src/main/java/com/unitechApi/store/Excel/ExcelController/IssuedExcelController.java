package com.unitechApi.store.Excel.ExcelController;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.store.Excel.ReportExcelIssedMachine;
import com.unitechApi.store.indent.Model.UsageItem;
import com.unitechApi.store.indent.Repository.UsageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping(value = "/unitech/api/v1/excel")
public class IssuedExcelController {
    private final UsageRepository usageRepository;

    public IssuedExcelController(UsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    public ResponseEntity<?> downloadDept(@RequestParam String deptname,
                                          HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = dateFormat.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=BloowRoomData" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<UsageItem> listdata=usageRepository.findByDeptName(deptname);
        ReportExcelIssedMachine r=new ReportExcelIssedMachine(listdata);
        r.export(response);
        return new ResponseEntity<>(new MessageResponse("upload SuccessFully"), HttpStatus.OK);
    }

}

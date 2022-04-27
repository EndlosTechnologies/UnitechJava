package com.unitechApi.user.controller;

import com.unitechApi.user.service.DbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@RestController
public class ExportController {





//    @GetMapping("/readingD")
//    public ResponseEntity<?> TestData(HttpServletResponse response) throws Exception {
//        response.setContentType("application/octet-stream");
//        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
//        String currentDate = dateFormat.format(new java.util.Date());
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=ReadingData" + currentDate + ".csv";
//        response.setHeader(headerKey, headerValue);
//        DbService d=new DbService();
//        d.backupReadingData();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}

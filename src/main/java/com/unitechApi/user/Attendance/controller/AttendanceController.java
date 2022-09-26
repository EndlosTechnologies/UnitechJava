package com.unitechApi.user.Attendance.controller;

import com.unitechApi.user.Attendance.Entity.AttendanceReportEntity;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/excel/upload")
public class AttendanceController {


    @RequestMapping(value = "/import-excel", method = RequestMethod.POST)
    public ResponseEntity<List<AttendanceReportEntity>> importExcelFile(@RequestParam("file") MultipartFile files) throws IOException, IOException, InvalidFormatException {
        HttpStatus status = HttpStatus.OK;
        List<AttendanceReportEntity> productList = new ArrayList<>();
        //OPCPackage pkg=OPCPackage.open(files.getInputStream());


        //HSSFWorkbook workbook=new HSSFWorkbook(new POIFSFileSystem(files.getInputStream()));
        Workbook workbook= WorkbookFactory.create(new POIFSFileSystem(files.getInputStream()));
        Sheet worksheet= workbook.getSheetAt(0);
//        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
//        XSSFSheet worksheet = workbook.getSheetAt(0);
        DataFormatter df=new DataFormatter();
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                AttendanceReportEntity product = new AttendanceReportEntity();

                Row row = worksheet.getRow(index);
                if (row.cellIterator().hasNext()) {
                       //Integer id = (int) row.getCell(0).getNumericCellValue();
                }
                product.setAttendanceId((long) row.getCell(1).getNumericCellValue());
                product.setEmployeeCode(String.valueOf(row.getCell(2).getNumericCellValue()));
                product.setEmployeeName(String.valueOf((row.getCell(3).getNumericCellValue())));
                product.setShift(String.valueOf(row.getCell(4).getStringCellValue()));
                product.setAInTime(String.valueOf(row.getCell(5).getNumericCellValue()));
                product.setAOutTime(String.valueOf(row.getCell(6).getStringCellValue()));
                product.setDBreak(String.valueOf(row.getCell(7).getNumericCellValue()));
                product.setWorkDuration(String.valueOf(row.getCell(8).getStringCellValue()));
                product.setOverTime(String.valueOf(row.getCell(9).getStringCellValue()));
                product.setTimeDuration(String.valueOf(row.getCell(10).getStringCellValue        ()));
                product.setStatus(row.getCell(11).getStringCellValue());
                product.setRemarks(row.getCell(12).getStringCellValue());
                productList.add(product);
            }
        }
        return new ResponseEntity<>(productList, status);
    }
}

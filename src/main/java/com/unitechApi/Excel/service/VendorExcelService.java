package com.unitechApi.Excel.service;

import com.unitechApi.store.vendor.model.VendorModel;
import com.unitechApi.store.issue.model.IssueStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class VendorExcelService {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<VendorModel> ListItem;

    public VendorExcelService(List<VendorModel> listItem) {
        this.ListItem = listItem;
        workbook = new XSSFWorkbook();
    }
    // this method create for write a header Line in Excel
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Vendor  Data");
        Row row = sheet.createRow(1);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        createCell(row, 1, "Index ", style);
        createCell(row, 2, "Vendor Name", style);
        createCell(row, 3, "Vendor Address", style);
        createCell(row, 4, "City", style);
        createCell(row, 5, "Pin Code", style);
        createCell(row, 6, "Vendor Code", style);
        createCell(row, 7, "Gst Number", style);
        createCell(row, 8, "Pan  NumberItem Name", style);
        createCell(row, 9, "Item Name", style);

    }
    /*
     * this method create for
     *
     * write data in Excel from db
     * */

    private void writeDataLine() {

        int rowcount = 2;

//        XSSFFont fontHeader = workbook.createFont();
//        Row rowHeader = sheet.createRow(4);
//        Row rowHeader2 = sheet.createRow(2);
//        CellStyle styleHeader = workbook.createCellStyle();
//        CellStyle styleHeader2 = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        CellStyle style = workbook.createCellStyle();
        font.setFontHeight(14);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        int serialNumber = 1;
        for (VendorModel vendorModel : ListItem) {
            Row row = sheet.createRow(rowcount++);
            int countRow = 1;
            createCell(row, countRow++, serialNumber++, style);
            createCell(row, countRow++, vendorModel.getVendorName(), style);
            createCell(row, countRow++, vendorModel.getSupplierscode(), style);
            createCell(row, countRow++, vendorModel.getGstno(), style);
            createCell(row, countRow++, vendorModel.getPanno(), style);
            createCell(row, countRow++, vendorModel.getPaymentTermsConditions(), style);
            //   createCell(row, countRow++, vendorModel.getContractModels()., style);
        }
    }

      /*
    this method create for Field Validation
    for cell
    */
    private void createCell(Row row, int i, Object value, CellStyle style) {
//        CellStyle cellStyle = workbook.createCellStyle();
//        CreationHelper createHelper = workbook.getCreationHelper();
//        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MMM/dd/yyyy hh:mm:ss"));
        sheet.autoSizeColumn(i);
        Cell cell = row.createCell(i);

        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Float) {
            cell.setCellValue(String.valueOf(value));
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Date) {
            cell.setCellValue((new Date()));

            System.out.println(value);

        } else if (value instanceof IssueStatus) {
            cell.setCellValue((String.valueOf(value)));
        } else {
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yyyy hh:mm:ss"));
            cell.setCellStyle(cellStyle);
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }


    public void export(HttpServletResponse response) throws IOException, ParseException {
        writeHeaderLine();
        writeDataLine();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}

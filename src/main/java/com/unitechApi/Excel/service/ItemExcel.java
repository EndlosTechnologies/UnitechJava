package com.unitechApi.Excel.service;

import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.issue.model.IssueStatus;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ItemExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<StoreItemModel> ListItem;
   public ItemExcel (List<StoreItemModel> ListItem)
   {
       this.ListItem = ListItem;
       workbook = new XSSFWorkbook();

   }
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Issued Item Data");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        //font.setBold(true);
        //font.setFontHeight(12);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setFont(font);
        createCell(row, 0, "Item Name", style);
        createCell(row, 1, "item Description", style);
        createCell(row, 2, "remaining item", style);
        createCell(row, 3, "drawingNo", style);
        createCell(row, 4, "catalogNo", style);
        createCell(row, 5, "frequency", style);
        createCell(row, 6, "tax", style);
        createCell(row, 7, "quantity", style);
        createCell(row, 8, "In Date", style);
        createCell(row, 9, "expiry Days", style);
        createCell(row, 10, "item Category", style);
        createCell(row, 11, "item unit", style);

    }
    private void writeDataLine() {

        int rowcount = 1;

//        XSSFFont fontHeader = workbook.createFont();
//        Row rowHeader = sheet.createRow(4);
//        Row rowHeader2 = sheet.createRow(2);
//        CellStyle styleHeader = workbook.createCellStyle();
//        CellStyle styleHeader2 = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        CellStyle style = workbook.createCellStyle();
//        font.setFontHeight(14);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setFont(font);
        for (StoreItemModel storeItem : ListItem) {
            Row row = sheet.createRow(rowcount++);
            int countRow = 0;
            createCell(row, countRow++, storeItem.getItemName(), style);
            createCell(row, countRow++, storeItem.getItemDescription(), style);
            createCell(row, countRow++, storeItem.getRemainingItem(), style);
            createCell(row, countRow++, storeItem.getDrawingNo(), style);
            createCell(row, countRow++, storeItem.getCatalogNo(), style);
            createCell(row, countRow++, storeItem.getFrequency(), style);
            createCell(row, countRow++, storeItem.getPaytax(), style);
            createCell(row, countRow++, storeItem.getQuantity(), style);
            createCell(row, countRow++, storeItem.getCreated(), style);
            createCell(row, countRow++, storeItem.getExpiryDays(), style);
            createCell(row, countRow++, storeItem.getProductCategory().getProductName(), style);
            createCell(row, countRow++, storeItem.getUnit().getUnitName(), style);
        }
    }
    private void createCell(Row row, int i, Object value, CellStyle style) {
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MMM/dd/yyyy hh:mm:ss"));
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
            cell.setCellValue((Date) value);
            cell.setCellStyle(cellStyle);
        } else if (value instanceof IssueStatus) {
            cell.setCellValue((String.valueOf(value)));
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLine();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}

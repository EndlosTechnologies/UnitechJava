package com.unitechApi.store.Excel;

import com.unitechApi.MachineSetParameter.model.BloowRoom;
import com.unitechApi.store.indent.Model.UsageItem;
import com.unitechApi.store.issue.model.IssueStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ReportExcelIssedMachine {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<UsageItem> ListItem;

    public ReportExcelIssedMachine(List<UsageItem> listData) {
        this.ListItem = listData;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("BloowRoom Data");
        Row row = sheet.createRow(1);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        createCell(row, 1, "Issue Id", style);
        createCell(row, 2, "Item Name", style);
        createCell(row, 3, "Description", style);
        createCell(row, 4, "Quantity", style);
        createCell(row, 5, "Issue Date", style);
        createCell(row, 6, "Status", style);

    }

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
        for (UsageItem usageItem : ListItem) {
            Row row = sheet.createRow(rowcount++);
            int countRow = 1;
            createCell(row, countRow++, usageItem.getIssuedItem().getIssueId(), style);
            createCell(row, countRow++, usageItem.getIssuedItem().getStoreItemModel().getItemName(), style);
            createCell(row, countRow++, usageItem.getIssuedItem().getDescription(), style);
            createCell(row, countRow++, usageItem.getIssuedItem().getQuantity(), style);
            createCell(row, countRow++, usageItem.getIssuedItem().getIssueDate(), style);
            createCell(row, countRow++, usageItem.getIssuedItem().getStatus(), style);
        }
    }

    private void createCell(Row row, int i, Object value, CellStyle style) {
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
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

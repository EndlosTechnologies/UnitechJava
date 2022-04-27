package com.unitechApi.MachineSetParameter.ExcelService;

import com.unitechApi.MachineSetParameter.model.Comber;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ComberExcelService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Comber> ListUser;

    public ComberExcelService(List<Comber> listData) {
        this.ListUser = listData;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("carding Data");
        Row row = sheet.createRow(5);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        createCell(row, 1, "Machine No", style);
        createCell(row, 2, "Comber Speed SPEED (NPM)", style);
        createCell(row, 3, "Feed Nip (MM)", style);
        createCell(row, 4, "Lap Weight (GM)", style);
        createCell(row, 5, "Machine Efficiency %", style);
        createCell(row, 6, "Noil %", style);
        createCell(row, 7, "Production / MC / 8 Hours (Kg)", style);
        createCell(row, 8, "Production / MC / 6 Hours (Kg)", style);
        createCell(row, 9, "Production / MC / Shift (Kg)", style);
        createCell(row, 10, "Production / MC / 24 Hours (Kg)", style);
        createCell(row, 11, "6 Hours", style);
        createCell(row, 12, "Shift A Avg. Difference from Target", style);
        createCell(row, 13, "6 Hours", style);
        createCell(row, 14, "Shift A Avg. Difference from Target", style);
        createCell(row, 15, "total Production Shift A", style);
        createCell(row, 16, "6 Hours", style);
        createCell(row, 17, "Shift B Avg. Difference from Target", style);
        createCell(row, 18, "6 Hours", style);
        createCell(row, 19, "Shift B Avg. Difference from Target", style);
        createCell(row, 20, "total Production Shift B", style);
        createCell(row, 21, "Actual Production", style);
        createCell(row, 22, "Efficiency", style);
        createCell(row, 23, "Target Production Variance In Kg", style);
        createCell(row, 24, "Target Production Variance", style);
        //  createCell(row,2,"total Production Shift A",style);
    }

    private void createCell(Row row, int i, Object value, CellStyle style) {

        sheet.autoSizeColumn(i);
        Cell cell = row.createCell(i);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Float) {
            cell.setCellValue(String.valueOf(value));
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLine() {

        int rowcount = 6;

        XSSFFont fontHeader = workbook.createFont();
        Row rowHeader = sheet.createRow(4);
        Row rowHeader2 = sheet.createRow(2);
        CellStyle styleHeader = workbook.createCellStyle();
        CellStyle styleHeader2 = workbook.createCellStyle();
        for (int i = 1; i <= 24; ++i) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellStyle(styleHeader);
        }
        for (int j =11;j<=24 ;++j)
        {
            Cell cell = rowHeader2.createCell(j);
            cell.setCellStyle(styleHeader2);
        }

        styleHeader2.setBorderBottom(BorderStyle.THICK);
        styleHeader2.setBorderTop(BorderStyle.THICK);
        styleHeader2.setBorderLeft(BorderStyle.THICK);
        styleHeader2.setBorderRight(BorderStyle.THICK);

        //  sheet.addMergedRegion(new CellRangeAddress(4,4,3,10));
        sheet.addMergedRegion(CellRangeAddress.valueOf("C5:K5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("Q5:U5"));

        sheet.addMergedRegion(CellRangeAddress.valueOf("V5:Y5"));
        // shift a Merged cell
        sheet.addMergedRegion(CellRangeAddress.valueOf("L5:P5"));
        //CellRangeAddress.valueOf("L3:Y3")
        sheet.addMergedRegion(new CellRangeAddress(2,3,11,24));


        styleHeader.setBorderRight(styleHeader2.getBorderRight());
        //   RegionUtil.setBorderBottom(BorderStyle.DASH_DOT_DOT,,sheet,workbook);
        styleHeader.setBorderBottom(BorderStyle.MEDIUM);
        styleHeader.setBorderTop(BorderStyle.MEDIUM);
        styleHeader.setBorderLeft(BorderStyle.MEDIUM);
        styleHeader.setBorderRight(BorderStyle.MEDIUM);

        fontHeader.setBold(true);
        fontHeader.setFontHeight(10);
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setFont(fontHeader);
        // styleHeader.set
        createCell(rowHeader2, 1, "Comber ", styleHeader);
        createCell(rowHeader2, 11, "Shift Wise Production Report ", styleHeader);
        createCell(rowHeader, 2, "Targeted Production ", styleHeader);
        createCell(rowHeader, 11, "Shift A Data (Morning) ", styleHeader);
        createCell(rowHeader, 16, "Shift B Data (Evening) ", styleHeader);
        createCell(rowHeader, 21, "Original Production ", styleHeader);


        XSSFFont font = workbook.createFont();
        CellStyle style = workbook.createCellStyle();
        font.setFontHeight(14);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        for (Comber comber : ListUser) {
            Row row = sheet.createRow(rowcount++);
            int countRow = 1;
            /*
             *   Quality Checker Data
             * */

            createCell(row, countRow++, comber.getAddcomber().getName(), style);
            createCell(row, countRow++, comber.getComberSpeedRpm(), style);
            createCell(row, countRow++, comber.getFeedNip(), style);
            createCell(row, countRow++, comber.getLapWeight(), style);
            createCell(row, countRow++, comber.getMachineEfficency(), style);
            createCell(row, countRow++, comber.getNoil(), style);
            createCell(row, countRow++, comber.getProductioMc8Hour(), style);
            createCell(row, countRow++, comber.getProductioMc6Hour(), style);
            createCell(row, countRow++, comber.getProductioShift(), style);
            createCell(row, countRow++, comber.getProductioMc24Hour(), style);
            /*
             *  superWiser Data
             * */
            createCell(row, countRow++, comber.getShift_a_sixHoursOne(), style);
            createCell(row, countRow++, comber.getAvervg_difference_a_sixHoursOne(), style);
            createCell(row, countRow++, comber.getShift_a_sixHoursTwo(), style);
            createCell(row, countRow++, comber.getAvervg_difference_a_sixHoursTwo(), style);
            createCell(row, countRow++, comber.getTotal_shift_prod_a(), style);
            createCell(row, countRow++, comber.getShift_b_sixHoursOne(), style);
            createCell(row, countRow++, comber.getAvervg_difference_b_sixHoursOne(), style);
            createCell(row, countRow++, comber.getShift_b_sixHoursTwo(), style);
            createCell(row, countRow++, comber.getAvervg_difference_b_sixHoursTwo(), style);
            createCell(row, countRow++, comber.getTotal_shift_prod_b(), style);
            createCell(row, countRow++, comber.getActual_producation(), style);
            createCell(row, countRow++, comber.getEfficiency(), style);
            createCell(row, countRow++, comber.getTarget_prod_variance_kg(), style);
            createCell(row, countRow++, comber.getTarget_prod_variance(), style);


        }
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

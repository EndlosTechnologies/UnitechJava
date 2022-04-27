package com.unitechApi.MachineSetParameter.ExcelService;

import com.unitechApi.MachineSetParameter.model.Simplex;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SimplexExcelService {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private List<Simplex> simplexeData;

    public SimplexExcelService(List<Simplex> simplexeData) {
        this.simplexeData = simplexeData;
        xssfWorkbook=new XSSFWorkbook();
    }
    private void writeHeaderLine() {
        xssfSheet=xssfWorkbook.createSheet("carding Data");
        Row row=xssfSheet.createRow(5);
        CellStyle style= xssfWorkbook.createCellStyle();
        XSSFFont font=xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        createCell(row,1,"Machine No",style);
        createCell(row,2,"Spindle SPEED (RPM)",style);
        createCell(row,3,"Machine Efficiency %",style);
        createCell(row,4,"TM",style);
        createCell(row,5,"ROVING HANK",style);
        createCell(row,6,"TPI",style);
        createCell(row,7,"Production / Spindle  / 8 Hours (Kg)",style);
        createCell(row,8,"Conversion To 12 Hours / spindle / shift (Kg)",style);
        createCell(row,9,"Production Machine 200 Spindles / Machine / 24 Hours (Kg)",style);
        createCell(row,10,"Production / Spindle  / 8 Hours (Hank)",style);
        createCell(row,11,"Production Machine 200 Spindles / Machine / 24 Hours (Hank)",style);
        createCell(row,12,"Conversion To 12 Hours / spindle / shift (Hank)",style);
        createCell(row,13,"Conversion To 6 Hours / spindle / shift ",style);
        createCell(row,14,"Conversion To 6 Hours  / Machine /shift (Kgs) ",style);
        createCell(row,15,"Conversion To 24 Hours /Machine (Kgs) ",style);
        createCell(row,16,"Conversion To 24 Hours /Machine (Hanks) ",style);

        createCell(row,17,"6 Hours",style);
        createCell(row,18,"Shift A Avg. Difference from Target",style);
        createCell(row,19,"6 Hours",style);
        createCell(row,20,"Shift A Avg. Difference from Target",style);
        createCell(row,21,"total Production Shift A",style);

        createCell(row,22,"6 Hours",style);
        createCell(row,23,"Shift B Avg. Difference from Target",style);
        createCell(row,24,"6 Hours",style);
        createCell(row,25,"Shift B Avg. Difference from Target",style);
        createCell(row,26,"total Production Shift B",style);

        createCell(row,27,"Actual Production",style);
        createCell(row,28,"Efficiency",style);
        createCell(row,29,"Target Production Variance In Kg",style);
        createCell(row,30,"Target Production Variance",style);
        //  createCell(row,2,"total Production Shift A",style);
    }

    private void createCell(Row row, int i, Object value, CellStyle style) {

        xssfSheet.autoSizeColumn(i);
        Cell cell= row.createCell(i);
        if (value instanceof Long){
            cell.setCellValue((Long) value);
        }else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Float){
            cell.setCellValue(String.valueOf(value));
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    private void writeDataLine() {

        int rowcount=6;

        XSSFFont fontHeader=xssfWorkbook.createFont();
        Row rowHeader=xssfSheet.createRow(4);
        Row rowHeader2=xssfSheet.createRow(2);
        CellStyle styleHeader=xssfWorkbook.createCellStyle();
        CellStyle styleHeader2=xssfWorkbook.createCellStyle();
        for (int i = 1; i <= 30; ++i) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellStyle(styleHeader);
        }
        for (int j =19;j<=30 ;++j)
        {
            Cell cell = rowHeader2.createCell(j);
            cell.setCellStyle(styleHeader2);
        }

        styleHeader2.setBorderBottom(BorderStyle.THICK);
        styleHeader2.setBorderTop(BorderStyle.THICK);
        styleHeader2.setBorderLeft(BorderStyle.THICK);
        styleHeader2.setBorderRight(BorderStyle.THICK);
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("B5:Q5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("R5:V5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("W5:AA5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("AB5:AE5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("R3:AE3"));
       // xssfSheet.addMergedRegion(CellRangeAddress.valueOf("G3:H3"));
        //xssfSheet.addMergedRegion(CellRangeAddress.valueOf("1:Y1"));
        styleHeader.setBorderBottom(BorderStyle.MEDIUM);
        styleHeader.setBorderTop(BorderStyle.MEDIUM);
        styleHeader.setBorderLeft(BorderStyle.MEDIUM);
        styleHeader.setBorderRight(BorderStyle.MEDIUM);
        fontHeader.setBold(true);
        fontHeader.setFontHeight(10);
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setFont(fontHeader);
        // styleHeader.set
        createCell(rowHeader2,1,"Simplex ",styleHeader);
        createCell(rowHeader2,17,"Shift Wise Production Report ",styleHeader);
        createCell(rowHeader,1,"Targeted Production ",styleHeader);
        createCell(rowHeader,17,"Shift A Data (Morning) ",styleHeader);
        createCell(rowHeader,22,"Shift B Data (Evening) ",styleHeader);
        createCell(rowHeader,27,"Original Production ",styleHeader);



        XSSFFont font=xssfWorkbook.createFont();
        CellStyle style= xssfWorkbook.createCellStyle();
        font.setFontHeight(14);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        for (Simplex simplexe :simplexeData){
            Row row=xssfSheet.createRow(rowcount++);
            int countRow=1;
            /*
             *   Quality Checker Data
             * */

            createCell(row,countRow++,simplexe.getSimplex().getName(),style);
            createCell(row,countRow++,simplexe.getSpeedRpm(),style);
            createCell(row,countRow++,simplexe.getMachineEfficiency(),style);
            createCell(row,countRow++,simplexe.getTM(),style);
            createCell(row,countRow++,simplexe.getRovingHank() ,style);
            createCell(row,countRow++,simplexe.getTPI(),style);
            createCell(row,countRow++,simplexe.getProductionSpindle8hours(),style);
            createCell(row,countRow++,simplexe.getConversionTo12HoursSpindleShift(),style);
            createCell(row,countRow++,simplexe.getProductionMachine200SpindlesMachineKgPer12Hours(),style);
            createCell(row,countRow++,simplexe.getProductionSpindle8hoursHank(),style);
            createCell(row,countRow++,simplexe.getProductionMachine200SpindlesMachineHankPer12Hours(),style);
            createCell(row,countRow++,simplexe.getConversionTo12HoursSpindleShiftHank(),style);
            createCell(row,countRow++,simplexe.getConversionTo6HoursSpindleShift(),style);
            createCell(row,countRow++,simplexe.getConversionTo6HoursMachineShiftKgs(),style);
            createCell(row,countRow++,simplexe.getConversionTo24HoursMachineShiftKgs(),style);
            createCell(row,countRow++,simplexe.getConversionTo24HoursMachineShiftHank(),style);
            /*
             *  superWiser Data
             * */
            createCell(row,countRow++,simplexe.getShift_a_sixHoursOne(),style);
            createCell(row,countRow++,simplexe.getAvervg_difference_a_sixHoursOne(),style);
            createCell(row,countRow++,simplexe.getShift_a_sixHoursTwo(),style);
            createCell(row,countRow++,simplexe.getAvervg_difference_a_sixHoursTwo(),style);
            createCell(row,countRow++,simplexe.getTotal_shift_prod_a(),style);
            createCell(row,countRow++,simplexe.getShift_b_sixHoursOne(),style);
            createCell(row,countRow++,simplexe.getAvervg_difference_b_sixHoursOne(),style);
            createCell(row,countRow++,simplexe.getShift_b_sixHoursTwo(),style);
            createCell(row,countRow++,simplexe.getAvervg_difference_b_sixHoursTwo(),style);
            createCell(row,countRow++,simplexe.getTotal_shift_prod_b(),style);
            createCell(row,countRow++,simplexe.getActual_producation(),style);
            createCell(row,countRow++,simplexe.getEfficiency(),style);
            createCell(row,countRow++,simplexe.getTarget_prod_variance_kg(),style);
            createCell(row,countRow++,simplexe.getTarget_prod_variance(),style);



        }
    }



    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLine();
        ServletOutputStream outputStream=response.getOutputStream();
        xssfWorkbook.write(outputStream);
        xssfWorkbook.close();
        outputStream.close();
    }
}

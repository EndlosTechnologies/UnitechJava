package com.unitechApi.MachineSetParameter.ExcelService;

import com.unitechApi.MachineSetParameter.model.DrawFramesPerHank;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DrawFramesPerHankExcelService {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private List<DrawFramesPerHank> listData;
    public DrawFramesPerHankExcelService(List<DrawFramesPerHank> listData)
    {
        this.listData=listData;
        xssfWorkbook=new XSSFWorkbook();
    }
    private void writeHeaderLine() {
        xssfSheet=xssfWorkbook.createSheet("drawFramesPerHank Data");
        Row row=xssfSheet.createRow(5);
        CellStyle style= xssfWorkbook.createCellStyle();
        XSSFFont font=xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        createCell(row,1,"machine No",style);
        createCell(row,2,"DELIVERY SPEED (MPM)",style);
        createCell(row,3,"Machine Efficiency",style);
        createCell(row,4,"Production Rate (Kg/Df/Delivery/8 Hour (Hank))",style);
        createCell(row,5,"Production on Rate (Kg/Df/6 Hour (Hank))",style);
        createCell(row,6,"Production on Rate (Kg/Df/24 Hour (Hank))",style);

        createCell(row,7,"6 Hours",style);
        createCell(row,8,"Shift A Avg. Difference from Target",style);
        createCell(row,9,"6 Hours",style);
        createCell(row,10,"Shift A Avg. Difference from Target",style);
        createCell(row,11,"total Production Shift A",style);
        createCell(row,12,"6 Hours",style);
        createCell(row,13,"Shift B Avg. Difference from Target",style);
        createCell(row,14,"6 Hours",style);
        createCell(row,15,"Shift B Avg. Difference from Target",style);
        createCell(row,16,"total Production Shift B",style);
        createCell(row,17,"Actual Production",style);
        createCell(row,18,"Efficiency",style);
        createCell(row,19,"Target Production Variance In Kg",style);
        createCell(row,20,"Target Production Variance",style);
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

        for (int i = 1; i <= 20; ++i) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellStyle(styleHeader);
        }
        for (int j =11;j<=20 ;++j)
        {
            Cell cell = rowHeader2.createCell(j);
            cell.setCellStyle(styleHeader2);
        }

        styleHeader2.setBorderBottom(BorderStyle.THICK);
        styleHeader2.setBorderTop(BorderStyle.THICK);
        styleHeader2.setBorderLeft(BorderStyle.THICK);
        styleHeader2.setBorderRight(BorderStyle.THICK);
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("B5:G5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("H5:L5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("M5:Q5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("R5:U5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("H3:U3"));
    //    xssfSheet.addMergedRegion(CellRangeAddress.valueOf("B3:C3"));
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
        createCell(rowHeader2,1,"Draw Frames In Hank ",styleHeader);
        createCell(rowHeader,1,"Targeted Production ",styleHeader);
        createCell(rowHeader2,7,"Shift Wise Production Report ",styleHeader);

        createCell(rowHeader,7,"Shift A Data (Morning) ",styleHeader);
        createCell(rowHeader,12,"Shift B Data (Evening) ",styleHeader);
        createCell(rowHeader,17,"Original Production ",styleHeader);



        XSSFFont font=xssfWorkbook.createFont();
        CellStyle style= xssfWorkbook.createCellStyle();
        font.setFontHeight(14);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        for (DrawFramesPerHank drawFramesPerHank :listData){
            Row row=xssfSheet.createRow(rowcount++);
            int countRow=1;
            createCell(row,countRow++,drawFramesPerHank.getDrawFramesPerHanks().getName(),style);
            createCell(row,countRow++,drawFramesPerHank.getDeliveryspeed(),style);
            createCell(row,countRow++,drawFramesPerHank.getMachineefficency(),style);
            createCell(row,countRow++,drawFramesPerHank.getProductiononratekgdfper8hour(),style);
            createCell(row,countRow++,drawFramesPerHank.getMachineefficencykgdfper6hours(),style);
            createCell(row,countRow++,drawFramesPerHank.getMachineefficencykgdfperday(),style);
            createCell(row,countRow++,drawFramesPerHank.getShift_a_sixHoursOne(),style);
            createCell(row,countRow++,drawFramesPerHank.getAvervg_difference_a_sixHoursOne(),style);
            createCell(row,countRow++,drawFramesPerHank.getShift_a_sixHoursTwo(),style);
            createCell(row,countRow++,drawFramesPerHank.getAvervg_difference_a_sixHoursTwo(),style);
            createCell(row,countRow++,drawFramesPerHank.getTotal_shift_prod_a(),style);
            createCell(row,countRow++,drawFramesPerHank.getShift_b_sixHoursOne(),style);
            createCell(row,countRow++,drawFramesPerHank.getAvervg_difference_b_sixHoursOne(),style);
            createCell(row,countRow++,drawFramesPerHank.getShift_b_sixHoursTwo(),style);
            createCell(row,countRow++,drawFramesPerHank.getAvervg_difference_b_sixHoursTwo(),style);
            createCell(row,countRow++,drawFramesPerHank.getTotal_shift_prod_b(),style);
            createCell(row,countRow++,drawFramesPerHank.getActual_producation(),style);
            createCell(row,countRow++,drawFramesPerHank.getEfficiency(),style);
            createCell(row,countRow++,drawFramesPerHank.getTarget_prod_variance_kg(),style);
            createCell(row,countRow++,drawFramesPerHank.getTarget_prod_variance(),style);



        }
    }



    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLine();
        ServletOutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        xssfWorkbook.close();
        outputStream.close();
    }
}

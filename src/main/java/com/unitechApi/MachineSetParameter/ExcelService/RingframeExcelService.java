package com.unitechApi.MachineSetParameter.ExcelService;

import com.unitechApi.MachineSetParameter.model.RingFrame;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RingframeExcelService {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private List<RingFrame> ringFrameData;

    public RingframeExcelService(List<RingFrame> ringFrameData) {
        this.ringFrameData = ringFrameData;
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
        createCell(row,3,"Type",style);
        createCell(row,4,"Count",style);

        createCell(row,5,"TM",style);
        createCell(row,6,  "TPI",style);
        createCell(row,7,"Machine Efficiency %",style);
        createCell(row,8,"Production Spindle (Grams)",style);
        createCell(row,9,"Production / Spindle  / 8 Hours (Kg)",style);
        createCell(row,10,"Production / Machine / 24 Hours (Kg)",style);
        createCell(row,11,"Production / Machine / 2 Hours (Kg)",style);
        createCell(row,12,"Pneumafil Waste %",style);
        createCell(row,13,"Idle Spindle %",style);
        createCell(row,14,"Doffing Loss % ",style);
        createCell(row,15,"Total Loss % ",style);
        createCell(row,16,"Total Loss in kg% ",style);
        createCell(row,17,"Net Production  ",style);


        createCell(row,18,"2 Hours",style);
        createCell(row,19,"result in Hank",style);
        createCell(row,20,"Shift A Avg. Difference from Target",style);

        createCell(row,21,"2 Hours",style);
        createCell(row,22,"result in Hank",style);
        createCell(row,23,"Shift A Avg. Difference from Target",style);

        createCell(row,24,"2 Hours",style);
        createCell(row,25,"result in Hank",style);
        createCell(row,26,"Shift A Avg. Difference from Target",style);

        createCell(row,27,"2 Hours",style);
        createCell(row,28,"result in Hank",style);
        createCell(row,29,"Shift A Avg. Difference from Target",style);

        createCell(row,30,"2 Hours",style);
        createCell(row,31,"result in Hank",style);
        createCell(row,32,"Shift A Avg. Difference from Target",style);

        createCell(row,33,"2 Hours",style);
        createCell(row,34,"result in Hank",style);;
        createCell(row,35,"Shift A Avg. Difference from Target",style);
        createCell(row,36,"Total Shift A Production",style);


        createCell(row,37,"2 Hours",style);
        createCell(row,38,"result in Hank",style);
        createCell(row,39,"Shift B Avg. Difference from Target",style);
        createCell(row,40,"2 Hours",style);
        createCell(row,41,"result in Hank",style);
        createCell(row,42,"Shift B Avg. Difference from Target",style);
        createCell(row,43,"2 Hours",style);
        createCell(row,44,"result in Hank",style);
        createCell(row,45,"Shift B Avg. Difference from Target",style);
        createCell(row,46,"2 Hours",style);
        createCell(row,47,"result in Hank",style);
        createCell(row,48,"Shift B Avg. Difference from Target",style);
        createCell(row,49,"2 Hours",style);
        createCell(row,50,"result in Hank",style);
        createCell(row,51,"Shift B Avg. Difference from Target",style);
        createCell(row,52,"2 Hours",style);
        createCell(row,53,"result in Hank",style);
        createCell(row,54,"Shift B Avg. Difference from Target",style);
        createCell(row,55,"Total Shift B",style);


        createCell(row,56,"Actual Production",style);
        createCell(row,57,"Efficiency",style);
        createCell(row,58,"Target Prod. Variance In kg",style);
        createCell(row,59,"Total Prod. Variance",style);
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
        for (int i = 1; i <= 59; ++i) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellStyle(styleHeader);
        }
        for (int j =18;j<=59 ;++j)
        {
            Cell cell = rowHeader2.createCell(j);
            cell.setCellStyle(styleHeader2);
        }
        styleHeader2.setBorderBottom(BorderStyle.THICK);
        styleHeader2.setBorderTop(BorderStyle.THICK);
        styleHeader2.setBorderLeft(BorderStyle.THICK);
        styleHeader2.setBorderRight(BorderStyle.THICK);
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("B5:R5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("S5:AK5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("AL5:BD5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("BE5:BH5"));
        xssfSheet.addMergedRegion(CellRangeAddress.valueOf("S3:BH3"));
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
        createCell(rowHeader2,1,"ring frame ",styleHeader);
        createCell(rowHeader2,18,"Shift Wise Production Report ",styleHeader);
        createCell(rowHeader,1,"Targeted Production ",styleHeader);
        createCell(rowHeader,18,"Shift A Data (Morning) ",styleHeader);
        createCell(rowHeader,37,"Shift B Data (Evening) ",styleHeader);
        createCell(rowHeader,56,"Original Production ",styleHeader);



        XSSFFont font=xssfWorkbook.createFont();
        CellStyle style= xssfWorkbook.createCellStyle();
        font.setFontHeight(14);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        for (RingFrame ringframe :ringFrameData){
            Row row=xssfSheet.createRow(rowcount++);
            int countRow=1;
            /*
             *   Quality Checker Data
             * */

            createCell(row,countRow++,ringframe.getRingframe().getName(),style);
            createCell(row,countRow++,ringframe.getSpindleRpm(),style);
            createCell(row,countRow++,ringframe.getType().name(),style);
            createCell(row,countRow++,ringframe.getRingFrameCount(),style);
            createCell(row,countRow++,ringframe.getTM() ,style);
            createCell(row,countRow++,ringframe.getTPI(),style);
            createCell(row,countRow++,ringframe.getMachineEfficiency(),style);
            createCell(row,countRow++,ringframe.getProductionSpindleGrams(),style);
            createCell(row,countRow++,ringframe.getProductionSpindle8HoursKg(),style);
            createCell(row,countRow++,ringframe.getProductionSpindle24HoursKg(),style);
            createCell(row,countRow++,ringframe.getProductionSpindle2HoursKg(),style);
            createCell(row,countRow++,ringframe.getPneumafilWaste(),style);
            createCell(row,countRow++,ringframe.getIdleSpindle(),style);
            createCell(row,countRow++,ringframe.getDoffingLoss(),style);
            createCell(row,countRow++,ringframe.getTotalLoss(),style);
            createCell(row,countRow++,ringframe.getTotalLossKg(),style);
            createCell(row,countRow++,ringframe.getNetProduction(),style);
            /*
             *  superWiser Data And Shift A Reading
             * */
            createCell(row,countRow++,ringframe.getShift_a_twoHoursOne(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankOne(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_a_twoHoursOne(),style);

            createCell(row,countRow++,ringframe.getShift_a_twoHoursTwo(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankTwo(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_a_twoHoursTwo(),style);

            createCell(row,countRow++,ringframe.getShift_a_twoHoursThree(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankThree(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_a_twoHoursThree(),style);

            createCell(row,countRow++,ringframe.getShift_a_twoHoursFour(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankFour(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_a_twoHoursFour(),style);

            createCell(row,countRow++,ringframe.getShift_a_twoHoursFive(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankFive(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_a_twoHoursFive(),style);

            createCell(row,countRow++,ringframe.getShift_a_twoHoursSix(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankSix(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_a_twoHoursSix(),style);
            createCell(row,countRow++,ringframe.getTotal_shift_prod_a(),style);
            /*
            *   Shift B Reading
            * */
            createCell(row,countRow++,ringframe.getShift_b_twoHoursOne(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankSeven(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_b_twoHoursOne(),style);

            createCell(row,countRow++,ringframe.getShift_b_twoHoursTwo(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankEight(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_b_twoHoursTwo(),style);

            createCell(row,countRow++,ringframe.getShift_b_twoHoursThree(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankNine(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_b_twoHoursThree(),style);

            createCell(row,countRow++,ringframe.getShift_b_twoHoursFour(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankTen(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_b_twoHoursFour(),style);

            createCell(row,countRow++,ringframe.getShift_b_twoHoursFive(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankEleven(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_b_twoHoursFive(),style);

            createCell(row,countRow++,ringframe.getShift_b_twoHoursSix(),style);
            createCell(row,countRow++,ringframe.getAverageshift_a_HankTwelev(),style);
            createCell(row,countRow++,ringframe.getAvervg_difference_b_twoHoursSix(),style);
            createCell(row,countRow++,ringframe.getTotal_shift_prod_b(),style);

            createCell(row,countRow++,ringframe.getActual_producation(),style);
            createCell(row,countRow++,ringframe.getEfficiency(),style);
            createCell(row,countRow++,ringframe.getTarget_prod_variance_kg(),style);
            createCell(row,countRow++,ringframe.getTarget_prod_variance(),style);



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

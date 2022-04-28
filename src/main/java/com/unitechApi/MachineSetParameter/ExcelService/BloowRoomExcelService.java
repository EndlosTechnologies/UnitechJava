package com.unitechApi.MachineSetParameter.ExcelService;

import com.unitechApi.MachineSetParameter.model.BloowRoom;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Slf4j
public class BloowRoomExcelService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<BloowRoom> ListUser;


    public BloowRoomExcelService(List<BloowRoom> listData){
        this.ListUser=listData;
        workbook=new XSSFWorkbook();
    }
    private void writeHeaderLine() {
        sheet=workbook.createSheet("BloowRoom Data");
        Row row=sheet.createRow(5);
        CellStyle style= workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        createCell(row,1,"machine No",style);
        createCell(row,2,"DELIVERY SPEED (MPM)",style);
        createCell(row,3,"Silver Hank",style);
        createCell(row,4,"Machine Efficiency",style);
        createCell(row,5,"Production on Rate (Kg/card/Hour)",style);
        createCell(row,6,"Production on Rate (Kg/card/Shift)",style);
        createCell(row,7,"Production on Rate (Kg/card/6Hour)",style);
        createCell(row,8,"Production on Rate (Kg/card/Day)",style);

        createCell(row,9,"6 Hours",style);
        createCell(row,10,"Shift A Avg. Difference from Target",style);
        createCell(row,11,"6 Hours",style);
        createCell(row,12,"Shift A Avg. Difference from Target",style);
        createCell(row,13,"total Production Shift A",style);
        createCell(row,14,"6 Hours",style);
        createCell(row,15,"Shift B Avg. Difference from Target",style);
        createCell(row,16,"6 Hours",style);
        createCell(row,17,"Shift B Avg. Difference from Target",style);
        createCell(row,18,"total Production Shift B",style);
        createCell(row,19,"Actual Production",style);
        createCell(row,20,"Efficiency",style);
        createCell(row,21,"Target Production Variance In Kg",style);
        createCell(row,22,"Target Production Variance",style);
        //  createCell(row,2,"total Production Shift A",style);
    }

    private void createCell(Row row, int i, Object value, CellStyle style) {

        sheet.autoSizeColumn(i);
        Cell cell= row.createCell(i);
        if (value instanceof Long){
            cell.setCellValue((Long) value);
        }else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Float){
            //Float f=Float.parseFloat(String.format("%.2f"));
            cell.setCellValue(String.valueOf(value));
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLine() {

        int rowcount=6;

        XSSFFont fontHeader=workbook.createFont();
        Row rowHeader=sheet.createRow(4);
        Row rowHeader2=sheet.createRow(2);
        CellStyle styleHeader=workbook.createCellStyle();
        CellStyle styleHeader2=workbook.createCellStyle();
        for (int i = 1; i <= 22; ++i) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellStyle(styleHeader);
        }
        for (int j =9;j<=22 ;++j)
        {
            Cell cell = rowHeader2.createCell(j);
            cell.setCellStyle(styleHeader2);
        }

        styleHeader2.setBorderBottom(BorderStyle.THICK);
        styleHeader2.setBorderTop(BorderStyle.THICK);
        styleHeader2.setBorderLeft(BorderStyle.THICK);
        styleHeader2.setBorderRight(BorderStyle.THICK);
        sheet.addMergedRegion(CellRangeAddress.valueOf("B5:I5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("J5:N5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("O5:S5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("T5:W5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("J3:W3"));
       // sheet.addMergedRegion(CellRangeAddress.valueOf("G3:H3"));
        //sheet.addMergedRegion(CellRangeAddress.valueOf("1:Y1"));
        styleHeader.setBorderBottom(BorderStyle.MEDIUM);
        styleHeader.setBorderTop(BorderStyle.MEDIUM);
        styleHeader.setBorderLeft(BorderStyle.MEDIUM);
        styleHeader.setBorderRight(BorderStyle.MEDIUM);
        fontHeader.setBold(true);
        fontHeader.setFontHeight(10);
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setFont(fontHeader);
        // styleHeader.set
        createCell(rowHeader2,1,"BloowRoom ",styleHeader);
        createCell(rowHeader2,9,"Shift Wise Production Report ",styleHeader);
        createCell(rowHeader,1,"Targeted Production ",styleHeader);
        createCell(rowHeader,9,"Shift A Data (Morning) ",styleHeader);
        createCell(rowHeader,14,"Shift B Data (Evening) ",styleHeader);
        createCell(rowHeader,19,"Original Production ",styleHeader);



        XSSFFont font=workbook.createFont();
        CellStyle style= workbook.createCellStyle();
        font.setFontHeight(14);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        for (BloowRoom bloowRoom :ListUser){
            Row row=sheet.createRow(rowcount++);
            int countRow=1;
            createCell(row,countRow++,bloowRoom.getAddBloowroom().getName(),style);
            createCell(row,countRow++,bloowRoom.getDeliveryspeed(),style);
            createCell(row,countRow++,bloowRoom.getSilverhank(),style);
            createCell(row,countRow++,bloowRoom.getMachineefficency(),style);
            createCell(row,countRow++,bloowRoom.getProductiononratekgcardperhour(),style);
            createCell(row,countRow++,bloowRoom.getMachineefficencykgcardpershift(),style);
            createCell(row,countRow++,bloowRoom.getMachineefficencykgcardpersixhours(),style);
            createCell(row,countRow++,bloowRoom.getMachineefficencykgcardperday(),style);
            log.info("{ } calculation of Data  "+bloowRoom.getMachineefficencykgcardpershift() );
            createCell(row,countRow++,bloowRoom.getShift_a_sixHoursOne(),style);
            createCell(row,countRow++,bloowRoom.getAvervg_difference_a_sixHoursOne(),style);
            createCell(row,countRow++,bloowRoom.getShift_a_sixHoursTwo(),style);
            createCell(row,countRow++,bloowRoom.getAvervg_difference_a_sixHoursTwo(),style);
            createCell(row,countRow++,bloowRoom.getTotal_shift_prod_a(),style);
            createCell(row,countRow++,bloowRoom.getShift_b_sixHoursOne(),style);
            createCell(row,countRow++,bloowRoom.getAvervg_difference_b_sixHoursOne(),style);
            createCell(row,countRow++,bloowRoom.getShift_b_sixHoursTwo(),style);
            createCell(row,countRow++,bloowRoom.getAvervg_difference_b_sixHoursTwo(),style);
            createCell(row,countRow++,bloowRoom.getTotal_shift_prod_b(),style);
            createCell(row,countRow++,bloowRoom.getActual_producation(),style);
            createCell(row,countRow++,bloowRoom.getEfficiency(),style);
            createCell(row,countRow++,bloowRoom.getTarget_prod_variance_kg(),style);
            createCell(row,countRow++,bloowRoom.getTarget_prod_variance(),style);

        }
    }



    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLine();
        ServletOutputStream outputStream=response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}

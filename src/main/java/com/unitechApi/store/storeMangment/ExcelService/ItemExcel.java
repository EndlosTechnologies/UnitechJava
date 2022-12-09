package com.unitechApi.store.storeMangment.ExcelService;

import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ItemExcel {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(ItemExcel.class);
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<StoreItemModel> ListItem;

    public ItemExcel( List<StoreItemModel> listItem) {
        workbook = new XSSFWorkbook();
        this.ListItem = listItem;
    }

    // this method create for write a header Line in Excel
    private void writeHeaderLine() {
        sheet= workbook.createSheet("itemList");
        Row row= sheet.createRow(2);
        CellStyle style= workbook.createCellStyle();
        XSSFFont font =workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        createCell(row,1,"item name",style);
        createCell(row,2,"item Description",style);
        createCell(row,3,"drawingNo",style);
        createCell(row,4,"catalogNo",style);
        createCell(row,5,"frequency",style);
        createCell(row,6,"tax",style);
        createCell(row,7,"name",style);
        createCell(row,8,"quantity",style);

        createCell(row,9,"In Date",style);
        createCell(row,10,"expiry Date",style);

        createCell(row,11,"item Category",style);
        createCell(row,12,"item unit",style);
    }
    /*
    thia method create for Field Validation
    for cell
    * */
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
        } else if (value instanceof  Integer) {
            cell.setCellValue((int) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    /*
     * this method create for
     *
     * write data in Excel from db
     * */
    private void writeDateLine() {
        int rowcount= 5;
        XSSFFont font=workbook.createFont();
        CellStyle style= workbook.createCellStyle();
        font.setFontHeight(14);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        for (StoreItemModel storeItemModel: ListItem)
        {
            Row row=sheet.createRow(rowcount++);
            int countRow=1;
           // logger.debug("data " +storeItemModel);
            createCell(row,countRow++,storeItemModel.getItemName(),style);
            createCell(row,countRow++,storeItemModel.getItemDescription(),style);
            createCell(row,countRow++,storeItemModel.getDrawingNo(),style);
            createCell(row,countRow++,storeItemModel.getCatalogNo(),style);
            createCell(row,countRow++,storeItemModel.getFrequency(),style);
            createCell(row,countRow++,storeItemModel.getPaytax(),style);

            createCell(row,countRow++,storeItemModel.getQuantity(),style);
            createCell(row,countRow++,storeItemModel.getCreated(),style);
            createCell(row,countRow++,storeItemModel.getExpiryDays(),style);
            createCell(row,countRow++,storeItemModel.getProductCategory().getProductName(),style);
            createCell(row,countRow++,storeItemModel.getUnit().getUnitName(),style);

        }
    }
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDateLine();
        ServletOutputStream outputStream=response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}

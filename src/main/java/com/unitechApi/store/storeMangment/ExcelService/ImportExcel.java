package com.unitechApi.store.storeMangment.ExcelService;

import com.unitechApi.store.storeMangment.Model.ProductCategory;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImportExcel {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Id", "item name", "item Description","drawingNo"
            ,"catalogNo","frequency","tax","quantity","In Date",
            "expiry Date","item Category","item unit" };
    static String SHEET = "Item";
    private static final Logger log = LoggerFactory.getLogger(ImportExcel.class);


    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            log.info("file content type ",file.getContentType());
            return false;
        }

        return true;
    }
    public static ByteArrayInputStream tutorialsToExcel(List<StoreItemModel> storeItemModels) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (StoreItemModel storeitem : storeItemModels) {
                Row row = sheet.createRow(rowIdx++);
                /*{"Id", "item name", "item Description","drawingNo"
            ,"catalogNo","frequency","tax","quantity","In Date",
            "expiry Date","item Category","item unit" };*/
                row.createCell(0).setCellValue(storeitem.getItemId());
                row.createCell(1).setCellValue(storeitem.getItemName());
                row.createCell(2).setCellValue(storeitem.getItemDescription());
                row.createCell(3).setCellValue(storeitem.getDrawingNo());
                row.createCell(4).setCellValue(storeitem.getCatalogNo());
                row.createCell(5).setCellValue(storeitem.getFrequency());
                row.createCell(6).setCellValue(storeitem.getPaytax());
                row.createCell(7).setCellValue(storeitem.getQuantity());
                row.createCell(8).setCellValue(storeitem.getInDate());
                row.createCell(9).setCellValue(storeitem.getExpiryDays());
                row.createCell(10).setCellValue(storeitem.getProductCategory().getProductName());
                row.createCell(11).setCellValue(storeitem.getUnit().getUnitName());


            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
    public static List<StoreItemModel> excelToTutorials(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<StoreItemModel> itemmodels = new ArrayList<StoreItemModel>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                StoreItemModel itemmodel = new StoreItemModel();

                DataFormatter df = new DataFormatter();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();


                    switch (cellIdx) {
                        case 0:
                            itemmodel.setItemId((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            itemmodel.setItemName(currentCell.getStringCellValue());
                            break;

                        case 2:
                            itemmodel.setItemDescription(currentCell.getStringCellValue());
                            break;

                        case 3:
                            itemmodel.setDrawingNo(currentCell.getStringCellValue());
                            break;

                        case 4:
                            itemmodel.setCatalogNo(currentCell.getStringCellValue());
                            break;
                        case 5:
                            itemmodel.setFrequency(currentCell.getStringCellValue());
                            break;
                        case 6:
                            itemmodel.setPaytax((int) currentCell.getNumericCellValue());
                            break;

                        case 7:
                            itemmodel.setQuantity((int) currentCell.getNumericCellValue());
                            break;
                        case 8:
                            itemmodel.setInDate( currentCell.getDateCellValue());
                            break;
                        case 9:
                            itemmodel.setExpiryDays((int) currentCell.getNumericCellValue());
                            break;
                        case 10:
                          ///      double s =currentCell.getNumericCellValue();
                            itemmodel.setProductCategory(new ProductCategory() );
                            break;
                        case 11:
                            itemmodel.setUnit(itemmodel.getUnit());
                            break;
                        default:
                            break;
                    }

                    cellIdx++;
                }

                itemmodels.add(itemmodel);
            }

            workbook.close();

            return itemmodels;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}

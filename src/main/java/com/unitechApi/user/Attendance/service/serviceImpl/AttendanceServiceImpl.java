package com.unitechApi.user.Attendance.service.serviceImpl;

import com.unitechApi.store.productCategory.model.ProductCategory;
import com.unitechApi.store.unit.model.Unit;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Iterator;
import java.util.List;

@Service
public class AttendanceServiceImpl {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;
    public static final Logger log= LoggerFactory.getLogger(AttendanceServiceImpl.class);

    public void AttendanceExcelUpload(InputStream is)
    {
        int batchSize = 20;
        Connection connection;

        try {
            long start = System.currentTimeMillis();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            String sql = "INSERT INTO store_management.attendance_report (aintime, aouttime,dbreak, workduration,employeecode,employeename,remarks,shift,status) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            DataFormatter fmt = new DataFormatter();

            int count = 0;
            rowIterator.next();// skip the header row
            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                while (cellIterator.hasNext()) {
                    if (cellIterator.hasNext()==false)
                    {
                        workbook.close();
                    }
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            String itemname = nextCell.getStringCellValue();
                            statement.setString(1, itemname);
                            log.info("item name ={}", itemname);
                            break;
                        case 1:
                            String itemDescription = nextCell.getStringCellValue();
                            statement.setString(2, itemDescription);
                            log.info("item Descr =" + itemDescription);
                            break;
                        case 2:
                            int remainingItem = (int) nextCell.getNumericCellValue();
                            statement.setInt(3, remainingItem);
                            log.info("item remainingItem ={}", remainingItem);
                            break;
                        case 3:
                            String drawingno = fmt.formatCellValue(nextCell);
                            log.info("dr no ={}", drawingno);
                            statement.setString(4, String.valueOf(drawingno));
                            //drawing,catalog,frequency,pay-tax,quantity,indate,expirydays,p_id,u_id
                            break;
                        case 4:
                            String catalogNumber = nextCell.getStringCellValue();
                            log.info("catalog Number ={}", catalogNumber);
                            statement.setString(5, String.valueOf(catalogNumber));
                            break;
                        case 5:
                            double frequency = nextCell.getNumericCellValue();
                            log.info("frequency Number ={}", frequency);
                            statement.setLong(6, (long) frequency);
                            break;
                        case 6:
                            int paytax = (int) nextCell.getNumericCellValue();
                            log.info("tax is ={}" + paytax);
                            statement.setInt(7, paytax);
                            break;
                        case 7:
                            float quantity = Float.parseFloat(fmt.formatCellValue(nextCell));
                            log.info("quantity is ={}", quantity);
                            statement.setFloat(8, quantity);
                            break;
                        case 8:
                            double itemRate = Double.parseDouble(fmt.formatCellValue(nextCell));
                            log.info("item Rate {}", itemRate);
                            statement.setLong(9, (long) itemRate);
                            break;


                    }

                }
                statement.addBatch();
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            workbook.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
            connection.close();

            long end = System.currentTimeMillis();
            System.out.printf("Import done in %d ms\n", (end - start));

        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        } catch (SQLException ex2) {
            System.out.println("Database error");
            ex2.printStackTrace();
        }
    }



}

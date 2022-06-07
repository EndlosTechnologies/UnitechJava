package com.unitechApi.user.service;


import com.unitechApi.store.productCategory.model.ProductCategory;
import com.unitechApi.store.productCategory.repository.ProductCategoryRepository;
import com.unitechApi.store.unit.model.Unit;
import com.unitechApi.store.unit.repository.UnitRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
public class DbService {

    @Value("${spring.datasource.url}")
    private String url;
    //public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/unitechdev";

    @Value("${spring.datasource.username}")
    private String username;
    //private static final String DEFAULT_USERNAME = "postgres";
    @Value("${spring.datasource.password}")
    private String password;
    // private static final String DEFAULT_PASSWORD = "postgres";
    public static final Logger log = LoggerFactory.getLogger(DbService.class);
    private final ProductCategoryRepository productCategoryRepository;
    private final UnitRepository unitRepository;

    public DbService(ProductCategoryRepository productCategoryRepository, UnitRepository unitRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.unitRepository = unitRepository;
    }


    private Connection createdConnecton() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void machineReadingDelete() throws SQLException {
        Connection connection = createdConnecton();
        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE machinereading.cardings," +
                "machinereading.bloowroom_machine," +
                "machinereading.comber_machine," +
                "machinereading.drawframes_machine," +
                "machinereading.drawframesperhank_machine ," +
                "machinereading.finisher_machine" +
                ",machinereading.finisherhank_machine" +
                ",machinereading.lapformermachine " +
                ",machinereading.packing_machine" +
                ",machinereading.ringframe_machine" +
                ",machinereading.simplex_machine" +
                ",machinereading.utiliity_machine" +
                ",machinereading.wasteroom_machine" +
                ",machinereading.winding_machine RESTART IDENTITY");
        statement.close();
    }

    public void excel(InputStream ls) {

        int batchSize = 20;
        Connection connection = null;
        try {
            long start = System.currentTimeMillis();
            Workbook workbook = new XSSFWorkbook(ls);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            String sql = "INSERT INTO store_management.item (itemname, itemdescription,remainingitem, drawingno,catalogno,frequency,paytax,quantity,created,expirydays,p_id,u_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            int count = 0;
            rowIterator.next(); // skip the header row

            while (rowIterator.hasNext()) {

                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();

                    int columnIndex = nextCell.getColumnIndex();

                    switch (columnIndex) {

                        case 0:
                            String itemname = nextCell.getStringCellValue();
                            statement.setString(1, itemname);
                            break;
                        case 1:
                            String itemdescription = nextCell.getStringCellValue();
                            statement.setString(2, itemdescription);
                            break;
                        case 2:
                            int remainingitem = (int) nextCell.getNumericCellValue();
                            statement.setInt(3, remainingitem);
                            break;
                        case 3:
                            String drawingno = nextCell.getStringCellValue();
                            statement.setString(4, drawingno);
                            //drawing,catalog,frequency,pay-tax,quantity,indate,expirydays,p_id,u_id
                            break;
                        case 4:
                            String catalogno = nextCell.getStringCellValue();
                            statement.setString(5, catalogno);
                            break;
                        case 5:
                            double frequency = nextCell.getNumericCellValue();
                            statement.setLong(6, (long) frequency);
                            break;
                        case 6:
                            int paytax = (int) nextCell.getNumericCellValue();
                            statement.setInt(7, paytax);
                            break;
                        case 7:
                            long quantity = (long) nextCell.getNumericCellValue();
                            statement.setLong(8, quantity);
                            break;
                        case 8:
                            java.util.Date created = nextCell.getDateCellValue();
                            log.info("Date value in ", created);
                            statement.setTimestamp(9, new Timestamp(created.getTime()));
                            break;
                        case 9:
                            int expirydays = (int) nextCell.getNumericCellValue();
                            statement.setInt(10, expirydays);
                            break;
                        case 10:
                            String p_id = nextCell.getStringCellValue();
                            log.info("excel product name {}", nextCell.getStringCellValue());
                            List<ProductCategory> productCategory = productCategoryRepository.findAll();
                            for (ProductCategory p : productCategory) {
                                log.info("product id ", productCategory.toArray().length);
                                if (p.getProductName().equals(p_id)) {
                                    statement.setLong(11, p.getPid());
                                }
                            }
                            break;
                        case 11:
                            String u_id = nextCell.getStringCellValue();
                            List<Unit> unit = unitRepository.findAll();
                            for (Unit u : unit) {
                                if (u.getUnitName().equals(u_id)) {
                                    statement.setLong(12, u.getUid());
                                }
                            }
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

        } catch (
                IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        } catch (
                SQLException ex2) {
            System.out.println("Database error");
            ex2.printStackTrace();
        }
    }

}


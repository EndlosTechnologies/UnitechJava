package com.unitechApi.user.service;


import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;


@Service
public class DbService {
    private String path;


    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/unitechdev";
    private static final String DEFAULT_USERNAME = "postgres";
    private static final String DEFAULT_PASSWORD = "postgres";

    private static Connection createdConnecton() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DEFAULT_PASSWORD, DEFAULT_USERNAME);
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



    }


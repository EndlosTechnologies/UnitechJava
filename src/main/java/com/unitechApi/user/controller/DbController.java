package com.unitechApi.user.controller;

import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.user.service.DbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/db/")
public class DbController {
    private final DbService dbService;

    public DbController(DbService dbService) {
        this.dbService = dbService;
    }
    @DeleteMapping
    public ResponseEntity<?> machineReadingFlush() throws SQLException {
        dbService.machineReadingDelete();
        return  new ResponseEntity<>(new MessageResponse("Successfully Flush Data"),HttpStatus.NO_CONTENT);
    }
    @GetMapping("/")
    public ResponseEntity<?> DateExtends() throws IOException, InterruptedException {

        Runtime rt = Runtime.getRuntime();
        Process p;
        ProcessBuilder pb;
        rt = Runtime.getRuntime();

        pb = new ProcessBuilder("/usr/bin/pg_dump",
                "--host=localhost",
                "--inserts", "--data-only",
                "--port=5432",
                "--username=postgres",
                "--no-password",
                "--format=custom",
                "--blobs",
                "--verbose", "--file=/home/endlos/Unitech/unitech.sql", "unitechdev");
        try {
            final Map<String, String> env = pb.environment();
            env.put("PGPASSWORD", "postgres");
            p = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();

            }
            r.close();
            p.waitFor();
            System.out.println(p.exitValue());

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/reading")
    public ResponseEntity<?> particularData(HttpServletResponse response) {
        Runtime rt = Runtime.getRuntime();
        Process p;
        ProcessBuilder pb;
        rt = Runtime.getRuntime();

        pb = new ProcessBuilder("/usr/bin/pg_dump",
                "--host=localhost",
                "--dbname=unitechdev",
                "--inserts", "--data-only",
                "--port=5432", "--username=postgres",
                "--table=machinereading.cardings*",
                "--table=machinereading.bloowroom_machine*",
                "--no-password",
                "--no-privileges",
                "--format=custom",
                "--blobs",
                "--verbose", "--file=/home/endlos/Unitech/reading.SQL");
        try {
            final Map<String, String> env = pb.environment();
            env.put("PGPASSWORD", "postgres");
            p = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();

            }
            r.close();
            p.waitFor();
            System.out.println(p.exitValue());

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "exc")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        dbService.excel(file.getInputStream());
    }

}

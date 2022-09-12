package com.unitechApi.common.query;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
public enum FieldType {
    BOOLEAN {
        public Object parse(String value) {
            return Boolean.valueOf(value);
        }
    },

    CHAR {
        public Object parse(String value) {
            return value.charAt(0);
        }
    },

    DATE {
        public Object parse(String value) {
            Object date = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String pattern = "dd-MM-yyyy";
                String pa="dd-MM-yyyy HH:mm:ss";


                //SimpleDateFormat dates = new SimpleDateFormat(pa);
                //Date dt=new Date();
               // LocalDateTime dateTime=LocalDateTime.parse(formatter);
               // dateTime.plusDays(1);
                date = LocalDateTime.parse(value,formatter);
               //date= LocalDate.parse(value,formatter).plus(23, ChronoUnit.HOURS).plus(59,ChronoUnit.MINUTES);
                log.info("date {}", date);
            } catch (Exception e) {
                log.info("Failed parse field type DATE {}", e.getMessage());
            }

            return date;
        }
    },

    DOUBLE {
        public Object parse(String value) {
            return Double.valueOf(value);
        }
    },

    INTEGER {
        public Object parse(String value) {
            return Integer.valueOf(value);
        }
    },

    LONG {
        public Object parse(String value) {
            return Long.valueOf(value);
        }
    },

    STRING {
        public Object parse(String value) {
            return value;
        }
    },
    ENUM {
        @Override
        public Object parse(String value) {

            return String.valueOf(value);
        }
    },
    FLOAT {
        public Object parse(String value) {
            return Float.valueOf(value);
        }
    };


    public abstract Object parse(String value);
}

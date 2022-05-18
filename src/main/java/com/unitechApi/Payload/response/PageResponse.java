package com.unitechApi.Payload.response;

import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class PageResponse {
    public static final String RECORDFOUND = "Records Found";
    public static final String RECORDNOTFOUND = "Records Not  Found";


    public static Map<String, Object> SuccessResponse(Object Response) {


        Map<String, Object> responsemap = new HashMap<>();
        if (responsemap.size()<0){
            UnSuccessStatus(responsemap);
        } else{
            AddSuccessStatus(responsemap);
            responsemap.put("Data", Response);
        }

        return responsemap;
    }

    private static void UnSuccessStatus(Map<String, Object> responsemap) {
        responsemap.put("Not Done", Boolean.FALSE);
        responsemap.put("Message", RECORDNOTFOUND);
    }

    //response success and message
    private static void AddSuccessStatus(Map<String, Object> responsemap) {
        responsemap.put("Done", Boolean.TRUE);
        responsemap.put("Message", RECORDFOUND);

    }


    public static Map<String, Object> pagebleResponse(Page<?> response, Pagination pagination) {

        pagination.setPagecount(response.getTotalPages());
        pagination.setRowcount(response.getTotalElements());
        pagination.setSort(response.getSort());
        Map<String, Object> responsemap = new HashMap<>();
        responsemap.put("Data", response.getContent());
        responsemap.put("Pagination", pagination);
        AddSuccessStatus(responsemap);
        return responsemap;
    }


}

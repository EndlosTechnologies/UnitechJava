package com.unitechApi.store.indent.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.store.indent.Service.IndentEventHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/unitech/api/v1/store/eve")
public class IndentEventController {
    private final IndentEventHistory indentEventHistory;

    public IndentEventController(IndentEventHistory indentEventHistory) {
        this.indentEventHistory = indentEventHistory;
    }
    @GetMapping(value = "/{indentId}")
    public ResponseEntity<?> findByIndentId(@PathVariable Long indentId)
    {
        List<?> data=indentEventHistory.getAllIndentId(indentId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }
    @GetMapping(value = "/getAll/{indentNumber}")
    public ResponseEntity<?> getAllByIndentNumber(@PathVariable String indentNumber)
    {
        List<?> data=indentEventHistory.getByIndentNumber(indentNumber);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }

}

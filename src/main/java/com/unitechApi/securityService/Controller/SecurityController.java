package com.unitechApi.securityService.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.securityService.Dto.SecuritySave;
import com.unitechApi.securityService.Entity.SecurityModel;
import com.unitechApi.securityService.Service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/unitech/api/v1/security")
@RestController
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping
    public ResponseEntity<?> SaveData(@RequestBody SecurityModel securitySave) {
        SecurityModel securityModel = securityService.saveData(securitySave);
        return new ResponseEntity<>(PageResponse.SuccessResponse(securityModel), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllData() {
        List<SecuritySave> Data = securityService.FindAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetById(@PathVariable Long id) {
        Optional<?> data = securityService.FindById(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping("/statusA/{id}")
    public ResponseEntity<?> acceptStatus(@PathVariable Long id) {
        SecurityModel securityModel = securityService.changeAStatus(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(securityModel), HttpStatus.OK);
    }

    @GetMapping("/statusR/{id}")
    public ResponseEntity<?> RejectStatus(@PathVariable Long id) {
        SecurityModel securityModel = securityService.changeAStatus(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(securityModel), HttpStatus.OK);
    }
}

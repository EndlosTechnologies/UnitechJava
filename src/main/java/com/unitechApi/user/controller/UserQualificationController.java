package com.unitechApi.user.controller;

import com.unitechApi.user.Repository.QualificationRepository;
import com.unitechApi.user.model.QualificationModel;
import com.unitechApi.user.service.UserQualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/qualification")
public class UserQualificationController {

    private final UserQualificationService userQualificationService;

    public UserQualificationController(UserQualificationService userQualificationService) {
        this.userQualificationService = userQualificationService;
    }

    @PostMapping("/create")
    public ResponseEntity<QualificationModel> savequalification(@RequestBody QualificationModel qualificationModel) throws Exception {
        return ResponseEntity.ok(userQualificationService.savequalification(qualificationModel));
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> FindById(@PathVariable Long id) {
        return new ResponseEntity<>(userQualificationService.FindById(id), HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        userQualificationService.DeleteReading(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {

        return new ResponseEntity<>(userQualificationService.UpdateData(id, fields), HttpStatus.OK);
    }

}

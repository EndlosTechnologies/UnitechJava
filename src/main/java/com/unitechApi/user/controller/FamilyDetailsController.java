package com.unitechApi.user.controller;

import com.unitechApi.user.Repository.FamilyDetailsRepository;
import com.unitechApi.user.model.FamilyDetailsModel;
import com.unitechApi.user.service.UserFamilyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/familyDetails")
public class FamilyDetailsController {
    private final UserFamilyDetailsService userFamilyDetailsService;

    public FamilyDetailsController(UserFamilyDetailsService userFamilyDetailsService) {
        this.userFamilyDetailsService = userFamilyDetailsService;
    }

    @PostMapping("/create")
    public ResponseEntity<FamilyDetailsModel> SaveDataFamilyDetails(@RequestBody FamilyDetailsModel familyDetailsModel) {
        return ResponseEntity.ok(userFamilyDetailsService.saveExperienceDetails(familyDetailsModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> FindByDataFamilyDetails(Long id) throws Exception {
        return ResponseEntity.ok(userFamilyDetailsService.FindByFamilyDetails(id));
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        userFamilyDetailsService.DeleteReading(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<FamilyDetailsModel> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        return new ResponseEntity<>(userFamilyDetailsService.UpdateData(id, fields), HttpStatus.OK);
    }
}

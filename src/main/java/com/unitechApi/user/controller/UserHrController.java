package com.unitechApi.user.controller;

import com.unitechApi.user.Repository.HrRepository;
import com.unitechApi.user.model.HrModel;
import com.unitechApi.user.service.UserHrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/hr")
public class UserHrController {

    private final UserHrService userHrService;
    private final HrRepository hrRepository;

    public UserHrController(UserHrService userHrService, HrRepository hrRepository) {
        this.userHrService = userHrService;
        this.hrRepository = hrRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<HrModel> SaveByHrDetails(@RequestBody HrModel hrModel) {
        return ResponseEntity.ok(userHrService.SaveHrDetails(hrModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<HrModel>> FindByHrDetails(Long id) {
        return ResponseEntity.ok(userHrService.findByHrModel(id));
    }
    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        userHrService.DeleteReading(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<HrModel> UpdateData(@PathVariable Long id,@RequestBody Map<Object, Object> fields) {

        return new ResponseEntity<>(userHrService.UpdateData(id,fields), HttpStatus.OK);
    }
}

package com.unitechApi.user.controller;

import com.unitechApi.user.Repository.ExperienceRepository;
import com.unitechApi.user.Repository.UserRepository;
import com.unitechApi.user.model.ExperienceModel;
import com.unitechApi.user.service.UserExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/unitech/api/v1/employeeExperience")
public class ExperienceController {

    private final UserExperienceService userExperienceService;
    private final UserRepository userProfileRepository;
    private final ExperienceRepository experienceRepository;

    public ExperienceController(UserExperienceService userExperienceService, UserRepository userProfileRepository, ExperienceRepository experienceRepository) {
        this.userExperienceService = userExperienceService;
        this.userProfileRepository = userProfileRepository;
        this.experienceRepository = experienceRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<ExperienceModel> saveExperienceDetails(@RequestBody ExperienceModel experienceModel) {
        return ResponseEntity.ok(userExperienceService.SaveUserExperienceDetails(experienceModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ExperienceModel>> FindByDetailsExperience(@PathVariable Long id) {
        return ResponseEntity.ok(userExperienceService.FindByIdExperienceDetails(id));
    }

    @DeleteMapping("/del/{id}")
    public void deleteByid(@PathVariable Long id) {
        userExperienceService.DeleteReading(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ExperienceModel> UpdateData(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {

        return new ResponseEntity<>(userExperienceService.UpdateData(id, fields), HttpStatus.OK);
    }

}

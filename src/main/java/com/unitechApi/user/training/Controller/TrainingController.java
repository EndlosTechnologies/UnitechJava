package com.unitechApi.user.training.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.user.training.Entity.TrainingSession;
import com.unitechApi.user.training.service.ServiceImpl.TrainingServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/unitech/api/v1/training")
public class TrainingController extends TrainingSession{
    private final TrainingServiceImpl trainingServiceImpl;

    public TrainingController(TrainingServiceImpl trainingServiceImpl) {
        this.trainingServiceImpl = trainingServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> saveData(@RequestBody TrainingSession trainingSession) {
        TrainingSession saveData = trainingServiceImpl.saveData(trainingSession);
        return new ResponseEntity<>(PageResponse.SuccessResponse(saveData), HttpStatus.CREATED);
    }

    @GetMapping(value = {"/{traId}"})
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long traId) {
        TrainingSession getData = trainingServiceImpl.getData(traId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(getData), HttpStatus.OK);
    }

    @GetMapping(value = {"/getAll"})
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam Integer page, @RequestParam Integer pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<TrainingSession> getData = trainingServiceImpl.getAll(pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(getData, pagination), HttpStatus.OK);
    }

    @GetMapping(value = "/update/{traId}")
    public ResponseEntity<Map<String, Object>> updateTrainingSession(@PathVariable Long traId, @RequestBody TrainingSession trainingSession) {
        TrainingSession data = trainingServiceImpl.updateTrainingSession(traId, trainingSession);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }
}

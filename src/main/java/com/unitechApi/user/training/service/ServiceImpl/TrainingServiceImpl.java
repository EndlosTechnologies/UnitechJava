package com.unitechApi.user.training.service.ServiceImpl;

import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.user.training.Entity.TrainingSession;
import com.unitechApi.user.training.Repository.TrainingRepository;
import com.unitechApi.user.training.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrainingServiceImpl implements TrainingService {
    public static final Logger log = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }


    /**
     * @param trainingSession
     * @return All Training Data
     */
    @Override
    public TrainingSession saveData(TrainingSession trainingSession) {
        trainingSession.setEndDate();
        return trainingRepository.save(trainingSession);
    }

    /**
     * @param trainingId trainingId
     * @return get A trainingSession by trainingId
     */
    @Override
    public TrainingSession getById(Long trainingId) {
        return trainingRepository.getById(trainingId);
    }

    /**
     * @return getAll trainingSession
     */
    @Override
    public Page<TrainingSession> getAll(Pagination pagination) {
        return trainingRepository.findAll(pagination.getpageble());

    }

    /**
     * @param trainingId trainingId
     * @param trainingSession trainingSession
     * @return update trainingSession data
     */
    @Override
    public TrainingSession updateTrainingSession(Long trainingId, TrainingSession trainingSession) {
        TrainingSession tr = trainingRepository.getById(trainingId);
        if (tr.getTrainingId() != null) {
                log.info("tr {}",tr);
        }
        return trainingRepository.save(trainingSession);
    }

    public TrainingSession getData(Long traId) {
        TrainingSession trainingSession = trainingRepository.getById(traId);
        LocalDateTime check = LocalDateTime.now();
        log.info("check update");
        if (trainingSession.getStartDate().isBefore(check)) {
            log.info("if in");

        } else if (trainingSession.getEndDate().isAfter(check)) {
            log.info("else if in");
        }

        return trainingSession;
    }
}

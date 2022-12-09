package com.unitechApi.user.training.service.ServiceImpl;

import com.unitechApi.user.training.Entity.SpeedFrameTrainingEntity;
import com.unitechApi.user.training.Repository.SpeedFrameTrainingRepository;
import com.unitechApi.user.training.service.SpeedFrameService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpeedFrameServiceImpl implements SpeedFrameService {
    private final SpeedFrameTrainingRepository speedFrameTrainingRepository;

    public SpeedFrameServiceImpl(SpeedFrameTrainingRepository speedFrameTrainingRepository) {
        this.speedFrameTrainingRepository = speedFrameTrainingRepository;
    }

    /**
     * @param speedFrameTraining speedFrameTraining
     * @return save speedFrameTraining data
     */
    @Override
    public SpeedFrameTrainingEntity saveData(SpeedFrameTrainingEntity speedFrameTraining) {
        return speedFrameTrainingRepository.save(speedFrameTraining);
    }

    /**
     * @param s_t_id s_t_id
     * @return get speedFrameTraining data By id
     */
    @Override
    public SpeedFrameTrainingEntity getById(Long s_t_id) {
        return speedFrameTrainingRepository.getById(s_t_id);
    }

    /**
     * @return get All {@link SpeedFrameTrainingRepository} Data
     */
    @Override
    public List<SpeedFrameTrainingEntity> getAll() {
        return speedFrameTrainingRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(SpeedFrameTrainingEntity::getS_t_id))
                .collect(Collectors.toList());
    }
}

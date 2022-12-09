package com.unitechApi.user.training.service.ServiceImpl;

import com.unitechApi.user.training.Entity.RingFrameTraining;
import com.unitechApi.user.training.Repository.RingFrameTrainingRepository;
import com.unitechApi.user.training.service.RingFrameService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RingFrameServiceImpl implements RingFrameService {
    private final RingFrameTrainingRepository ringFrameTrainingRepository;

    public RingFrameServiceImpl(RingFrameTrainingRepository ringFrameTrainingRepository) {
        this.ringFrameTrainingRepository = ringFrameTrainingRepository;
    }

    /**
     * @param ringFrameTraining ringFrameTraining
     * @return save ringframeTrainigData save
     */
    @Override
    public RingFrameTraining saveData(RingFrameTraining ringFrameTraining) {
        return ringFrameTrainingRepository.save(ringFrameTraining);
    }

    /**
     * @param r_t_id r_t_id
     * @return get All traini data By id
     */
    @Override
    public RingFrameTraining getById(Long r_t_id) {
        return ringFrameTrainingRepository.getById(r_t_id);
    }

    /**
     * @return getAll
     */
    @Override
    public List<RingFrameTraining> getAll() {
        return ringFrameTrainingRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(RingFrameTraining::getR_t_id))
                .collect(Collectors.toList());
    }
}

package com.unitechApi.user.training.service.ServiceImpl;

import com.unitechApi.user.training.Entity.DrawFramesTraining;
import com.unitechApi.user.training.Repository.DrawFramesTrainingRepository;
import com.unitechApi.user.training.service.DrawFramesService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrawFramesServiceImpl implements DrawFramesService {
    private final DrawFramesTrainingRepository drawFramesTrainingRepository;

    public DrawFramesServiceImpl(DrawFramesTrainingRepository drawFramesTrainingRepository) {
        this.drawFramesTrainingRepository = drawFramesTrainingRepository;
    }

    /**
     * @param drawFramesTraining drawFramesTraining
     * @return save drawFramesTraining data
     */
    @Override
    public DrawFramesTraining saveData(DrawFramesTraining drawFramesTraining) {
        return drawFramesTrainingRepository.save(drawFramesTraining);
    }

    /**
     * @param d_t_id d_t_id
     * @return get drawFramesTraining by Id
     */
    @Override
    public DrawFramesTraining getById(Long d_t_id) {
        return drawFramesTrainingRepository.getById(d_t_id);
    }

    /**
     * @return get All drawFramesTraining data
     */
    @Override
    public List<DrawFramesTraining> getAll() {
        return drawFramesTrainingRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(DrawFramesTraining::getD_t_id))
                .collect(Collectors.toList());
    }
}

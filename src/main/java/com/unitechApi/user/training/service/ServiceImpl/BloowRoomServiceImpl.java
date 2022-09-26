package com.unitechApi.user.training.service.ServiceImpl;

import com.unitechApi.user.training.Entity.BloowRoomTraining;
import com.unitechApi.user.training.Repository.BloowRoomTrainingRepository;
import com.unitechApi.user.training.service.BloowRoomService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloowRoomServiceImpl implements BloowRoomService {
    private final BloowRoomTrainingRepository bloowRoomTrainingRepository;

    public BloowRoomServiceImpl(BloowRoomTrainingRepository bloowRoomTrainingRepository) {
        this.bloowRoomTrainingRepository = bloowRoomTrainingRepository;
    }

    /**
     * @param bloowRoomTraining 
     * @return bloowRoom Data Save
     */
    @Override
    public BloowRoomTraining save(BloowRoomTraining bloowRoomTraining) {
        return bloowRoomTrainingRepository.save(bloowRoomTraining);
    }

    /**
     * @param b_t_id 
     * @return
     */
    @Override
    public BloowRoomTraining getById(Long b_t_id) {
        return bloowRoomTrainingRepository.getById(b_t_id);
    }

    /**
     * @return 
     */
    @Override
    public List<BloowRoomTraining> getAll() {
        return bloowRoomTrainingRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(BloowRoomTraining::getB_t_id))
                .collect(Collectors.toList());
    }
}

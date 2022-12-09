package com.unitechApi.user.training.service.ServiceImpl;

import com.unitechApi.user.training.Entity.CardingTraining;
import com.unitechApi.user.training.Repository.CardingTrainingRepository;
import com.unitechApi.user.training.service.CardingService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardingServiceImpl implements CardingService {
    private final CardingTrainingRepository cardingTrainingRepository;

    public CardingServiceImpl(CardingTrainingRepository cardingTrainingRepository) {
        this.cardingTrainingRepository = cardingTrainingRepository;
    }

    /**
     * @param cardingTraining cardingTraining
     * @return save cardingTraining
     */
    @Override
    public CardingTraining saveData(CardingTraining cardingTraining) {
        return cardingTrainingRepository.save(cardingTraining);
    }

    /**
     * @param c_t_id c_t_id
     * @return get cardingTraining by c_t_id
     */
    @Override
    public CardingTraining getById(Long c_t_id) {
        return cardingTrainingRepository.getById(c_t_id);
    }

    /**
     * @return  getAll cardingTraining
     */
    @Override
    public List<CardingTraining> getAll() {
        return cardingTrainingRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(CardingTraining::getCarding_id))
                .collect(Collectors.toList());
    }
}

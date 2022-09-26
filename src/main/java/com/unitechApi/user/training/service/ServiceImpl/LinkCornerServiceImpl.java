package com.unitechApi.user.training.service.ServiceImpl;

import com.unitechApi.user.training.Entity.LinkCornerTraining;
import com.unitechApi.user.training.Repository.LinkCornerTrainingRepository;
import com.unitechApi.user.training.service.LinkCornerService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkCornerServiceImpl implements LinkCornerService {
    private final LinkCornerTrainingRepository linkCornerTrainingRepository;

    public LinkCornerServiceImpl(LinkCornerTrainingRepository linkCornerTrainingRepository) {
        this.linkCornerTrainingRepository = linkCornerTrainingRepository;
    }

    /**
     * @param linkCornerTraining
     * @return
     */
    @Override
    public LinkCornerTraining saveData(LinkCornerTraining linkCornerTraining) {
        return linkCornerTrainingRepository.save(linkCornerTraining);
    }

    /**
     * @param l_t_id
     * @return
     */
    @Override
    public LinkCornerTraining getById(Long l_t_id) {
        return linkCornerTrainingRepository.getById(l_t_id);
    }

    /**
     * @return
     */
    @Override
    public List<LinkCornerTraining> getAll() {
        return linkCornerTrainingRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(LinkCornerTraining::getL_t_id))
                .collect(Collectors.toList());
    }
}

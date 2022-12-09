package com.unitechApi.user.service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import com.unitechApi.user.Repository.ExperienceRepository;
import com.unitechApi.user.model.ExperienceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class UserExperienceService {
    private final ExperienceRepository experienceRepository;

    public UserExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    /*
    * save experience and user object pass In requestBody
    *
    * */
    public ExperienceModel SaveUserExperienceDetails(ExperienceModel experienceModel) {
        return experienceRepository.save(experienceModel);
    }
    /*
    * params id
    * delete experince By Id
    *     * */
    public void DeleteReading(Long id) {
        try {
            experienceRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }
    /*
    * params id
    * return Experience by ID
    * */
    public Optional<ExperienceModel> FindByIdExperienceDetails(Long id) {
        return Optional.ofNullable(experienceRepository.findById(id).orElseThrow(() -> new UserNotFound("Sorry !  i cann't Do This cause your Data Not Found")));
    }
    /*
    * params ID
    * params field
    * return update  experienceData by ID
    * */
    public ExperienceModel UpdateData(Long Id, Map<Object, Object> fields) {
        ExperienceModel experienceModel = experienceRepository.findById(Id).orElseThrow(() -> new ResourceNotFound("Sorry !  i cann't Do This cause your Data Not Found"));
        fields.forEach((key, values) -> {
            Field field = ReflectionUtils.findField(ExperienceModel.class, String.valueOf(key));
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, experienceModel, values);
        });
        ExperienceModel q = experienceRepository.save(experienceModel);
        return experienceModel;
    }
}

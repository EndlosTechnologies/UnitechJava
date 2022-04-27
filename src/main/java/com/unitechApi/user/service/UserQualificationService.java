package com.unitechApi.user.service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.user.Repository.QualificationRepository;
import com.unitechApi.user.model.QualificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class UserQualificationService {
    private final QualificationRepository qualificationRepository;

    public UserQualificationService(QualificationRepository qualificationRepository) {
        this.qualificationRepository = qualificationRepository;
    }

    public QualificationModel savequalification(QualificationModel qualificationModel) throws Exception {
        QualificationModel qualificationModel1 = qualificationRepository.save(qualificationModel);
        if (qualificationModel == null) {
            throw new Exception("Qualification Details are Empty");
        }
        return qualificationModel;
    }

    public void DeleteReading(Long id) {
        try {
            qualificationRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present ");
        }
    }

    public QualificationModel FindById(Long id) {
        QualificationModel qualificationModel = qualificationRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Sorry !  i cann't Do This cause your Data Not Found"));
        return qualificationModel;
    }

    public QualificationModel UpdateData(Long Id, Map<Object, Object> fields) {
        QualificationModel qualificationModel = qualificationRepository.findById(Id).orElseThrow(() -> new ResourceNotFound("Sorry !  i cann't Do This cause your Data Not Found"));

        fields.forEach((key, values) -> {
            Field field = ReflectionUtils.findField(QualificationModel.class, String.valueOf(key));
            field.setAccessible(true);
            ReflectionUtils.setField(field, qualificationModel, values);
        });
        QualificationModel q = qualificationRepository.save(qualificationModel);
        return qualificationModel;
    }
}


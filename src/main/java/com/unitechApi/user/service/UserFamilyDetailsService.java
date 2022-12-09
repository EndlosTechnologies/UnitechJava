package com.unitechApi.user.service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.user.Repository.FamilyDetailsRepository;
import com.unitechApi.user.model.FamilyDetailsModel;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class UserFamilyDetailsService {
    private final FamilyDetailsRepository familyDetailsRepository;

    public UserFamilyDetailsService(FamilyDetailsRepository familyDetailsRepository) {
        this.familyDetailsRepository = familyDetailsRepository;
    }

    /*
     * save familyDetails and user object pass In requestBody
     *
     * */
    public FamilyDetailsModel saveExperienceDetails(FamilyDetailsModel familyDetailsModel) {
        return familyDetailsRepository.save(familyDetailsModel);
    }

    /*
     * params id
     * delete familyDetails By Id
     *
     * check parent Object
     *     * */
    public void DeleteReading(Long id) {
        try {
            familyDetailsRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);

        }

    }

    /*
     * params id
     * return family Details by ID
     * */
    public Optional<FamilyDetailsModel> FindByFamilyDetails(Long id) throws Exception {
        return Optional.ofNullable(familyDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Sorry !  i cann't Do This cause your Data Not Found")));
    }

    /*
     * params ID
     * params field
     * return update  Family Details Data by ID
     * */
    public FamilyDetailsModel UpdateData(Long Id, Map<Object, Object> fields) {
        FamilyDetailsModel familyDetailsModel = familyDetailsRepository.findById(Id).orElseThrow(() -> new ResourceNotFound("Sorry !  i cann't Do This cause your Data Not Found"));
        fields.forEach((key, values) -> {
            Field field = ReflectionUtils.findField(FamilyDetailsModel.class, String.valueOf(key));
            field.setAccessible(true);
            ReflectionUtils.setField(field, familyDetailsModel, values);
        });
        FamilyDetailsModel q = familyDetailsRepository.save(familyDetailsModel);

        return familyDetailsModel;
    }
}

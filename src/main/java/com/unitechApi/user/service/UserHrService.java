package com.unitechApi.user.service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.user.Repository.HrRepository;
import com.unitechApi.user.model.HrModel;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class UserHrService {
    private final HrRepository hrRepository;

    public UserHrService(HrRepository hrRepository) {
        this.hrRepository = hrRepository;
    }

    /*
     * save Hr details
     *
     * */
    public HrModel SaveHrDetails(HrModel hrModel) {
        return hrRepository.save(hrModel);
    }

    /*
     * params id
     * return hr Detials by ID
     * */
    public Optional<HrModel> findByHrModel(Long id) {
        return Optional.ofNullable(hrRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Sorry !  i cann't Do This cause your Data Not Found")));
    }

    /*
     * params id
     * delete Hr Details By Id
     *
     * check parent Object
     *     * */
    public void DeleteReading(Long id) {
        try {
            hrRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + ResourceNotFound.class);
        }
    }

    /*
     * params ID
     * params field
     * return update  Hr Putted  by ID
     * */
    public HrModel UpdateData(Long Id, Map<Object, Object> fields) {
        HrModel hrModel = hrRepository.findById(Id).orElseThrow(() -> new ResourceNotFound("Sorry !  i cann't Do This cause your Data Not Found"));
        fields.forEach((key, values) -> {
            Field field = ReflectionUtils.findField(HrModel.class, String.valueOf(key));
            field.setAccessible(true);
            ReflectionUtils.setField(field, hrModel, values);
        });
        HrModel q = hrRepository.save(hrModel);
        return hrModel;
    }
}

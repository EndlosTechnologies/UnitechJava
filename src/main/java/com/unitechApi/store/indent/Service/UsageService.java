package com.unitechApi.store.indent.Service;

import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.store.indent.Model.UsageItem;
import com.unitechApi.store.indent.Repository.UsageRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

@Service
public class UsageService {
    private final UsageRepository usageRepository;

    public UsageService(UsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    public void save(UsageItem usageItem) {
        this.usageRepository.save(usageItem);
    }

    public BaseStream<UsageItem, Stream<UsageItem>> FindAll() {
        return usageRepository.findAll().stream().sorted(Comparator.comparing(UsageItem::getuId));
    }
    
    public List<UsageItem> findByDepName(String name) {
        return usageRepository.findByDeptName(name);
    }



    public Page<UsageItem> findByCardingDataAndDeptName(String deptname, Long id, Date start, Date end, Pagination pagination) {
        if (deptname.equalsIgnoreCase("Bloowrom")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndBloowusageIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("Carding")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndCardingusageIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("comber")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndComberusageIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("drawframes")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndDrawFramesMachineIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("finisher")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndFinisherMachinedataIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("lapformer")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndLapFormerusageIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("ringframe")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndRingframeMachineusageIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("packing")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndPackingMachineusageIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("simplex")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndSimplexMachineusageIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("utillity")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndUtilliyMachineusageIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("waste")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndWasteMachineusageIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("winding")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedBetween(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndWindingMachineusageIdAndCreatedBetween(deptname, id, start, end, pagination.getpageble());
            }
        }

        return null;
    }

}

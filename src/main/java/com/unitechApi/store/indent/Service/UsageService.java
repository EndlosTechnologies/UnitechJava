package com.unitechApi.store.indent.Service;

import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.store.indent.Model.UsageItem;
import com.unitechApi.store.indent.Repository.UsageRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
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

    public List<UsageItem> FindAll() {
        return usageRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(UsageItem::getuId))
                .collect(Collectors.toList());
    }
    public List<UsageItem> allSaveData(List<UsageItem> data)
    {
        return usageRepository.saveAll(data);
    }

    public List<UsageItem> findByDepName(String name) {
        return usageRepository.findByDeptName(name);
    }

    public List<UsageItem> ExcelReportDate(String deptname, Long id, Date start, Date end) {
        if (deptname.equalsIgnoreCase("bloowroom")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndBloowusage(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("carding")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndCardingusage(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("comber")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndComberusage(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("drawframe")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndDrawFramesMachine(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("finisher")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndFinisherMachinedata(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("lapformer")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndLapFormerusage(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("ringframe")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndRingframeMachineusage(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("packing")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndPackingMachineusage(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("speedframe")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndSimplexMachineusage(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("utility")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndUtilliyMachineusage(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("wasteroom")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndWasteMachineusage(deptname, id, start, end);
            }
        } else if (deptname.equalsIgnoreCase("winding")) {
            if (id == null) {
                return usageRepository.findByDepartment(deptname, start, end);
            } else {
                return usageRepository.findByDeptNameAndWindingMachineusage(deptname, id, start, end);
            }
        }
        return null;
    }

    public Page<UsageItem> findByCardingDataAndDeptName(String deptname, Long id, Date start, Date end, Pagination pagination) {
        if (deptname.equalsIgnoreCase("bloowroom")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndBloowusageIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("carding")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndCardingusageIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("comber")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndComberusageIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("drawframe")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndDrawFramesMachineIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("finisher")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndFinisherMachinedataIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("lapformer")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndLapFormerusageIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("ringframe")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndRingframeMachineusageIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("packing")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndPackingMachineusageIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("speedframe")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndSimplexMachineusageIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("utility")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndUtilliyMachineusageIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("wasteroom")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndWasteMachineusageIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        } else if (deptname.equalsIgnoreCase("winding")) {
            if (id == null) {
                return usageRepository.findByDeptNameAndCreatedDate(deptname, start, end, pagination.getpageble());
            } else {
                return usageRepository.findByDeptNameAndWindingMachineusageIdAndCreated(deptname, id, start, end, pagination.getpageble());
            }
        }

        return null;
    }

}



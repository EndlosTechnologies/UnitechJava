package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.UsageItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UsageRepository extends JpaRepository<UsageItem, Long> {
    List<UsageItem> findByDeptName(String name);

    Page<UsageItem> findByDeptNameAndCreatedBetween(String deptname, Date start, Date end, Pageable pageable);

    Page<UsageItem> findByDeptNameAndBloowusageIdAndCreatedBetween(String deptname, Long id, Date start, Date end, Pageable pageable);

    Page<UsageItem> findByDeptNameAndCardingusageIdAndCreatedBetween(String deptname, Long id, Date start, Date end, Pageable pageable);

    Page<UsageItem> findByDeptNameAndComberusageIdAndCreatedBetween(String deptname, Long id, Date start, Date end,Pageable pageable);

    Page<UsageItem> findByDeptNameAndDrawFramesMachineIdAndCreatedBetween(String deptname, Long id, Date start, Date end ,Pageable pageable);

    Page<UsageItem> findByDeptNameAndFinisherMachinedataIdAndCreatedBetween(String deptname, Long id, Date start, Date end, Pageable pageable);

    Page<UsageItem> findByDeptNameAndLapFormerusageIdAndCreatedBetween(String deptname, Long id, Date start, Date end, Pageable pageable);

    Page<UsageItem> findByDeptNameAndPackingMachineusageIdAndCreatedBetween(String deptname, Long id, Date start, Date end, Pageable pageable);

    Page<UsageItem> findByDeptNameAndRingframeMachineusageIdAndCreatedBetween(String deptname, Long id, Date start, Date end, Pageable pageable);

    Page<UsageItem> findByDeptNameAndSimplexMachineusageIdAndCreatedBetween(String deptname, Long id, Date start, Date end, Pageable pageable);

    Page<UsageItem> findByDeptNameAndUtilliyMachineusageIdAndCreatedBetween(String deptname, Long id, Date start, Date end, Pageable pageable);

    Page<UsageItem> findByDeptNameAndWasteMachineusageIdAndCreatedBetween(String deptname, Long id, Date start, Date end, Pageable pageable);

    Page<UsageItem> findByDeptNameAndWindingMachineusageIdAndCreatedBetween(String deptname, Long id, Date start, Date end, Pageable pageable);


}

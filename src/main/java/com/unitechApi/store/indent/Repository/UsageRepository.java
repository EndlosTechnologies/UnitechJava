package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.UsageItem;
import org.apache.xmlbeans.impl.jam.JPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UsageRepository extends JpaRepository<UsageItem,Long> {
    List<UsageItem> findByDeptName(String name);
    List<UsageItem> findByDeptNameAndCreatedBetweenAndBloowusageIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);
    List<UsageItem> findByDeptNameAndCreatedBetweenAndCardingusageIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);
    List<UsageItem> findByDeptNameAndCreatedBetweenAndComberusageIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);

    List<UsageItem> findByDeptNameAndCreatedBetweenAndDrawFramesMachineIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);

    List<UsageItem> findByDeptNameAndCreatedBetweenAndFinisherMachinedataIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);

    List<UsageItem> findByDeptNameAndCreatedBetweenAndLapFormerusageIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);

    List<UsageItem> findByDeptNameAndCreatedBetweenAndPackingMachineusageIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);

    List<UsageItem> findByDeptNameAndCreatedBetweenAndRingframeMachineusageIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);

    List<UsageItem> findByDeptNameAndCreatedBetweenAndSimplexMachineusageIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);

    List<UsageItem> findByDeptNameAndCreatedBetweenAndUtilliyMachineusageIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);

    List<UsageItem> findByDeptNameAndCreatedBetweenAndWasteMachineusageIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);

    List<UsageItem> findByDeptNameAndCreatedBetweenAndWindingMachineusageIdAndIssuedItemIssueId(String deptname, Date start, Date end,long id,long issueId);


}

package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.UsageItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UsageRepository extends JpaRepository<UsageItem, Long> {
    List<UsageItem> findByDeptName(String name);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname  and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndCreatedDate(String deptname, Date start, Date end, Pageable pageable);

    // @Query("select c from IssueItem c where DATE(c.issueDate) between :start and :end and c.storeItemModel.itemId = :itemId")
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.bloowusage.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndBloowusageIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.cardingusage.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndCardingusageIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.comberusage.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndComberusageIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.drawFramesMachine.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndDrawFramesMachineIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.finisherMachinedata.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndFinisherMachinedataIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.lapFormerusage.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndLapFormerusageIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.packingMachineusage.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndPackingMachineusageIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.ringframeMachineusage.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndRingframeMachineusageIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.simplexMachineusage.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndSimplexMachineusageIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.utilliyMachineusage.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndUtilliyMachineusageIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.wasteMachineusage.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndWasteMachineusageIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.windingMachineusage.id =:id and   DATE(u.created) between :start and :end")
    Page<UsageItem> findByDeptNameAndWindingMachineusageIdAndCreated(String deptname, Long id, Date start, Date end, Pageable pageable);


    // for Excel Generate Method in Department and And Date
    @Query("select u  from  UsageItem  u where u.deptName =:deptname  and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDepartment(String deptname, Date start, Date end);

    // @Query("select c from IssueItem c where DATE(c.issueDate) between :start and :end and c.storeItemModel.itemId = :itemId")
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.bloowusage.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndBloowusage(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.cardingusage.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndCardingusage(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.comberusage.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndComberusage(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.drawFramesMachine.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndDrawFramesMachine(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.finisherMachinedata.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndFinisherMachinedata(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.lapFormerusage.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndLapFormerusage(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.packingMachineusage.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndPackingMachineusage(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.ringframeMachineusage.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndRingframeMachineusage(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.simplexMachineusage.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndSimplexMachineusage(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.utilliyMachineusage.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndUtilliyMachineusage(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.wasteMachineusage.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndWasteMachineusage(String deptname, Long id, Date start, Date end);
    @Query("select u  from  UsageItem  u where u.deptName =:deptname and u.windingMachineusage.id =:id and   DATE(u.created) between :start and :end")
    List<UsageItem> findByDeptNameAndWindingMachineusage(String deptname, Long id, Date start, Date end);

}

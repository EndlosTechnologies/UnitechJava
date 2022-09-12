package com.unitechApi.store.issue.Repository;

import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.issue.model.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<IssueItem,Long> , JpaSpecificationExecutor<IssueItem> {
    List<IssueItem> findByStatus(IssueStatus status);
    @Query("select c from IssueItem c where DATE(c.issueDate) =?1")
    List<IssueItem> findByIssueDate(Date date);
    List<IssueItem> findByisRaised(boolean data);
    @Query("select c from IssueItem c where DATE(c.issueDate) between :start and :end and c.storeItemModel.itemId = :itemId")
    List<IssueItem> findByIssueDateBetween(Date start,Date end,Long itemId);
    //List<IssueItem> findByisRaisedFalse(boolean data);
    Optional<IssueItem>  findByCloseresid(Long id);
//    void findByIssueIdAndStoreItemModelItemId
}

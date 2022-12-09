package com.unitechApi.store.issue.Service;

import com.unitechApi.common.query.SearchRequest;
import com.unitechApi.common.query.SearchSpecification;
import com.unitechApi.exception.ExceptionService.OutOfStock;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.store.issue.Repository.IssueRepository;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.issue.model.IssueStatus;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.service.StoreItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final StoreItemService storeItemService;
    public static final Logger log = LoggerFactory.getLogger(IssueService.class);

    public IssueService(IssueRepository issueRepository, StoreItemService storeItemService) {
        this.issueRepository = issueRepository;
        this.storeItemService = storeItemService;
    }

    public IssueItem saveData(IssueItem issueItem) {
        return issueRepository.save(issueItem);
    }

    public IssueItem findByIdIssue(@PathVariable Long id) {
        return issueRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Issue Not Found"));
    }

    public List<IssueItem> findAll() {
        return issueRepository.findAll().stream().sorted(Comparator.comparing(IssueItem::getIssueId).reversed()).collect(Collectors.toList());
    }

    public IssueItem deleteIssueId(Long id) {
        Optional<?> dta = Optional.ofNullable(issueRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Issue Not Found")));
        if (dta.isPresent()) {
            issueRepository.deleteById(id);
        }
        return null;
    }

    /*
     *   params issueId
     *   params dta
     *   by default issue are pending
     *   change status  in issue
     * */

    public Object changeStatus(long issueId, IssueItem dta) {

        IssueItem itemRequest = findByIdIssue(issueId);
        StoreItemModel storeItemModel = storeItemService.findById(itemRequest.getStoreItemModel().getItemId());

        if (itemRequest.getStatus().equals(IssueStatus.PENDING)) {
            itemRequest.setStatus(dta.getStatus());
            if (itemRequest.getStatus().equals(IssueStatus.OPEN)) {
                if (itemRequest.getQuantity() >= dta.getQuantity()) {
                    storeItemModel.setQuantity(storeItemModel.getQuantity() - dta.getQuantity());
                    log.info("store item {}", storeItemModel);
                    itemRequest.setQuantity(dta.getQuantity());
                } else {
                    throw new OutOfStock("can't get any Resource");
                }
            }
//            log.info("store item {}", storeItemModel.getQuantity());
//            issueRepository.save(itemRequest);
        } else {
            itemRequest.setStatus(dta.getStatus());
        }
        log.info("store item {}", storeItemModel.getQuantity());
        issueRepository.save(itemRequest);
        return null;
    }

    /*
     * params status
     * get All Issue by IssueStatus
     * */
    public List<IssueItem> FindByStatus(IssueStatus status) {

        return issueRepository.findByStatus(status).stream().sorted(Comparator.comparing(IssueItem::getIssueId).reversed()).collect(Collectors.toList());
    }
    /*
     * params dateTime
     * get All Issue By Date
     * */

    public List<IssueItem> findByIssueData(Date dateTime) {
        return issueRepository.findByIssueDate(dateTime);
    }


    public List<IssueItem> FindByRaisedtrue(Boolean data) {
        return issueRepository.findByisRaised(data);
    }
    /*
     * params start
     * params end
     * params itemId
     * get All Issue By between two Dates and itemId
     * */

    public List<IssueItem> FindByIssueDateBetween(Date start, Date end, Long itemId) {
        return issueRepository.findByIssueDateBetween(start, end, itemId);
    }

    public Optional<IssueItem> FindByCloseId(Long id) {
        return Optional.ofNullable(issueRepository.findByCloseresid(id).orElseThrow(() -> new ResourceNotFound("Not found Aby Type Of Data")));
    }

    public Page<IssueItem> getSearchingInIssueItem(SearchRequest searchRequest) {
        SearchSpecification<IssueItem> data = new SearchSpecification<>(searchRequest);
        Pageable pageable = SearchSpecification.getPageable(searchRequest.getPage(), searchRequest.getSize());
        return issueRepository.findAll(data, pageable);
    }
}


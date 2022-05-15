package com.unitechApi.store.indent.Service;

import com.unitechApi.store.indent.Model.UsageItem;
import com.unitechApi.store.indent.Repository.UsageRepository;
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

    public BaseStream<UsageItem, Stream<UsageItem>> FindAll()
    {
        return usageRepository.findAll().stream().sorted(Comparator.comparing(UsageItem::getuId));
    }
    public List<UsageItem> findByDepName(String name) {
        return usageRepository.findByDeptName(name);
    }

    public List<UsageItem> findByBloowDataAndDeptName(String deptname, Date start, Date end,long id,long issueId)
    {
        UsageItem re=new UsageItem();
        if (deptname.equals(re.getDeptName())) {
            usageRepository.findByDeptNameAndCreatedBetweenAndBloowusageIdAndIssuedItemIssueId(deptname, start, end, id, issueId);
        } else if (deptname.equals("Carding")) {
            return usageRepository.findByDeptNameAndCreatedBetweenAndCardingusageIdAndIssuedItemIssueId(deptname ,start,end,id,issueId);

        }
        return null;
    }
    public List<UsageItem> findByCardingDataAndDeptName(String deptname, Date start, Date end,long id,long issueId)
    {
        return usageRepository.findByDeptNameAndCreatedBetweenAndCardingusageIdAndIssuedItemIssueId(deptname ,start,end,id,issueId);
    }

}

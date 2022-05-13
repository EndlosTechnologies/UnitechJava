package com.unitechApi.store.indent.Service;

import com.unitechApi.store.indent.Model.UsageItem;
import com.unitechApi.store.indent.Repository.UsageRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UsageService {
    private final UsageRepository usageRepository;
    public UsageService(UsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    public void save(UsageItem usageItem)
    {
        this.usageRepository.save(usageItem);
    }
    public List<UsageItem> findByDepName(String  name)
    {
        return  usageRepository.findByDeptName(name);
    }
}

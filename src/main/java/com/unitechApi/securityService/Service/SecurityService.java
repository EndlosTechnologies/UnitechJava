package com.unitechApi.securityService.Service;

import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.securityService.Dto.SecuritySave;
import com.unitechApi.securityService.Entity.Cstatus;
import com.unitechApi.securityService.Entity.SecurityModel;
import com.unitechApi.securityService.Repository.SecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SecurityService {

    private final SecurityRepository securityRepository;
    public SecurityService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public SecurityModel saveData(SecurityModel securityModel) {
        log.info(" {} Save Data", securityModel);
        return securityRepository.save(securityModel);
    }

    public Optional<SecuritySave> FindById(Long id) {
        Optional<SecuritySave> FindById = Optional.ofNullable(securityRepository.findById(id).map(this::FinById).orElseThrow(() -> new RuntimeException()));
        return FindById;
    }

    public List<SecuritySave> FindAll() {
        log.info(" {}");
        return securityRepository
                .findAll()
                .stream()
                .map(this::FindData).collect(Collectors.toList());
    }

    public SecurityModel changeAStatus(Long id) {
        SecurityModel securityModel = securityRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
        securityModel.setCstatus(Cstatus.ACCEPT);
        return securityRepository.save(securityModel);
    }

    public SecurityModel changeRStatus(Long id) {
        SecurityModel securityModel = securityRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
        securityModel.setCstatus(Cstatus.REJECT);
        return securityRepository.save(securityModel);
    }

    private SecuritySave FindData(SecurityModel securityModel) {
        SecuritySave securitySave = new SecuritySave();
        securitySave.setId(securityModel.getId());
        securitySave.setName(securityModel.getName());
        securitySave.setMobileNo(securityModel.getMobileNo());
        securitySave.setMeetName(securityModel.getMeetName());
        securitySave.setPurpose(securityModel.getPurpose());
        securitySave.setInTime(securityModel.getInTime());
        securitySave.setOutTime(securityModel.getOutTime());
        securitySave.setEntryDate(securityModel.getEntryDate());
        return securitySave;
    }

    private SecuritySave FinById(SecurityModel securityModel) {
        SecuritySave securitySave = new SecuritySave();
        securitySave.setId(securityModel.getId());
        securitySave.setName(securityModel.getName());
        securitySave.setMobileNo(securityModel.getMobileNo());
        securitySave.setMeetName(securityModel.getMeetName());
        securitySave.setPurpose(securityModel.getPurpose());
        securitySave.setInTime(securityModel.getInTime());
        securitySave.setOutTime(securityModel.getOutTime());
        securitySave.setEntryDate(securityModel.getEntryDate());
        return securitySave;
    }


}

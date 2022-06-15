package com.unitechApi.purchase.RawMaterial.vendor.Service;

import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import com.unitechApi.purchase.RawMaterial.Contract.Repository.ContractRepository;
import com.unitechApi.purchase.RawMaterial.vendor.Repository.VendorRepository;
import com.unitechApi.purchase.RawMaterial.vendor.model.VendorModel;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final ContractRepository contractRepository;

    public VendorService(VendorRepository vendorRepository, ContractRepository contractRepository) {
        this.vendorRepository = vendorRepository;
        this.contractRepository = contractRepository;
    }
    public VendorModel SaveData(VendorModel vendorModel) {
        return vendorRepository.save(vendorModel);
    }

    public VendorModel FindById(Long id) {
        return vendorRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Sorry ! Vendor Not Found in System"));
    }

    public Object FindAll() {
        return vendorRepository.findAll();
    }

    public Optional<VendorModel> DeleteData(Long id) {
        Optional<VendorModel> vendorModel = vendorRepository.findById(id);
        if (!vendorModel.isPresent()) {
            throw new UserNotFound("Sorry ! User Not Found in System");
        } else
            vendorRepository.deleteById(id);
        return Optional.empty();
    }

    public List<VendorModel> FindDateByData(Date start, Date end) {
        java.util.Date date = new java.util.Date();
        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return vendorRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(VendorModel::getId))
                .collect(Collectors.toList());
    }

    public List<VendorModel> FindByParticularDate(Date pdate) {
        java.util.Date date = new java.util.Date();
        if (date.before(pdate)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + pdate);
        }
        return vendorRepository.findByCreatedAt(pdate)
                .stream()
                .sorted(Comparator.comparing(VendorModel::getId))
                .collect(Collectors.toList());
    }
    public Object DeleteItem(Long id)
    {
        VendorModel vendorModel=vendorRepository.findById(id).get();
        vendorModel.getItemData().remove(id);
        return null;
    }
}

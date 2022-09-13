package com.unitechApi.store.vendor.Service;

import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.common.query.SearchRequest;
import com.unitechApi.common.query.SearchSpecification;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import com.unitechApi.store.vendor.Dto.VendorDto;
import com.unitechApi.store.vendor.Repository.VendorRepository;
import com.unitechApi.store.vendor.model.VendorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final StoreItemRepository storeItemRepository;

    public VendorService(VendorRepository vendorRepository, StoreItemRepository storeItemRepository) {
        this.vendorRepository = vendorRepository;
        this.storeItemRepository = storeItemRepository;
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
        }
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

    public Object DeleteItem(Long vendorId, Long itemId) {
        VendorModel vendorModel = vendorRepository.findById(vendorId).get();
        StoreItemModel storeItemModel = storeItemRepository.findById(itemId).get();
        vendorModel.deleteItem(storeItemModel);
        return vendorRepository.save(vendorModel);
    }

    public Page<VendorDto> findAllGetByDto(Pagination pagination) {
        return vendorRepository.findAll(pagination.getpageble())
                .map(this::getByDto);

    }

//    public List<VendorDto> getById(Long id) {
//     return    vendorRepository.findById(id)
//                .stream()
//                .map(this::getByDto)
//                .collect(Collectors.toList());
//    }
    public Page<VendorModel> getByAllVendor(SearchRequest request)
    {
        SearchSpecification<VendorModel> data=new SearchSpecification<>(request);
        Pageable pageable=SearchSpecification.getPageable(request.getPage(), request.getSize());
        return vendorRepository.findAll(pageable);
    }

    private VendorDto getByDto(VendorModel vendorModel) {
        VendorDto vendorDto = new VendorDto();
        vendorDto.setId(vendorModel.getId());
        vendorDto.setVendorName(vendorModel.getVendorName());
        vendorDto.setGstno(vendorModel.getGstno());
        vendorDto.setPanno(vendorModel.getPanno());
        vendorDto.setPaymentTermsConditions(vendorModel.getPaymentTermsConditions());
        vendorDto.setPaymentDays(vendorModel.getPaymentDays());
        vendorDto.setGstStatus(vendorModel.getGstStatus());
        vendorDto.setMsmeType(vendorModel.getMsmeType());
        vendorDto.setFactory(vendorModel.getFactory());
        vendorDto.setMsgmeRegisterDate(vendorModel.getMsgmeRegisterDate());
        vendorDto.setGstForm(vendorModel.getGstForm());
        vendorDto.setGstTo(vendorModel.getGstTo());
        vendorDto.setCentralgst(vendorModel.getCentralgst());
        vendorDto.setStategst(vendorModel.getStategst());
        vendorDto.setIntegratedgst(vendorModel.getIntegratedgst());
        vendorDto.setSezNumber(vendorModel.getSezNumber());
        vendorDto.setRefrencesBy(vendorModel.getRefrencesBy());
        vendorDto.setBankName(vendorModel.getBankName());
        vendorDto.setBranchName(vendorModel.getBranchName());
        vendorDto.setBankCityName(vendorModel.getBankCityName());
        vendorDto.setBankAccountNumber(vendorModel.getBankAccountNumber());
        vendorDto.setIfscCode(vendorModel.getIfscCode());
        vendorDto.setMicrCode(vendorModel.getMicrCode());
        vendorDto.setCancelChequeNumber(vendorModel.getCancelChequeNumber());
        vendorDto.setSupplierscode(vendorModel.getSupplierscode());
        vendorDto.setAccountGroupHead(vendorModel.getAccountGroupHead());
        vendorDto.setNatureOfBussiness(vendorModel.getNatureOfBussiness());
        vendorDto.setOfficePhoneNumber(vendorModel.getOfficePhoneNumber());
        vendorDto.setResidentPhoneNumber(vendorModel.getResidentPhoneNumber());
        vendorDto.setVendorEmail(vendorModel.getVendorEmail());
        vendorDto.setWebSite(vendorModel.getWebSite());
        vendorDto.setFaxNumber(vendorModel.getFaxNumber());
        vendorDto.setDateOfIncorporation(vendorModel.getDateOfIncorporation());
        vendorDto.setVendorAddressModels(vendorModel.getVendorAddressModels());
//        for (VendorAddressModel v : vendorModel.getVendorAddressModels()) {
//            vendorModel.getVendorAddressModels()
//                    .stream()
//                    .map(update -> {
//                        VendorAddressModelDto ve=new VendorAddressModelDto();
//                        ve.setId(v.getId());
//                        ve.setCity(v.getCity());
//                        System.out.println(ve);
//                        return update;
//                    }).collect(Collectors.toList());
//        }

        return vendorDto;
    }




}

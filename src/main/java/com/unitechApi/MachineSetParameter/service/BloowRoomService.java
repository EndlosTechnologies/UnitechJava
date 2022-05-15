package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.BloowRoom;
import com.unitechApi.MachineSetParameter.repository.BloowRoomRepository;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@ToString
public class BloowRoomService {
    private final BloowRoomRepository bloowRoomRepository;

    public BloowRoomService(BloowRoomRepository bloowRoomRepository) {
        this.bloowRoomRepository = bloowRoomRepository;
    }

    public static final float CONSTANT = (float) 0.0354;
    DecimalFormat df = new DecimalFormat("0.000");


    public BloowRoom SaveData(BloowRoom bloowroom) {
        //df.setMinimumFractionDigits(3);

        System.out.println((Float.parseFloat(df.format(CONSTANT * bloowroom.getDeliveryspeed() *
                bloowroom.getMachineefficency() / (bloowroom.getSilverhank() * 100)))));
        bloowroom.setProductiononratekgcardperhour(Float.parseFloat(df.format(CONSTANT * bloowroom.getDeliveryspeed() *
                bloowroom.getMachineefficency() / (bloowroom.getSilverhank() * 100))));
        bloowroom.setMachineefficencykgcardpershift(Float.parseFloat(df.format(bloowroom.getProductiononratekgcardperhour() * 12)));
        bloowroom.setMachineefficencykgcardpersixhours(Float.parseFloat(df.format(bloowroom.getMachineefficencykgcardpershift() / 2)));
        bloowroom.setMachineefficencykgcardperday(Float.parseFloat(df.format(bloowroom.getMachineefficencykgcardpershift() * 2)));
        log.info("  Bloow Room Data Added{}", bloowroom);
        return bloowRoomRepository.save(bloowroom);
    }


    public Object ViewData() {
        return bloowRoomRepository.findAll();
    }

    public void DeleteReading(Long id) {
        try {
            bloowRoomRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + id + ": ");
        }
    }

    public Optional<BloowRoom> FindByData(Long id) {
        return Optional.ofNullable(bloowRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public Page<BloowRoom> FindByDate(Date start, Date end, Pagination pagination) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return bloowRoomRepository.findByCreatedAtBetween(start, end, pagination.getpageble());
    }

    public Page<BloowRoom> listOfData(Date starts, Pagination pagination) {
        return bloowRoomRepository.findByCreatedAt(starts, pagination.getpageble());
    }


    public List<BloowRoom> ExcelDateToDateReport(Date start, Date end) {
        return bloowRoomRepository.findByShiftdateBetween(start, end);
    }

    public List<BloowRoom> ExcelDateToPerDateReport(Date start) {
        return bloowRoomRepository.findByShiftdate(start);
    }
}

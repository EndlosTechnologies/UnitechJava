package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.BloowRoom;
import com.unitechApi.MachineSetParameter.model.DrawFramesPerHank;
import com.unitechApi.MachineSetParameter.repository.DrawFramesPerHankRepository;
import com.unitechApi.Payload.response.Pagination;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DrawFramesHankService {
    public static final float CONSTANT = (float) 0.6251;

    private final DrawFramesPerHankRepository drawFramesPerHankRepository;
    DecimalFormat df = new DecimalFormat("#.###");

    public DrawFramesHankService(DrawFramesPerHankRepository drawFramesPerHankRepository) {
        this.drawFramesPerHankRepository = drawFramesPerHankRepository;
    }
    /*
     * save Data with necessary calculation
     * */

    public DrawFramesPerHank save(DrawFramesPerHank drawFramesPerHank) {
        df.setMaximumFractionDigits(3);
        drawFramesPerHank.setProductiononratekgdfper8hour(Float.parseFloat(df.format(CONSTANT * drawFramesPerHank.getDeliveryspeed() * drawFramesPerHank.getMachineefficency() / 100)));
        drawFramesPerHank.setMachineefficencykgdfper6hours(Float.parseFloat(df.format((drawFramesPerHank.getProductiononratekgdfper8hour() * 6) / 8)));
        drawFramesPerHank.setMachineefficencykgdfperday(Float.parseFloat(df.format((drawFramesPerHank.getProductiononratekgdfper8hour() * 24) / 8)));
        log.info(" { }DrawFrames In Hank Data ", drawFramesPerHank);
        return drawFramesPerHankRepository.save(drawFramesPerHank);
    }
    /*
     * get All Added Reading  from MachineReadingParameter schema
     * */
    public List<DrawFramesPerHank> ViewData() {
        return drawFramesPerHankRepository.findAll();
    }
    /*
     *   parameter Long machineI
     *   it's hard delete
     *   NOTE ->  develop a Soft Delete Machine Service
     * */
    public void DeleteReading(Long id) {
        try {
            drawFramesPerHankRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("data already deleted present " + id + ": ");
        }
    }

    /*
     * parameter Long machineId
     * get  Data By MachineId
     * if data has not in the database then throw an exception ResourceNot Found
     * */

    public DrawFramesPerHank FindByData(Long id) {

        return drawFramesPerHankRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data"));
    }
    /*
     * parameter Start CreatedDate and End CreatedDate
     * get  Data By CreatedDate
     * if data has not in the database then throw an exception DateMisMatchException
     * */

    public List<DrawFramesPerHank> FindByDate(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return drawFramesPerHankRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(c->c.getDrawFramesPerHanks().getId()))
                .collect(Collectors.toList());
    }

    /*
     * parameter Start CreatedDate
     * get  Data By CreatedDate
     * if data has not in the database then throw an exception DateMisMatchException
     * */
    public List<DrawFramesPerHank> FindBySingleDate(Date start) {
        return drawFramesPerHankRepository.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(c->c.getDrawFramesPerHanks().getId()))
                .collect(Collectors.toList());
    }

    public List<DrawFramesPerHank> ExcelDateToPerDateReport(Date start) {
        return drawFramesPerHankRepository.findByShiftdate(start)
                .stream()
                .sorted(Comparator.comparing(c->c.getDrawFramesPerHanks().getId()))
                .collect(Collectors.toList());
    }

    public List<DrawFramesPerHank> ExcelDateToDateReport(Date start, Date end) {
        return drawFramesPerHankRepository.findByShiftdateBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(c->c.getDrawFramesPerHanks().getId()))
                .collect(Collectors.toList());
    }
}

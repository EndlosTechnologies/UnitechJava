package com.unitechApi.MachineSetParameter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.addmachine.model.AddDrawFramesMachine;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "drawframes_machine", schema = "machinereading")
public class Drawframesperkg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    private Long machineId;
    @Column(name = "DELIVERYSPEED", nullable = false, updatable = false)
    private float deliveryspeed;
    @Column(name = "silverhank", nullable = false, updatable = false)
    private float silverhank;
    @Column(name = "machineefficency", nullable = false, updatable = false)
    private float machineefficency;
    @Column(name = "productiononratekgdfper8hour", nullable = false, updatable = false)
    private float productiononratekgdfper8hour;
    @Column(name = "machineefficencykgdfper6hours", nullable = false, updatable = false)
    private float machineefficencykgdfper6hours;
    @Column(name = "machineefficencykgdfperday", nullable = false, updatable = false)
    private float machineefficencykgdfperday;

    @Column(name = "shift_a_sixHoursOne")
    @Digits(integer = 4,fraction = 2,message = "can you enter the fraction part is 2 digit")
    private float shift_a_sixHoursOne;
    @Column(name = "avervg_difference_a_sixHoursOne")
    private float avervg_difference_a_sixHoursOne;
    @Column(name = "shift_a_sixHoursTwo")
    @Digits(integer = 4,fraction = 2)
    private float shift_a_sixHoursTwo;
    @Column(name = "avervg_difference_a_sixHoursTWo")
    private float avervg_difference_a_sixHoursTwo;
    @Column(name = "total_shift_prod_a")
    private float total_shift_prod_a;


    @Column(name = "shift_b_sixHoursOne")
    @Digits(integer = 4,fraction = 2)
    private float shift_b_sixHoursOne;
    @Column(name = "avervg_difference_b_sixHoursOne")
    private float avervg_difference_b_sixHoursOne;
    @Column(name = "shift_b_sixHoursTwo")
    @Digits(integer = 4,fraction = 2)
    private float shift_b_sixHoursTwo;
    @Column(name = "avervg_difference_b_sixHoursTWo")
    private float avervg_difference_b_sixHoursTwo;
    @Column(name = "total_shift_prod_b")
    private float total_shift_prod_b;

    @Column(name = "actual_producation")
    private float actual_producation;
    @Column(name = "efficiency")
    private float efficiency;
    @Column(name = "target_prod_variance_kg")
    private float target_prod_variance_kg;
    @Column(name = "target_prod_variance")
    private float target_prod_variance;
    @Column(name = "ShiftDate")
    private LocalDate shiftDate;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
    @PrePersist
    private void CreatedAt() {
        createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "d_m_id"), name = "d_m_id", referencedColumnName = "m_id")
    @JsonIgnoreProperties({"drawframesperkgReading","drawFramesPerHanks"})
    private AddDrawFramesMachine drawFramesMachine;
    public AddDrawFramesMachine getDrawFramesMachine() {
        return drawFramesMachine;
    }
    public void setDrawFramesMachine(AddDrawFramesMachine drawFramesMachine) {
        this.drawFramesMachine = drawFramesMachine;
    }
    public Long getMachineId() {
        return machineId;
    }
    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public float getDeliveryspeed() {
        return deliveryspeed;
    }

    public void setDeliveryspeed(float deliveryspeed) {
        this.deliveryspeed = deliveryspeed;
    }

    public float getSilverhank() {
        return silverhank;
    }

    public void setSilverhank(float silverhank) {
        this.silverhank = silverhank;
    }

    public float getMachineefficency() {
        return machineefficency;
    }

    public void setMachineefficency(float machineefficency) {
        this.machineefficency = machineefficency;
    }

    public float getProductiononratekgdfper8hour() {
        return productiononratekgdfper8hour;
    }

    public void setProductiononratekgdfper8hour(float productiononratekgdfper8hour) {
        this.productiononratekgdfper8hour = productiononratekgdfper8hour;
    }

    public float getMachineefficencykgdfper6hours() {
        return machineefficencykgdfper6hours;
    }

    public void setMachineefficencykgdfper6hours(float machineefficencykgdfper6hours) {
        this.machineefficencykgdfper6hours = machineefficencykgdfper6hours;
    }

    public float getMachineefficencykgdfperday() {
        return machineefficencykgdfperday;
    }

    public void setMachineefficencykgdfperday(float machineefficencykgdfperday) {
        this.machineefficencykgdfperday = machineefficencykgdfperday;
    }

    public float getShift_a_sixHoursOne() {
        return shift_a_sixHoursOne;
    }

    public void setShift_a_sixHoursOne(float shift_a_sixHoursOne) {
        this.shift_a_sixHoursOne = shift_a_sixHoursOne;
    }

    public float getAvervg_difference_a_sixHoursOne() {
        return avervg_difference_a_sixHoursOne;
    }

    public void setAvervg_difference_a_sixHoursOne(float avervg_difference_a_sixHoursOne) {
        this.avervg_difference_a_sixHoursOne = avervg_difference_a_sixHoursOne;
    }

    public float getShift_a_sixHoursTwo() {
        return shift_a_sixHoursTwo;
    }

    public void setShift_a_sixHoursTwo(float shift_a_sixHoursTwo) {
        this.shift_a_sixHoursTwo = shift_a_sixHoursTwo;
    }

    public float getAvervg_difference_a_sixHoursTwo() {
        return avervg_difference_a_sixHoursTwo;
    }

    public void setAvervg_difference_a_sixHoursTwo(float avervg_difference_a_sixHoursTwo) {
        this.avervg_difference_a_sixHoursTwo = avervg_difference_a_sixHoursTwo;
    }

    public float getTotal_shift_prod_a() {
        return total_shift_prod_a;
    }

    public void setTotal_shift_prod_a(float total_shift_prod_a) {
        this.total_shift_prod_a = total_shift_prod_a;
    }

    public float getShift_b_sixHoursOne() {
        return shift_b_sixHoursOne;
    }

    public void setShift_b_sixHoursOne(float shift_b_sixHoursOne) {
        this.shift_b_sixHoursOne = shift_b_sixHoursOne;
    }

    public float getAvervg_difference_b_sixHoursOne() {
        return avervg_difference_b_sixHoursOne;
    }

    public void setAvervg_difference_b_sixHoursOne(float avervg_difference_b_sixHoursOne) {
        this.avervg_difference_b_sixHoursOne = avervg_difference_b_sixHoursOne;
    }

    public float getShift_b_sixHoursTwo() {
        return shift_b_sixHoursTwo;
    }

    public void setShift_b_sixHoursTwo(float shift_b_sixHoursTwo) {
        this.shift_b_sixHoursTwo = shift_b_sixHoursTwo;
    }

    public float getAvervg_difference_b_sixHoursTwo() {
        return avervg_difference_b_sixHoursTwo;
    }

    public void setAvervg_difference_b_sixHoursTwo(float avervg_difference_b_sixHoursTwo) {
        this.avervg_difference_b_sixHoursTwo = avervg_difference_b_sixHoursTwo;
    }

    public float getTotal_shift_prod_b() {
        return total_shift_prod_b;
    }

    public void setTotal_shift_prod_b(float total_shift_prod_b) {
        this.total_shift_prod_b = total_shift_prod_b;
    }

    public float getActual_producation() {
        return actual_producation;
    }

    public void setActual_producation(float actual_producation) {
        this.actual_producation = actual_producation;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(float efficiency) {
        this.efficiency = efficiency;
    }

    public float getTarget_prod_variance_kg() {
        return target_prod_variance_kg;
    }

    public void setTarget_prod_variance_kg(float target_prod_variance_kg) {
        this.target_prod_variance_kg = target_prod_variance_kg;
    }

    public float getTarget_prod_variance() {
        return target_prod_variance;
    }

    public void setTarget_prod_variance(float target_prod_variance) {
        this.target_prod_variance = target_prod_variance;
    }

    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(LocalDate shiftDate) {
        this.shiftDate = shiftDate;
    }

    public void idupdatedrawframes(AddDrawFramesMachine addDrawFramesMachine) {
        this.drawFramesMachine = addDrawFramesMachine;
    }

    @Override
    public String toString() {
        return "Drawframesperkg{" +
                "machineId=" + machineId +
                ", deliveryspeed=" + deliveryspeed +
                ", silverhank=" + silverhank +
                ", machineefficency=" + machineefficency +
                ", productiononratekgdfper8hour=" + productiononratekgdfper8hour +
                ", machineefficencykgdfper6hours=" + machineefficencykgdfper6hours +
                ", machineefficencykgdfperday=" + machineefficencykgdfperday +
                ", createdAt=" + createdAt +
                ", drawFramesMachine=" + drawFramesMachine +
                '}';
    }
}

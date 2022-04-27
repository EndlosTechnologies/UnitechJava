package com.unitechApi.MachineSetParameter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.addmachine.model.AddComber;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "comber_machine", schema = "machinereading")
public class Comber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(nullable = false)
    @GenericGenerator(name = "native", strategy = "native")
    private Long machineId;
    @Column(name = "comberSpeedNpm", nullable = false, updatable = false)
    private float comberSpeedRpm;
    @Column(name = "FeedNip", nullable = false, updatable = false)
    private float FeedNip;
    @Column(name = "LapWeight", nullable = false, updatable = false)
    private float LapWeight;
    @Column(name = "MachineEfficency", nullable = false, updatable = false)
    private float MachineEfficency;
    @Column(name = "Noil", nullable = false, updatable = false)
    private float Noil;
    @Column(name = "productioMc8Hour", nullable = false, updatable = false)
    private float productioMc8Hour;
    @Column(name = "productioMc6Hour", nullable = false, updatable = false)
    private float productioMc6Hour;
    @Column(name = "productioShift", nullable = false, updatable = false)
    private float productioShift;
    @Column(name = "productioMc24Hour", nullable = false, updatable = false)
    private float productioMc24Hour;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "co_m_id"), name = "co_m_id", referencedColumnName = "m_id")
    @JsonIgnoreProperties("comberreading")
    private AddComber addcomber;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
    @PrePersist
    private void CreatedAt() {
        createdAt = new Date();
    }

    public void setProductioShift(float productioShift) {
        this.productioShift = productioShift;
    }

    public AddComber getAddcomber() {
        return addcomber;
    }

    public void setAddcomber(AddComber addcomber) {
        this.addcomber = addcomber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public float getComberSpeedRpm() {
        return comberSpeedRpm;
    }

    public void setComberSpeedRpm(float comberSpeedRpm) {
        this.comberSpeedRpm = comberSpeedRpm;
    }

    public float getFeedNip() {
        return FeedNip;
    }

    public void setFeedNip(float feedNip) {
        FeedNip = feedNip;
    }

    public float getLapWeight() {
        return LapWeight;
    }

    public void setLapWeight(float lapWeight) {
        LapWeight = lapWeight;
    }

    public float getMachineEfficency() {
        return MachineEfficency;
    }

    public void setMachineEfficency(float machineEfficency) {
        MachineEfficency = machineEfficency;
    }

    public float getNoil() {
        return Noil;
    }

    public void setNoil(float noil) {
        Noil = noil;
    }

    public float getProductioMc8Hour() {
        return productioMc8Hour;
    }

    public void setProductioMc8Hour(float productioMc8Hour) {
        this.productioMc8Hour = productioMc8Hour;
    }

    public float getProductioMc6Hour() {
        return productioMc6Hour;
    }

    public void setProductioMc6Hour(float productioMc6Hour) {
        this.productioMc6Hour = productioMc6Hour;
    }

    public float getProductioMc24Hour() {
        return productioMc24Hour;
    }

    public void setProductioMc24Hour(float productioMc24Hour) {
        this.productioMc24Hour = productioMc24Hour;
    }

    public float getProductioShift() {
        return productioShift;
    }

    public void setProductioMcShift(float productioShift) {
        this.productioShift = productioShift;
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

    public void updateid(AddComber addComber) {
        this.addcomber=addComber;
    }
}

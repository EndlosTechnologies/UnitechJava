package com.unitechApi.MachineSetParameter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.addmachine.model.AddSimplexMAchine;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "simplex_machine", schema = "machinereading")
public class Simplex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @GenericGenerator(name = "native", strategy = "native")
    private Long machineId;
    @Column(name = "SpeedRpm")
    private float SpeedRpm;
    @Column(name = "MachineEfficiency")
    private float MachineEfficiency;
    @Column(name = "TM")
    private float TM;
    @Column(name = "RovingHank")
    private float RovingHank;

    @Column(name = "TPI")
    private float TPI;

    @Column(name = "productionSpindle8hours")
    private float productionSpindle8hours;
    @Column(name = "conversionTo12HoursSpindleShift")
    private float conversionTo12HoursSpindleShift;
    @Column(name = "productionMachine200SpindlesMachineKgPer12Hours")
    private float productionMachine200SpindlesMachineKgPer12Hours;
    @Column(name = "productionSpindle8hoursHank")
    private float productionSpindle8hoursHank;
    @Column(name = "productionMachine200SpindlesMachineHankPer12Hours")
    private float productionMachine200SpindlesMachineHankPer12Hours;
    @Column(name = "conversionTo12HoursSpindleShiftHank")
    private float conversionTo12HoursSpindleShiftHank;
    @Column(name = "conversionTo6HoursSpindleShift")
    private float conversionTo6HoursSpindleShift;
    @Column(name = "conversionTo6HoursMachineShiftKgs")
    private float conversionTo6HoursMachineShiftKgs;

    @Column(name = "conversionTo24HoursMachineShiftKgs")
    private float conversionTo24HoursMachineShiftKgs;
    @Column(name = "conversionTo24HoursMachineShiftHank")
    private float conversionTo24HoursMachineShiftHank;


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



    @CreationTimestamp
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
    @JoinColumn(foreignKey = @ForeignKey(name = "s_m_id"), name = "s_m_id", referencedColumnName = "m_id")
    @JsonIgnoreProperties("simplexReading")

    private AddSimplexMAchine simplex;

    public AddSimplexMAchine getSimplex() {
        return simplex;
    }

    public void setSimplex(AddSimplexMAchine simplex) {
        this.simplex = simplex;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public float getSpeedRpm() {
        return SpeedRpm;
    }

    public void setSpeedRpm(float speedRpm) {
        SpeedRpm = speedRpm;
    }

    public float getMachineEfficiency() {
        return MachineEfficiency;
    }

    public void setMachineEfficiency(float machineEfficiency) {
        MachineEfficiency = machineEfficiency;
    }

    public float getTM() {
        return TM;
    }

    public void setTM(float TM) {
        this.TM = TM;
    }

    public float getRovingHank() {
        return RovingHank;
    }

    public void setRovingHank(float rovingHank) {
        RovingHank = rovingHank;
    }

    public float getTPI() {
        return TPI;
    }

    public void setTPI(float TPI) {
        this.TPI = TPI;
    }

    public float getProductionSpindle8hours() {
        return productionSpindle8hours;
    }

    public void setProductionSpindle8hours(float productionSpindle8hours) {
        this.productionSpindle8hours = productionSpindle8hours;
    }

    public float getConversionTo12HoursSpindleShift() {
        return conversionTo12HoursSpindleShift;
    }

    public void setConversionTo12HoursSpindleShift(float conversionTo12HoursSpindleShift) {
        this.conversionTo12HoursSpindleShift = conversionTo12HoursSpindleShift;
    }

    public float getProductionMachine200SpindlesMachineKgPer12Hours() {
        return productionMachine200SpindlesMachineKgPer12Hours;
    }

    public void setProductionMachine200SpindlesMachineKgPer12Hours(float productionMachine200SpindlesMachineKgPer12Hours) {
        this.productionMachine200SpindlesMachineKgPer12Hours = productionMachine200SpindlesMachineKgPer12Hours;
    }

    public float getProductionSpindle8hoursHank() {
        return productionSpindle8hoursHank;
    }

    public void setProductionSpindle8hoursHank(float productionSpindle8hoursHank) {
        this.productionSpindle8hoursHank = productionSpindle8hoursHank;
    }

    public float getProductionMachine200SpindlesMachineHankPer12Hours() {
        return productionMachine200SpindlesMachineHankPer12Hours;
    }

    public void setProductionMachine200SpindlesMachineHankPer12Hours(float productionMachine200SpindlesMachineHankPer12Hours) {
        this.productionMachine200SpindlesMachineHankPer12Hours = productionMachine200SpindlesMachineHankPer12Hours;
    }

    public float getConversionTo12HoursSpindleShiftHank() {
        return conversionTo12HoursSpindleShiftHank;
    }

    public void setConversionTo12HoursSpindleShiftHank(float conversionTo12HoursSpindleShiftHank) {
        this.conversionTo12HoursSpindleShiftHank = conversionTo12HoursSpindleShiftHank;
    }

    public float getConversionTo6HoursSpindleShift() {
        return conversionTo6HoursSpindleShift;
    }

    public void setConversionTo6HoursSpindleShift(float conversionTo6HoursSpindleShift) {
        this.conversionTo6HoursSpindleShift = conversionTo6HoursSpindleShift;
    }

    public float getConversionTo6HoursMachineShiftKgs() {
        return conversionTo6HoursMachineShiftKgs;
    }

    public void setConversionTo6HoursMachineShiftKgs(float conversionTo6HoursMachineShiftKgs) {
        this.conversionTo6HoursMachineShiftKgs = conversionTo6HoursMachineShiftKgs;
    }

    public float getConversionTo24HoursMachineShiftKgs() {
        return conversionTo24HoursMachineShiftKgs;
    }

    public void setConversionTo24HoursMachineShiftKgs(float conversionTo24HoursMachineShiftKgs) {
        this.conversionTo24HoursMachineShiftKgs = conversionTo24HoursMachineShiftKgs;
    }

    public float getConversionTo24HoursMachineShiftHank() {
        return conversionTo24HoursMachineShiftHank;
    }

    public void setConversionTo24HoursMachineShiftHank(float conversionTo24HoursMachineShiftHank) {
        this.conversionTo24HoursMachineShiftHank = conversionTo24HoursMachineShiftHank;
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

    public void idupdate(AddSimplexMAchine addSimplexMAchine) {
        this.simplex = addSimplexMAchine;
    }
}

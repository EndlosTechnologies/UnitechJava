package com.unitechApi.MachineSetParameter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.addmachine.model.AddRingFramesMachine;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "ringframe_machine", schema = "machinereading")
@ToString
public class RingFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @GenericGenerator(name = "native", strategy = "native")
    private Long machineId;
    @Column(name = "spindleRpm")
    private float spindleRpm;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RIngFrameEnumType type;
    @Column(name = "ringFrameCount")
    private float ringFrameCount;
    @Column(name = "TM")
    private float TM;
    @Column(name = "TPI")
    private float TPI;

    @Column(name = "machineEfficiency")
    private float machineEfficiency;
    @Column(name = "pneumafilWaste")
    private float pneumafilWaste;
    @Column(name = "idleSpindle")
    private float idleSpindle;
    @Column(name = "doffingLoss")
    private float doffingLoss;
    @Column(name = "totalLoss")
    private float totalLoss;
    @Column(name = "totalLossKg")
    private float totalLossKg;
    @Column(name = "netProduction")
    private float netProduction;


    @Column(name = "productionSpindleGrams")
    private float productionSpindleGrams;
    @Column(name = "productionSpindle8HoursKg")
    private float productionSpindle8HoursKg;
    @Column(name = "productionSpindle24HoursKg")
    private float productionSpindle24HoursKg;
    @Column(name = "productionSpindle2HoursKg")
    private float productionSpindle2HoursKg;


    @Column(name = "shift_a_twoHoursOne")
    @Digits(integer = 4,fraction = 2,message = "can you enter the fraction part is 2 digit")
    private float shift_a_twoHoursOne;

    public float getAverageshift_a_HankOne() {
        return averageshift_a_HankOne;
    }

    public void setAverageshift_a_HankOne(float averageshift_a_HankOne) {
        this.averageshift_a_HankOne = averageshift_a_HankOne;
    }

    @Column(name = "averageshift_a_HankOne")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankOne;
    @Column(name = "averageshift_a_HankTwo")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankTwo;
    @Column(name = "averageshift_a_HankThree")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankThree;
    @Column(name = "averageshift_a_HankFour")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankFour;
    @Column(name = "averageshift_a_HankFive")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankFive;
    @Column(name = "averageshift_a_HankSix")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankSix;
    @Column(name = "averageshift_a_HankSeven")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankSeven;
    @Column(name = "averageshift_a_HankEight")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankEight;
    @Column(name = "averageshift_a_HankNine")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankNine;
    @Column(name = "averageshift_a_HankTen")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankTen;
    @Column(name = "averageshift_a_HankEleven")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankEleven;
    @Column(name = "averageshift_a_HankTwelve")
    @Digits(integer = 4,fraction = 2)
    private float averageshift_a_HankTwelev;
    @Column(name = "avervg_difference_a_twoHoursOne")
    private float avervg_difference_a_twoHoursOne;

    @Column(name = "shift_a_twoHoursTwo")
    @Digits(integer = 4,fraction = 2)
    private float shift_a_twoHoursTwo;
    @Column(name = "avervg_difference_a_twoHoursTWo")
    private float avervg_difference_a_twoHoursTwo;

    @Column(name = "shift_a_twoHoursThree")
    @Digits(integer = 4,fraction = 2)
    private float shift_a_twoHoursThree;
    @Column(name = "avervg_difference_a_twoHoursThree")
    private float avervg_difference_a_twoHoursThree;

    @Column(name = "shift_a_twoHoursFour")
    @Digits(integer = 4,fraction = 2)
    private float shift_a_twoHoursFour;

    @Column(name = "avervg_difference_a_twoHoursFour")
    private float avervg_difference_a_twoHoursFour;

    @Column(name = "shift_a_twoHoursFive")
    @Digits(integer = 4,fraction = 2)
    private float shift_a_twoHoursFive;
    @Column(name = "avervg_difference_a_twoHoursFive")
    private float avervg_difference_a_twoHoursFive;

    @Column(name = "shift_a_twoHoursSix")
    @Digits(integer = 4,fraction = 2)
    private float shift_a_twoHoursSix;
    @Column(name = "avervg_difference_a_twoHoursSix")
    private float avervg_difference_a_twoHoursSix;

    @Column(name = "total_shift_prod_a")
    private float total_shift_prod_a;


    @Column(name = "shift_b_twoHoursOne")
    @Digits(integer = 4,fraction = 2,message = "can you enter the fraction part is 2 digit")
    private float shift_b_twoHoursOne;
    @Column(name = "avervg_difference_b_twoHoursOne")
    private float avervg_difference_b_twoHoursOne;

    @Column(name = "shift_b_twoHoursTwo")
    @Digits(integer = 4,fraction = 2)
    private float shift_b_twoHoursTwo;
    @Column(name = "avervg_difference_b_twoHoursTWo")
    private float avervg_difference_b_twoHoursTwo;

    @Column(name = "shift_b_twoHoursThree")
    @Digits(integer = 4,fraction = 2)
    private float shift_b_twoHoursThree;
    @Column(name = "avervg_difference_b_twoHoursThree")
    private float avervg_difference_b_twoHoursThree;

    @Column(name = "shift_b_twoHoursFour")
    @Digits(integer = 4,fraction = 2)
    private float shift_b_twoHoursFour;
    @Column(name = "avervg_difference_b_twoHoursFour")
    private float avervg_difference_b_twoHoursFour;

    @Column(name = "shift_b_twoHoursFive")
    @Digits(integer = 4,fraction = 2)
    private float shift_b_twoHoursFive;
    @Column(name = "avervg_difference_b_twoHoursFive")
    private float avervg_difference_b_twoHoursFive;

    @Column(name = "shift_b_twoHoursSix")
    @Digits(integer = 4,fraction = 2)
    private float shift_b_twoHoursSix;
    @Column(name = "avervg_difference_b_twoHoursSix")
    private float avervg_difference_b_twoHoursSix;

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
    @JoinColumn(foreignKey = @ForeignKey(name = "r_m_id"), name = "r_m_id", referencedColumnName = "m_id")
    @JsonIgnoreProperties({"ringframesReading","usageItems"})
    private AddRingFramesMachine ringframe;

    public AddRingFramesMachine getRingframe() {
        return ringframe;
    }

    public void setRingframe(AddRingFramesMachine ringframe) {
        this.ringframe = ringframe;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public float getSpindleRpm() {
        return spindleRpm;
    }

    public void setSpindleRpm(float spindleRpm) {
        this.spindleRpm = spindleRpm;
    }

    public RIngFrameEnumType getType() {
        return type;
    }

    public void setType(RIngFrameEnumType type) {
        this.type = type;
    }

    public float getRingFrameCount() {
        return ringFrameCount;
    }

    public void setRingFrameCount(float ringFrameCount) {
        this.ringFrameCount = ringFrameCount;
    }

    public float getTM() {
        return TM;
    }

    public void setTM(float TM) {
        this.TM = TM;
    }

    public float getTPI() {
        return TPI;
    }

    public void setTPI(float TPI) {
        this.TPI = TPI;
    }

    public float getMachineEfficiency() {
        return machineEfficiency;
    }

    public void setMachineEfficiency(float machineEfficiency) {
        this.machineEfficiency = machineEfficiency;
    }

    public float getPneumafilWaste() {
        return pneumafilWaste;
    }

    public void setPneumafilWaste(float pneumafilWaste) {
        this.pneumafilWaste = pneumafilWaste;
    }

    public float getIdleSpindle() {
        return idleSpindle;
    }

    public void setIdleSpindle(float idleSpindle) {
        this.idleSpindle = idleSpindle;
    }

    public float getDoffingLoss() {
        return doffingLoss;
    }

    public void setDoffingLoss(float doffingLoss) {
        this.doffingLoss = doffingLoss;
    }

    public float getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(float totalLoss) {
        this.totalLoss = totalLoss;
    }

    public float getTotalLossKg() {
        return totalLossKg;
    }

    public void setTotalLossKg(float totalLossKg) {
        this.totalLossKg = totalLossKg;
    }

    public float getNetProduction() {
        return netProduction;
    }

    public void setNetProduction(float netProduction) {
        this.netProduction = netProduction;
    }

    public float getProductionSpindleGrams() {
        return productionSpindleGrams;
    }

    public void setProductionSpindleGrams(float productionSpindleGrams) {
        this.productionSpindleGrams = productionSpindleGrams;
    }

    public float getProductionSpindle8HoursKg() {
        return productionSpindle8HoursKg;
    }

    public void setProductionSpindle8HoursKg(float productionSpindle8HoursKg) {
        this.productionSpindle8HoursKg = productionSpindle8HoursKg;
    }

    public float getProductionSpindle24HoursKg() {
        return productionSpindle24HoursKg;
    }

    public void setProductionSpindle24HoursKg(float productionSpindle24HoursKg) {
        this.productionSpindle24HoursKg = productionSpindle24HoursKg;
    }

    public float getProductionSpindle2HoursKg() {
        return productionSpindle2HoursKg;
    }

    public void setProductionSpindle2HoursKg(float productionSpindle2HoursKg) {
        this.productionSpindle2HoursKg = productionSpindle2HoursKg;
    }

    public float getShift_a_twoHoursOne() {
        return shift_a_twoHoursOne;
    }

    public void setShift_a_twoHoursOne(float shift_a_twoHoursOne) {
        this.shift_a_twoHoursOne = shift_a_twoHoursOne;
    }

    public float getAvervg_difference_a_twoHoursOne() {
        return avervg_difference_a_twoHoursOne;
    }

    public void setAvervg_difference_a_twoHoursOne(float avervg_difference_a_twoHoursOne) {
        this.avervg_difference_a_twoHoursOne = avervg_difference_a_twoHoursOne;
    }

    public float getShift_a_twoHoursTwo() {
        return shift_a_twoHoursTwo;
    }

    public void setShift_a_twoHoursTwo(float shift_a_twoHoursTwo) {
        this.shift_a_twoHoursTwo = shift_a_twoHoursTwo;
    }

    public float getAvervg_difference_a_twoHoursTwo() {
        return avervg_difference_a_twoHoursTwo;
    }

    public void setAvervg_difference_a_twoHoursTwo(float avervg_difference_a_twoHoursTwo) {
        this.avervg_difference_a_twoHoursTwo = avervg_difference_a_twoHoursTwo;
    }

    public float getShift_a_twoHoursThree() {
        return shift_a_twoHoursThree;
    }

    public void setShift_a_twoHoursThree(float shift_a_twoHoursThree) {
        this.shift_a_twoHoursThree = shift_a_twoHoursThree;
    }

    public float getAvervg_difference_a_twoHoursThree() {
        return avervg_difference_a_twoHoursThree;
    }

    public void setAvervg_difference_a_twoHoursThree(float avervg_difference_a_twoHoursThree) {
        this.avervg_difference_a_twoHoursThree = avervg_difference_a_twoHoursThree;
    }

    public float getShift_a_twoHoursFour() {
        return shift_a_twoHoursFour;
    }

    public void setShift_a_twoHoursFour(float shift_a_twoHoursFour) {
        this.shift_a_twoHoursFour = shift_a_twoHoursFour;
    }

    public float getAvervg_difference_a_twoHoursFour() {
        return avervg_difference_a_twoHoursFour;
    }

    public void setAvervg_difference_a_twoHoursFour(float avervg_difference_a_twoHoursFour) {
        this.avervg_difference_a_twoHoursFour = avervg_difference_a_twoHoursFour;
    }

    public float getShift_a_twoHoursFive() {
        return shift_a_twoHoursFive;
    }

    public void setShift_a_twoHoursFive(float shift_a_twoHoursFive) {
        this.shift_a_twoHoursFive = shift_a_twoHoursFive;
    }

    public float getAvervg_difference_a_twoHoursFive() {
        return avervg_difference_a_twoHoursFive;
    }

    public void setAvervg_difference_a_twoHoursFive(float avervg_difference_a_twoHoursFive) {
        this.avervg_difference_a_twoHoursFive = avervg_difference_a_twoHoursFive;
    }

    public float getShift_a_twoHoursSix() {
        return shift_a_twoHoursSix;
    }

    public void setShift_a_twoHoursSix(float shift_a_twoHoursSix) {
        this.shift_a_twoHoursSix = shift_a_twoHoursSix;
    }

    public float getAvervg_difference_a_twoHoursSix() {
        return avervg_difference_a_twoHoursSix;
    }

    public void setAvervg_difference_a_twoHoursSix(float avervg_difference_a_twoHoursSix) {
        this.avervg_difference_a_twoHoursSix = avervg_difference_a_twoHoursSix;
    }

    public float getTotal_shift_prod_a() {
        return total_shift_prod_a;
    }

    public void setTotal_shift_prod_a(float total_shift_prod_a) {
        this.total_shift_prod_a = total_shift_prod_a;
    }

    public float getShift_b_twoHoursOne() {
        return shift_b_twoHoursOne;
    }

    public void setShift_b_twoHoursOne(float shift_b_twoHoursOne) {
        this.shift_b_twoHoursOne = shift_b_twoHoursOne;
    }

    public float getAvervg_difference_b_twoHoursOne() {
        return avervg_difference_b_twoHoursOne;
    }

    public void setAvervg_difference_b_twoHoursOne(float avervg_difference_b_twoHoursOne) {
        this.avervg_difference_b_twoHoursOne = avervg_difference_b_twoHoursOne;
    }

    public float getShift_b_twoHoursTwo() {
        return shift_b_twoHoursTwo;
    }

    public void setShift_b_twoHoursTwo(float shift_b_twoHoursTwo) {
        this.shift_b_twoHoursTwo = shift_b_twoHoursTwo;
    }

    public float getAvervg_difference_b_twoHoursTwo() {
        return avervg_difference_b_twoHoursTwo;
    }

    public void setAvervg_difference_b_twoHoursTwo(float avervg_difference_b_twoHoursTwo) {
        this.avervg_difference_b_twoHoursTwo = avervg_difference_b_twoHoursTwo;
    }

    public float getShift_b_twoHoursThree() {
        return shift_b_twoHoursThree;
    }

    public void setShift_b_twoHoursThree(float shift_b_twoHoursThree) {
        this.shift_b_twoHoursThree = shift_b_twoHoursThree;
    }

    public float getAvervg_difference_b_twoHoursThree() {
        return avervg_difference_b_twoHoursThree;
    }

    public void setAvervg_difference_b_twoHoursThree(float avervg_difference_b_twoHoursThree) {
        this.avervg_difference_b_twoHoursThree = avervg_difference_b_twoHoursThree;
    }

    public float getShift_b_twoHoursFour() {
        return shift_b_twoHoursFour;
    }

    public void setShift_b_twoHoursFour(float shift_b_twoHoursFour) {
        this.shift_b_twoHoursFour = shift_b_twoHoursFour;
    }

    public float getAvervg_difference_b_twoHoursFour() {
        return avervg_difference_b_twoHoursFour;
    }

    public void setAvervg_difference_b_twoHoursFour(float avervg_difference_b_twoHoursFour) {
        this.avervg_difference_b_twoHoursFour = avervg_difference_b_twoHoursFour;
    }

    public float getShift_b_twoHoursFive() {
        return shift_b_twoHoursFive;
    }

    public void setShift_b_twoHoursFive(float shift_b_twoHoursFive) {
        this.shift_b_twoHoursFive = shift_b_twoHoursFive;
    }

    public float getAvervg_difference_b_twoHoursFive() {
        return avervg_difference_b_twoHoursFive;
    }

    public void setAvervg_difference_b_twoHoursFive(float avervg_difference_b_twoHoursFive) {
        this.avervg_difference_b_twoHoursFive = avervg_difference_b_twoHoursFive;
    }

    public float getShift_b_twoHoursSix() {
        return shift_b_twoHoursSix;
    }

    public void setShift_b_twoHoursSix(float shift_b_twoHoursSix) {
        this.shift_b_twoHoursSix = shift_b_twoHoursSix;
    }

    public float getAvervg_difference_b_twoHoursSix() {
        return avervg_difference_b_twoHoursSix;
    }

    public void setAvervg_difference_b_twoHoursSix(float avervg_difference_b_twoHoursSix) {
        this.avervg_difference_b_twoHoursSix = avervg_difference_b_twoHoursSix;
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

    public float getAverageshift_a_HankTwo() {
        return averageshift_a_HankTwo;
    }

    public void setAverageshift_a_HankTwo(float averageshift_a_HankTwo) {
        this.averageshift_a_HankTwo = averageshift_a_HankTwo;
    }

    public float getAverageshift_a_HankThree() {
        return averageshift_a_HankThree;
    }

    public void setAverageshift_a_HankThree(float averageshift_a_HankThree) {
        this.averageshift_a_HankThree = averageshift_a_HankThree;
    }

    public float getAverageshift_a_HankFour() {
        return averageshift_a_HankFour;
    }

    public void setAverageshift_a_HankFour(float averageshift_a_HankFour) {
        this.averageshift_a_HankFour = averageshift_a_HankFour;
    }

    public float getAverageshift_a_HankFive() {
        return averageshift_a_HankFive;
    }

    public void setAverageshift_a_HankFive(float averageshift_a_HankFive) {
        this.averageshift_a_HankFive = averageshift_a_HankFive;
    }

    public float getAverageshift_a_HankSix() {
        return averageshift_a_HankSix;
    }

    public void setAverageshift_a_HankSix(float averageshift_a_HankSix) {
        this.averageshift_a_HankSix = averageshift_a_HankSix;
    }

    public float getAverageshift_a_HankSeven() {
        return averageshift_a_HankSeven;
    }

    public void setAverageshift_a_HankSeven(float averageshift_a_HankSeven) {
        this.averageshift_a_HankSeven = averageshift_a_HankSeven;
    }

    public float getAverageshift_a_HankEight() {
        return averageshift_a_HankEight;
    }

    public void setAverageshift_a_HankEight(float averageshift_a_HankEight) {
        this.averageshift_a_HankEight = averageshift_a_HankEight;
    }

    public float getAverageshift_a_HankNine() {
        return averageshift_a_HankNine;
    }

    public void setAverageshift_a_HankNine(float averageshift_a_HankNine) {
        this.averageshift_a_HankNine = averageshift_a_HankNine;
    }

    public float getAverageshift_a_HankTen() {
        return averageshift_a_HankTen;
    }

    public void setAverageshift_a_HankTen(float averageshift_a_HankTen) {
        this.averageshift_a_HankTen = averageshift_a_HankTen;
    }

    public float getAverageshift_a_HankEleven() {
        return averageshift_a_HankEleven;
    }

    public void setAverageshift_a_HankEleven(float averageshift_a_HankEleven) {
        this.averageshift_a_HankEleven = averageshift_a_HankEleven;
    }

    public float getAverageshift_a_HankTwelev() {
        return averageshift_a_HankTwelev;
    }

    public void setAverageshift_a_HankTwelev(float averageshift_a_HankTwelev) {
        this.averageshift_a_HankTwelev = averageshift_a_HankTwelev;
    }

    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(LocalDate shiftDate) {
        this.shiftDate = shiftDate;
    }

    public void idupdate(AddRingFramesMachine addRingFramesMachine) {
        this.ringframe = addRingFramesMachine;
    }
}

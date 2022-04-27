package com.unitechApi.MachineSetParameter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.addmachine.model.AddCardingMachine;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "cardings", schema = "machinereading")
@ToString
public class Carding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @GenericGenerator(name = "native", strategy = "native")
    private Long machineId;
    @Column(name = "deliveryspeed", nullable = false, updatable = false)
    private float deliveryspeed;
    @Column(name = "silverhank", nullable = false, updatable = false)
    private float silverhank;
    @Column(name = "machineefficency", nullable = false, updatable = false)
    private float machineefficency;
    @Column(name = "productiononratekgcardperhour", nullable = false, updatable = false)
    private float productiononratekgcardperhour;
    @Column(name = "machineefficencykgcardpershift", nullable = false, updatable = false)
    private float machineefficencykgcardpershift;
    @Column(name = "machineefficencykgcardpersixhours", nullable = false, updatable = false)
    private float machineefficencykgcardpersixhours;
    @Column(name = "machineefficencykgcardperday", nullable = false, updatable = false)
    private float machineefficencykgcardperday;

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
    @JoinColumn(foreignKey = @ForeignKey(name = "c_m_id"), name = "c_m_id", referencedColumnName = "m_id")
    @JsonIgnoreProperties("cardingsreading")
    private AddCardingMachine cardingMachine;

    public AddCardingMachine getCardingMachine() {
        return cardingMachine;
    }

    public void setCardingMachine(AddCardingMachine cardingMachine) {
        this.cardingMachine = cardingMachine;
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

    public float getProductiononratekgcardperhour() {
        return productiononratekgcardperhour;
    }

    public void setProductiononratekgcardperhour(float productiononratekgcardperhour) {
        this.productiononratekgcardperhour = productiononratekgcardperhour;
    }

    public float getMachineefficencykgcardpershift() {
        return machineefficencykgcardpershift;
    }

    public void setMachineefficencykgcardpershift(float machineefficencykgcardpershift) {
        this.machineefficencykgcardpershift = machineefficencykgcardpershift;
    }

    public float getMachineefficencykgcardpersixhours() {
        return machineefficencykgcardpersixhours;
    }

    public void setMachineefficencykgcardpersixhours(float machineefficencykgcardpersixhours) {
        this.machineefficencykgcardpersixhours = machineefficencykgcardpersixhours;
    }

    public float getMachineefficencykgcardperday() {
        return machineefficencykgcardperday;
    }

    public void setMachineefficencykgcardperday(float machineefficencykgcardperday) {
        this.machineefficencykgcardperday = machineefficencykgcardperday;
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

    public void idupdateincarding(AddCardingMachine addCardingMachine) {
        this.cardingMachine = addCardingMachine;
    }
}

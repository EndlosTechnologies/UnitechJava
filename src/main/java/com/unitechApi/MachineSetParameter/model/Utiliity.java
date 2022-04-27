package com.unitechApi.MachineSetParameter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.addmachine.model.AddUtillityMachine;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "utiliity_machine", schema = "machinereading")
public class Utiliity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @GenericGenerator(name = "native", strategy = "native")
    private Long machineId;
    @Column(name = "para1")
    private float para1;
    @Column(name = "para2")
    private float para2;
    @Column(name = "para3")
    private float para3;
    @Column(name = "para4")
    private float para4;
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
    @JoinColumn(foreignKey = @ForeignKey(name = "u_m_id"), name = "u_m_id", referencedColumnName = "m_id")
    @JsonIgnoreProperties("utilityReading")
    private AddUtillityMachine utillity;

    public AddUtillityMachine getUtillity() {
        return utillity;
    }

    public void setUtillity(AddUtillityMachine utillity) {
        this.utillity = utillity;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public float getPara1() {
        return para1;
    }

    public void setPara1(float para1) {
        this.para1 = para1;
    }

    public float getPara2() {
        return para2;
    }

    public void setPara2(float para2) {
        this.para2 = para2;
    }

    public float getPara3() {
        return para3;
    }

    public void setPara3(float para3) {
        this.para3 = para3;
    }

    public float getPara4() {
        return para4;
    }

    public void setPara4(float para4) {
        this.para4 = para4;
    }

    public void idupdate(AddUtillityMachine addUtillityMachine) {
        this.utillity = addUtillityMachine;
    }
}

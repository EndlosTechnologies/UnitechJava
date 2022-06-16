package com.unitechApi.addmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.MachineSetParameter.model.FinisherperHank;
import com.unitechApi.MachineSetParameter.model.FinisherperKg;
import com.unitechApi.store.indent.Model.UsageItem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "add_finisher", schema = "AddMachine")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AddFinisherMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(name = "m_id")
    private Long id;
    @Column(name = "machine_name",unique = true)
    private String name;
    @Column(name = "machine_descrption")
    private String descrption;
    @Column(name = "status")
    private boolean status;

    public AddFinisherMachine(boolean status) {
        this.status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "finisherMachine", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("finisherMachine")
    private Set<FinisherperKg> finisherperKgReading;

    @OneToMany(mappedBy = "finisherhankMachine", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"finisherhankMachine","usageItems"})
    private Set<FinisherperHank> finisherperhankMachineReading;

    @OneToMany(mappedBy = "finisherMachinedata",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("finisherMachinedata")
    private Set<UsageItem> usageItems;

    public Set<UsageItem> getUsageItems() {
        return usageItems;
    }

    public void setUsageItems(Set<UsageItem> usageItems) {
        this.usageItems = usageItems;
    }

    public Set<FinisherperKg> getFinisherperKgReading() {
        return finisherperKgReading;
    }

    public void setFinisherperKgReading(Set<FinisherperKg> finisherperKgReading) {
        this.finisherperKgReading = finisherperKgReading;
    }

    public Set<FinisherperHank> getFinisherperhankMachineReading() {
        return finisherperhankMachineReading;
    }

    public void setFinisherperhankMachineReading(Set<FinisherperHank> finisherperhankMachineReading) {
        this.finisherperhankMachineReading = finisherperhankMachineReading;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
}


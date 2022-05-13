package com.unitechApi.addmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.MachineSetParameter.model.Wasteroom;
import com.unitechApi.store.indent.Model.UsageItem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "add_wasteroom", schema = "AddMachine")
@NoArgsConstructor
@AllArgsConstructor
public class AddWasteRoomeMAchine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(name = "m_id")
    private Long id;
    @Column(name = "machine_name")
    private String name;
    @Column(name = "machine_descrption")
    private String descrption;
    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "wasteMachineusage",cascade =CascadeType.ALL)
    @JsonIgnoreProperties("wasteMachineusage")
    private Set<UsageItem> usageItems;

    public Set<UsageItem> getUsageItems() {
        return usageItems;
    }

    public void setUsageItems(Set<UsageItem> usageItems) {
        this.usageItems = usageItems;
    }

    public AddWasteRoomeMAchine(boolean status) {
        this.status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "wasteroom", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("wasteroom")
    private Set<Wasteroom> wasteroomReading;

    public Set<Wasteroom> getWasteroomReading() {
        return wasteroomReading;
    }

    public void setWasteroomReading(Set<Wasteroom> wasteroomReading) {
        this.wasteroomReading = wasteroomReading;
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

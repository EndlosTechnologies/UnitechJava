package com.unitechApi.addmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.MachineSetParameter.model.LapFormer;
import com.unitechApi.store.indent.Model.UsageItem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "add_lapformer", schema = "AddMachine")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class AddLapFormer {
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
    @OneToMany(mappedBy = "lapFormerusage",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("lapFormerusage")
    private Set<UsageItem> usageItems;

    public Set<UsageItem> getUsageItems() {
        return usageItems;
    }

    public void setUsageItems(Set<UsageItem> usageItems) {
        this.usageItems = usageItems;
    }

    public AddLapFormer(boolean status) {
        this.status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    @OneToMany(mappedBy = "addLapFormer", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("addLapFormer")
    private Set<LapFormer> lapFormersreading;

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

    public Set<LapFormer> getLapFormersreading() {
        return lapFormersreading;
    }

    public void setLapFormersreading(Set<LapFormer> lapFormersreading) {
        this.lapFormersreading = lapFormersreading;
    }
}

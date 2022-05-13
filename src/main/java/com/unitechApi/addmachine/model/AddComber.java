package com.unitechApi.addmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.MachineSetParameter.model.Comber;
import com.unitechApi.store.indent.Model.UsageItem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "add_comber",schema = "AddMachine")@NoArgsConstructor
@AllArgsConstructor
public class AddComber {
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

    public AddComber(boolean status) {
        this.status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    @OneToMany(mappedBy = "addcomber", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("addcomber")
    private Set<Comber> comberreading;
    @OneToMany(mappedBy = "comberusage",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("comberusage")
    private Set<UsageItem>  usageItems;

    public Set<UsageItem> getUsageItems() {
        return usageItems;
    }

    public void setUsageItems(Set<UsageItem> usageItems) {
        this.usageItems = usageItems;
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

    public Set<Comber> getComberreading() {
        return comberreading;
    }

    public void setComberreading(Set<Comber> comberreading) {
        this.comberreading = comberreading;
    }
}

package com.unitechApi.addmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.MachineSetParameter.model.DrawFramesPerHank;
import com.unitechApi.MachineSetParameter.model.Drawframesperkg;
import com.unitechApi.store.indent.Model.UsageItem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "add_DrawFrames", schema = "AddMachine")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AddDrawFramesMachine {
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

    public AddDrawFramesMachine(boolean status) {
        this.status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "drawFramesMachine", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("drawFramesMachine")
    private Set<Drawframesperkg> drawframesperkgReading;

    @OneToMany(mappedBy = "drawFramesPerHanks", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("drawFramesPerHanks")
    private Set<DrawFramesPerHank> drawFramesPerHanks;

    @OneToMany(mappedBy = "drawFramesMachine",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("drawFramesMachine")
    private Set<UsageItem> usageItems;

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

    public Set<Drawframesperkg> getDrawframesperkgReading() {
        return drawframesperkgReading;
    }

    public void setDrawframesperkgReading(Set<Drawframesperkg> drawframesperkgReading) {
        this.drawframesperkgReading = drawframesperkgReading;
    }

    public Set<DrawFramesPerHank> getDrawFramesPerHanks() {
        return drawFramesPerHanks;
    }

    public void setDrawFramesPerHanks(Set<DrawFramesPerHank> drawFramesPerHanks) {
        this.drawFramesPerHanks = drawFramesPerHanks;
    }
}

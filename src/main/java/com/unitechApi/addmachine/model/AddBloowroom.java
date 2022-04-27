package com.unitechApi.addmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.MachineSetParameter.model.BloowRoom;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "add_blowroom", schema = "AddMachine")
@NoArgsConstructor
@AllArgsConstructor
public class AddBloowroom {
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


    @OneToMany(mappedBy = "addBloowroom",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("addBloowroom")
    private Set<BloowRoom> bloowroomReading;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<BloowRoom> getBloowroomReading() {
        return bloowroomReading;
    }

    public void setBloowroomReading(Set<BloowRoom> bloowroomReading) {
        this.bloowroomReading = bloowroomReading;
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

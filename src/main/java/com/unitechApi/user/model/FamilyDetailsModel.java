package com.unitechApi.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "family_details", schema = "profiledetails")
public class FamilyDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(
            name = "family_id"
    )
    private Long id;
    @Column(
            name = "family_name"
    )
    private String name;
    @Column(
            name = "age"
    )
    private int age;
    @Column(
            name = "relationship"
    )
    private String relationship;
    @Column(
            name = "occupation"
    )
    private String occupation;
    @Column(
            name = "position"
    )
    private String position;
    @Column(
            name = "official_Number"
    )
    private String offcialTelNumber;
    @Column(
            name = "current_date_time"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentDatetime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(foreignKey = @ForeignKey(name = "user_id"), name = "user_id", referencedColumnName = "user_profile_id")
    private User userProfileModel;

    @PrePersist
    private void curentdatetime() {
        currentDatetime = new Date();
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOffcialTelNumber() {
        return offcialTelNumber;
    }

    public void setOffcialTelNumber(String offcialTelNumber) {
        this.offcialTelNumber = offcialTelNumber;
    }

    public Date getCurrentDatetime() {
        return currentDatetime;
    }

    public void setCurrentDatetime(Date currentDatetime) {
        this.currentDatetime = currentDatetime;
    }

    public User getUserProfileModel() {
        return userProfileModel;
    }

    public void setUserProfileModel(User userProfileModel) {
        this.userProfileModel = userProfileModel;
    }

    public void AddFamilyDetails(User userProfileModel) {
        this.userProfileModel = userProfileModel;
    }
}

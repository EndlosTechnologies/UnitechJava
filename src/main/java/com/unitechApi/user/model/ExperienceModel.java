package com.unitechApi.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "experience", schema = "profiledetails")
public class ExperienceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "experience_id")
    private Long id;
    @Column(name = "user_organization")
    private String organization;
    @Column(name = "user_position")
    private String position;
    @Column(name = "reporting")
    private String reporting;
    @Column(name = "year_experience")
    private int yearOfExperience;
    @Column(name = "salary")
    private int salary;
    @Column(name = "reason_of_leaving")
    private String reasonOfLeaving;
    @Column(
            name = "current_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentDatetime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "user_id"), name = "user_id", referencedColumnName = "user_profile_id")
    @JsonBackReference
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReporting() {
        return reporting;
    }

    public void setReporting(String reporting) {
        this.reporting = reporting;
    }

    public int getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(int yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getReasonOfLeaving() {
        return reasonOfLeaving;
    }

    public void setReasonOfLeaving(String reasonOfLeaving) {
        this.reasonOfLeaving = reasonOfLeaving;
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

    public void AddExperience(User userProfileModel) {
        this.userProfileModel = userProfileModel;
    }
}

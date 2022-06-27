package com.unitechApi.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "user_qualification", schema = "profiledetails")
public class QualificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    @Column(
            name = "user_examination", nullable = true
    )
    private String examination;
    @NotNull(message = "you are Not Allowed To Null Examination ")
    @Column(
            name = "user_universityBoard"
    )
    @NotNull(message = "you are Not Allowed To Null UniversityBoard ")
    private String universityBoard;
    @Column(
            name = "user_durationOfCourse"
    )
    @NotNull(message = "you are Not Allowed To Null Time Duration")
    private int durationOfCourse;
    @Column(
            name = "user_yearOfPassing"
    )
    @NotNull(message = " please Enter the Year Of Passing")
    private int yearOfPassing;

    @Column(
            name = "user_grade", nullable = true
    )
    @NotNull(message = "Please Enter grade" )
    private String grade;
    @Column(
            name = "current_date_time"
    )
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

    public String getExamination() {
        return examination;
    }

    public void setExamination(String examination) {
        this.examination = examination;
    }

    public String getUniversityBoard() {
        return universityBoard;
    }

    public void setUniversityBoard(String universityBoard) {
        this.universityBoard = universityBoard;
    }

    public int getDurationOfCourse() {
        return durationOfCourse;
    }

    public void setDurationOfCourse(int durationOfCourse) {
        this.durationOfCourse = durationOfCourse;
    }

    public int getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(int yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public void AddQualification(User userProfileModel) {
        this.userProfileModel = userProfileModel;
    }
}

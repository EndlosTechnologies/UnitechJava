package com.unitechApi.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "hr_confirmation", schema = "profiledetails")
public class HrModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @GenericGenerator(name = "native", strategy = "native")

    @Column(
            name = "hr_modelid"
    )
    private Long id;
    @Column(
            name = "interviewBy"
    )
    private String interviewBy;
    @Column(
            name = "interviewDate"
    )
    private LocalDate InterviewDate;
    @Column(
            name = "user_post"
    )
    private String Post;
    @Column(
            name = "user_salary"
    )
    private long salary;
    @Column(
            name = "period_Of_Probation"
    )
    private int periodOfProbation;
    @Column(
            name = "Date_of_job"
    )
    private String doj;
    @Column(
            name = "Remarks"
    )
    private String remarks;
    @Column(
            name = "current_date_time"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentDatetime;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_profile_id")
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

    public String getInterviewBy() {
        return interviewBy;
    }

    public void setInterviewBy(String interviewBy) {
        this.interviewBy = interviewBy;
    }

    public LocalDate getInterviewDate() {
        return InterviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        InterviewDate = interviewDate;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public int getPeriodOfProbation() {
        return periodOfProbation;
    }

    public void setPeriodOfProbation(int periodOfProbation) {
        this.periodOfProbation = periodOfProbation;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
}

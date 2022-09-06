package com.unitechApi.user.model;


import com.fasterxml.jackson.annotation.*;
import com.unitechApi.AuditingAndResponse.Audit;
import com.unitechApi.exception.validator.BirthDate;
import com.unitechApi.store.Response.Model.ResEntity;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.po.Model.PoStore;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", schema = "profiledetails", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;

    private String surName;
    @NotBlank(message = "please fill Unique Name ")
    @Size(min = 3, max = 20, message = " required Username is not accept  < 3 or > 20")
    private String username;
    @Value("{validation.mail.notEmpty}")
    @NotNull(message = "Enter Your Valid Email")
    @Size(max = 50)
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "confirmatioStatus")
    private ConfirmatioStatus confirmatioStatus;

    @NotBlank(message = "please fill your Address")
    private String address;

    @NotBlank(message = "please fill your Number")
    @Column(unique = true)
    private String phoneno;

    @Column(name = "user_tele_number", nullable = true)
    private String telephoneNumber;
    @Column(name = "Date_Of_birth")
    @BirthDate
    @Past
    @NotNull(message = "Add the value  ")
    private LocalDate dob;
    @Column(name = "marital_status", nullable = true)
    private String maritalStatus;
    @Column(name = "native_palace", nullable = true)
    private String nativePalace;
    @Column(name = "nationality", nullable = true)
    private String nationality;
    @Column(name = "user_pinCode", nullable = true)
    private int pinCode;
//    @Column(name = "confirmed",nullable = true)
//    private boolean confirmed;
//
//    public boolean isConfirmed() {
//        return confirmed;
//    }
//
//    public void setConfirmed(boolean confirmed) {
//        this.confirmed = confirmed;
//    }

    @Column(name = "BloodGroup", nullable = true)
    private String bloodGroup;
    @Column(name = "indentification", nullable = true)
    private String indentification;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date datetime;


    @PrePersist
    private void Createdate() {
        datetime = new Date();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "profiledetails", name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "userProfileModel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<QualificationModel> userQualificationData;

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("employe")
    private Set<StoreItemModel> itemModelSet;
    @OneToMany(mappedBy = "",cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"userListData"})
    private Set<PoStore> poStoreSetData;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private PasswordEntity passwordEntity;


    @OneToOne(mappedBy = "userProfileModel")
    @JsonManagedReference
    private HrModel hrModel;
    @OneToMany(mappedBy = "userProfileModel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<FamilyDetailsModel> familyDetails;
    @OneToMany(mappedBy = "userProfileModel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ExperienceModel> userExperienceData;

    @OneToMany(mappedBy = "emp", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("emp")
    private Set<IssueItem> issueItemsData;

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("employe")
    private Set<ResEntity> response;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("employee")
    private Set<Indent> indentData;

    public Set<Indent> getIndentData() {
        return indentData;
    }

    public void setIndentData(Set<Indent> indentData) {
        this.indentData = indentData;
    }

    public Set<ResEntity> getResponse() {
        return response;
    }

    public void setResponse(Set<ResEntity> response) {
        this.response = response;
    }

    public PasswordEntity getPasswordEntity() {
        return passwordEntity;
    }

    public void setPasswordEntity(PasswordEntity passwordEntity) {
        this.passwordEntity = passwordEntity;
    }


    public Set<IssueItem> getIssueItemsData() {
        return issueItemsData;
    }

    public void setIssueItemsData(Set<IssueItem> issueItemsData) {
        this.issueItemsData = issueItemsData;
    }

    public Set<StoreItemModel> getItemModelSet() {
        return itemModelSet;
    }

    public void setItemModelSet(Set<StoreItemModel> itemModelSet) {
        this.itemModelSet = itemModelSet;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public User() {
    }



    public User(String username, String email, String address, String phoneno, String telephoneNumber, LocalDate dob, String maritalStatus, String nativePalace, String nationality, int pinCode, String bloodGroup, String indentification) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.phoneno = phoneno;
        this.telephoneNumber = telephoneNumber;
        this.dob = dob;
        this.maritalStatus = maritalStatus;
        this.nativePalace = nativePalace;
        this.nationality = nationality;
        this.pinCode = pinCode;
        this.bloodGroup = bloodGroup;
        this.indentification = indentification;

        this.confirmatioStatus = ConfirmatioStatus.PENDING;
    }

    public ConfirmatioStatus getConfirmatioStatus() {
        return confirmatioStatus;
    }

    public void setConfirmatioStatus(ConfirmatioStatus confirmatioStatus) {
        this.confirmatioStatus = confirmatioStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }


    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }


    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        LocalDate birthdate = dob;
        LocalDate currentdate = LocalDate.now();
        calculateAge(birthdate, currentdate);
        this.dob = dob;
    }

    private static int calculateAge(LocalDate birthdate, LocalDate currentdate) {
        if ((birthdate != null) && (currentdate != null)) {
            return Period.between(birthdate, currentdate).getYears();
        } else throw new RuntimeException("You Are Not ELigible This Job");
    }


    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNativePalace() {
        return nativePalace;
    }

    public void setNativePalace(String nativePalace) {
        this.nativePalace = nativePalace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getIndentification() {
        return indentification;
    }

    public void setIndentification(String indentification) {
        this.indentification = indentification;
    }

    public Set<QualificationModel> getUserQualificationData() {
        return userQualificationData;
    }

    public void setUserQualificationData(Set<QualificationModel> userQualificationData) {
        this.userQualificationData = userQualificationData;
    }

    public HrModel getHrModel() {
        return hrModel;
    }

    public void setHrModel(HrModel hrModel) {
        this.hrModel = hrModel;
    }

    public Set<FamilyDetailsModel> getFamilyDetails() {
        return familyDetails;
    }

    public void setFamilyDetails(Set<FamilyDetailsModel> familyDetails) {
        this.familyDetails = familyDetails;
    }

    public Set<ExperienceModel> getUserExperienceData() {
        return userExperienceData;
    }

    public void setUserExperienceData(Set<ExperienceModel> userExperienceData) {
        this.userExperienceData = userExperienceData;
    }


}
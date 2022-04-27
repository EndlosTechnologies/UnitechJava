package com.unitechApi.Payload.request;

import com.unitechApi.user.model.PasswordEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> roles;

    private String password;
    @NotBlank
    private String phoneno;
    @NotBlank
    private String address;

    private String telephoneNumber;

    private LocalDate dob;

    private String maritalStatus;

    private String nativePalace;

    private String nationality;

    private int pinCode;

    private String bloodGroup;
    private String indentification;

    public String getIndentification() {
        return indentification;
    }
    PasswordEntity pa=new PasswordEntity();
    public void setIndentification(String indentification) {
        this.indentification = indentification;
    }
//    @Enumerated(EnumType.ORDINAL)
//    private Status status;
//
//    public Status getStatus() {
//        return status;
//    }

//    public void setStatus(Status status) {
//        this.status = status;
//    }

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
        this.dob = dob;
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

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date datetime;

    @PrePersist
    private void Createdate() {
        datetime = new Date();
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
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

    public Set<String> getRoles() {
        System.out.println("sign up Request ");
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }




}

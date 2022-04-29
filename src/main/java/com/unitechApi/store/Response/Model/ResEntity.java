package com.unitechApi.store.Response.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.store.issue.model.IssueStatus;
import com.unitechApi.user.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "response",schema = "store_management")
public class ResEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;
    @Enumerated(EnumType.STRING)
    private ResStatus resStatus;

    @Enumerated(EnumType.STRING)
    private pdiid issueStatus;

    public pdiid getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(pdiid issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getComents() {
        return coments;
    }

    public void setComents(String coments) {
        this.coments = coments;
    }

    private Date created;
    private Long pdiId;
    public Date getCreated() {
        return created;
    }
    @PrePersist
    public void setCreated() {
        this.created = new Date();
    }


    private String coments;
    private String remarks;
    private boolean indentRaised;
    private boolean poRaised;
    private boolean doRaised;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "emp_id"), name = "emp_id", referencedColumnName = "user_profile_id")
    @JsonIgnoreProperties("response")
    private User employe;
    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPdiId() {
        return pdiId;
    }

    public void setPdiId(Long pdiId) {
        this.pdiId = pdiId;
    }

    public User getEmploye() {
        return employe;
    }

    public void setEmploye(User employe) {
        this.employe = employe;
    }


    public ResStatus getResStatus() {
        return resStatus;
    }

    public void setResStatus(ResStatus resStatus) {
        this.resStatus = resStatus;
    }

    public String getComments() {
        return coments;
    }

    public void setComments(String comments) {
        this.coments = comments;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isIndentRaised() {
        return indentRaised;
    }

    public void setIndentRaised(boolean indentRaised) {
        this.indentRaised = indentRaised;
    }

    public boolean isPoRaised() {
        return poRaised;
    }

    public void setPoRaised(boolean poRaised) {
        this.poRaised = poRaised;
    }

    public boolean isDoRaised() {
        return doRaised;
    }

    public void setDoRaised(boolean doRaised) {
        this.doRaised = doRaised;
    }
}

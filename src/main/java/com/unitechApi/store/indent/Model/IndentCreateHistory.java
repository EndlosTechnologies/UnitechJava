package com.unitechApi.store.indent.Model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "indent_history",schema = "store_management")
public class IndentCreateHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indent_history_id", nullable = false)
    private Long indent_history_id;
    private  Long indentId;
    @Enumerated(EnumType.STRING)
    private  IndentStatus indentEvent=IndentStatus.GM;
    @CreationTimestamp
    private Date TimeStamp;
    private Long userId;
    private String indentNumber;
    private String userName;
    private String comment;

    public IndentCreateHistory(IndentStatus indentEvent) {
        this.indentEvent = indentEvent;
    }

    public IndentCreateHistory(Long indentId, IndentStatus indentEvent, Long userId) {
        this.indentId = indentId;
        this.indentEvent = indentEvent;
        this.userId = userId;
    }

    public IndentCreateHistory(Long indentId, IndentStatus indentEvent, Long userId, String userName) {
        this.indentId = indentId;
        this.indentEvent = indentEvent;
        this.userId = userId;
        this.userName = userName;
    }
    public IndentCreateHistory(String indentNumber,Long indentId, IndentStatus indentEvent, Long userId, String userName,String  comment) {
        this.indentNumber=indentNumber;
        this.indentId = indentId;
        this.indentEvent = indentEvent;
        this.userId = userId;
        this.userName = userName;
        this.comment=comment;
    }

    public IndentCreateHistory() {

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIndentNumber() {
        return indentNumber;
    }

    public void setIndentNumber(String indentNumber) {
        this.indentNumber = indentNumber;
    }




    public Long getIndentId() {
        return indentId;
    }

    public void setIndentId(Long indentId) {
        this.indentId = indentId;
    }

    public IndentStatus getIndentEvent() {
        return indentEvent;
    }

    public void setIndentEvent(IndentStatus indentEvent) {
        this.indentEvent = indentEvent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        TimeStamp = timeStamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIndent_history_id() {
        return indent_history_id;
    }

    public void setIndent_history_id(Long indent_history_id) {
        this.indent_history_id = indent_history_id;
    }
}

package com.unitechApi.securityService.Entity;

import com.unitechApi.securityService.Event.EventType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "security_event",schema = "security")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Long event_id;
    private String message;
    private String userName;
    @CreationTimestamp
    private Date createdDate;
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    public EventEntity(String message, EventType eventType,String userName) {
        this.message = message;
        this.eventType = eventType;
        this.userName=userName;
    }

    public EventEntity() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }
}

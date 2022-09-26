package com.unitechApi.user.training.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "training_period",schema = "profiledetails")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString
public class  TrainingSession {
    @Id
    @Column(name = "trainingId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String cardNumber = RandomStringUtils.randomNumeric(6);
    private String userName;
    private String existingPost;
    private String trailCheckBy;
    public void setEndDate() {
        endDate=startDate.plusDays(3);

    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }
}

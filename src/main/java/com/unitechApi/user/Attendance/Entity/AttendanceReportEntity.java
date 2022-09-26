package com.unitechApi.user.Attendance.Entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "attendance_report",schema = "profiledetails")
public class AttendanceReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id", nullable = false)
    private Long attendanceId;

    private String employeeName;
    private String employeeCode;
    private String shift;
    private String aInTime;
    private String aOutTime;
    private String dBreak;
    private String workDuration;
    private String overTime;
    private String timeDuration;
    private String status;
    private String remarks;

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }
}

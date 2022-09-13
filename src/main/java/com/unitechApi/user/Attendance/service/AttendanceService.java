package com.unitechApi.user.Attendance.service;

import com.unitechApi.user.Attendance.Entity.AttendanceReportEntity;

import java.nio.file.WatchService;

public interface AttendanceService {
    AttendanceReportEntity save(AttendanceReportEntity attendanceReportEntity);

}

package com.unitechApi.user.Attendance.Repository;

import com.unitechApi.user.Attendance.Entity.AttendanceReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AttendanceRepository  extends JpaRepository<AttendanceReportEntity,Long> {



}

package com.unitechApi.securityService.Repository;

import com.unitechApi.securityService.Dto.SecuritySave;
import com.unitechApi.securityService.Entity.SecurityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SecurityRepository extends JpaRepository<SecurityModel ,Long> {
    @Query("select s from SecurityModel s")
    List<SecuritySave> FindAll();
}

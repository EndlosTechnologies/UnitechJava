package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddUtillityMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddUtilityRepository extends JpaRepository<AddUtillityMachine, Long> {
    List<AddUtillityMachine> findByStatus(boolean status);
}

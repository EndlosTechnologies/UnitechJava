package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddWindingMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddWindingRepository extends JpaRepository<AddWindingMachine, Long> {
    List<AddWindingMachine> findByStatus(boolean status);
}
